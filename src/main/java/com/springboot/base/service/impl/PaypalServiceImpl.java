package com.springboot.base.service.impl;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.springboot.base.data.dto.EmailDTO;
import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.data.enmus.OrderStatus;
import com.springboot.base.data.enmus.paypal.PaypalPaymentIntent;
import com.springboot.base.data.enmus.paypal.PaypalPaymentMethod;
import com.springboot.base.data.entity.OrderInfo;
import com.springboot.base.data.exception.PrivateException;
import com.springboot.base.data.vo.EmailOrderContentVO;
import com.springboot.base.data.vo.OrderDetailVO;
import com.springboot.base.data.vo.OrderVO;
import com.springboot.base.mapper.OrderDetailMapper;
import com.springboot.base.service.OrderService;
import com.springboot.base.service.PaypalService;
import com.springboot.base.util.EmailUtils;
import com.springboot.base.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PaypalServiceImpl implements PaypalService {

    private final String CURRENCY = "USD";

    @Value("${web.url}")
    private String WEB_URL;

    @Value("${paypal.client.app}")
    private String clientId;

    @Value("${paypal.client.secret}")
    private String clientSecret;

    @Value("${paypal.mode}")
    private String mode;

    private String PAYPAL_SUCCESS_URL = "/checkout-forth.html";

    private String PAYPAL_CANCEL_URL ="/";

    @Inject
    private OrderService orderService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createPayment(OrderInfo order) throws Exception {
        //订单保存
        OrderInfo save = orderService.save(order);
        Amount amount = new Amount();
        amount.setCurrency(CURRENCY);
        amount.setTotal(String.format("%.2f", save.getAmount()));
        Transaction transaction = new Transaction();
        transaction.setDescription(save.getDescription());
        transaction.setAmount(amount);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        Payer payer = new Payer();
        payer.setPaymentMethod(PaypalPaymentMethod.paypal.toString());
        Payment payment = new Payment();
        payment.setIntent(PaypalPaymentIntent.sale.toString());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(WEB_URL + PAYPAL_CANCEL_URL);
        redirectUrls.setReturnUrl(WEB_URL + PAYPAL_SUCCESS_URL);
        payment.setRedirectUrls(redirectUrls);
        Payment newPayment = payment.create(apiContext());
        for (Links links : newPayment.getLinks()) {
            if (links.getRel().equals("approval_url")) {
                orderService.updatePaymentId(save.getId(), newPayment.getId());
                return links.getHref();
            }
        }
        throw new PrivateException(ErrorInfo.PAY_ERROR);
    }

    @Override
    public void executePayment(String paymentId, String payerId) throws Exception {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        payment = payment.execute(apiContext(), paymentExecute);
        if (payment.getState().equals("approved")) {
            //处理订单
           orderService.updateStatusByPaymentId(paymentId, OrderStatus.PAY_SUCCESS.getIndex(), OrderStatus.PAYING.getIndex());
            HttpClientUtil.async(()->sendEmail(paymentId));
        } else {
            throw new PrivateException(ErrorInfo.PAY_ERROR);
        }
    }

    private APIContext apiContext() throws PayPalRESTException {
        APIContext apiContext = new APIContext(clientId, clientSecret, mode);
        return apiContext;
    }

    private void sendEmail(String paymentId) {
        OrderVO order = orderService.getByPaymentId(paymentId);
        EmailOrderContentVO emailOrderContentVO = new EmailOrderContentVO();
        BeanUtils.copyProperties(order, emailOrderContentVO);
        List<OrderDetailVO> orderDetailVOList = orderService.getOrderDetailById(order.getId());
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss yy/MM/dd");
        emailOrderContentVO.setCreateTime(formatter.format(order.getCreate_time()));
        emailOrderContentVO.setOrderDetailVOs(orderDetailVOList);
        try {
            EmailUtils.sendEmail(new EmailDTO("", order.getEmail(), "", "ORDER SUMMARY JUNJIE", emailOrderContentVO.getContent()));
            log.info("email success");
        } catch (Exception e) {
            log.error("邮件发送失败！paymentId{}", paymentId, e);
        }
    }

}

package com.springboot.base.service.impl;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.data.enmus.paypal.PaypalPaymentIntent;
import com.springboot.base.data.enmus.paypal.PaypalPaymentMethod;
import com.springboot.base.data.entity.OrderInfo;
import com.springboot.base.data.exception.PrivateException;
import com.springboot.base.service.OrderService;
import com.springboot.base.service.PaypalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PaypalServiceImpl implements PaypalService {

    private final String CURRENCY = "USD";

    @Value("${web.url}")
    private String WEB_URL;

    private String PAYPAL_SUCCESS_URL = "";

    private String PAYPAL_CANCEL_URL ="";

    @Autowired
    private APIContext apiContext;

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
        Payment newPayment = payment.create(apiContext);
        for (Links links : newPayment.getLinks()) {
            if (links.getRel().equals("approval_url")) {
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
        payment = payment.execute(apiContext, paymentExecute);
        if (payment.getState().equals("approved")) {
            //处理订单

        } else {
            throw new PrivateException(ErrorInfo.PAY_ERROR);
        }
    }
}

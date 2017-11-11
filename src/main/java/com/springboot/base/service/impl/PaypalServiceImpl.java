package com.springboot.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.springboot.base.data.enmus.paypal.PaypalPaymentIntent;
import com.springboot.base.data.enmus.paypal.PaypalPaymentMethod;
import com.springboot.base.data.entity.OrderInfo;
import com.springboot.base.service.PaypalService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;

@Service
@Log4j
public class PaypalServiceImpl implements PaypalService {

    @Value("${web.url}")
    private String WEB_URL;

    private final String CURRENCY = "USD";

    private String PAYPAL_SUCCESS_URL = WEB_URL + "";

    private String PAYPAL_CANCEL_URL= WEB_URL + "";

    @Autowired
    private APIContext apiContext;

    @Override
    public Payment createPayment(OrderInfo order) throws Exception {
        Amount amount = new Amount();
        amount.setCurrency(CURRENCY);
        amount.setTotal(String.format("%.2f", order.getAmount()));

        Transaction transaction = new Transaction();
        transaction.setDescription(order.getDescription());
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
        redirectUrls.setCancelUrl(PAYPAL_SUCCESS_URL);
        redirectUrls.setReturnUrl(PAYPAL_CANCEL_URL);
        payment.setRedirectUrls(redirectUrls);

        Payment payment1 = payment.create(apiContext);
        log.info(payment1);
        return payment1;
    }

    @Override
    public Payment executePayment(String paymentId, String payerId) throws Exception {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }
}

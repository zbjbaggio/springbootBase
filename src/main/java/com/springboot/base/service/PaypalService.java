package com.springboot.base.service;

import com.paypal.api.payments.Payment;
import com.springboot.base.data.entity.OrderInfo;

/**
 * 描述：
 * Created by jay on 2017-11-9.
 */
public interface PaypalService {

    String createPayment(OrderInfo order) throws Exception;

    void executePayment(String paymentId, String payerId) throws Exception;
}

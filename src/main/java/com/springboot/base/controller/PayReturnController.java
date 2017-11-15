package com.springboot.base.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.springboot.base.data.entity.OrderInfo;
import com.springboot.base.service.PaypalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 描述：订单web
 * Created by jay on 2017-9-29.
 */
@Controller
@RequestMapping("web/payReturn")
@Slf4j
public class PayReturnController {

    @Autowired
    private PaypalService paypalService;

    @RequestMapping(value = "/cancel", method = RequestMethod.GET)
    public String cancelPay() {
        log.error("");
        return "hello";
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) throws Exception {
        paypalService.executePayment(paymentId, payerId);
        return "redirect:/";
    }

}

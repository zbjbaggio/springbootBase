package com.springboot.base.controller;

import com.springboot.base.service.PaypalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
        log.error("失败！");
        return "cancel";
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) throws Exception {
        paypalService.executePayment(paymentId, payerId);
        return "hello";
    }

}

package com.springboot.base.controller.advice;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.springboot.base.data.entity.OrderInfo;
import com.springboot.base.service.PaypalService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pay")
@Log4j
public class PaymentController {

    @Autowired
    private PaypalService paypalService;

    @RequestMapping(method = RequestMethod.POST)
    public String pay(@RequestBody @Validated OrderInfo order) {
        try {
            Payment payment = paypalService.createPayment(order);
            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return "redirect:" + links.getHref();
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.GET)
    public String cancelPay() {
        log.error("11111111111111111");
        return "cancel";
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) throws Exception {
        log.error("2222222222222222222");
        Payment payment = paypalService.executePayment(paymentId, payerId);
        if (payment.getState().equals("approved")) {
            return "success";
        }
        return "redirect:/";
    }

}

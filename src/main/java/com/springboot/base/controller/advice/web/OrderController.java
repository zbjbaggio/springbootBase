package com.springboot.base.controller.advice.web;

import com.springboot.base.data.dto.OrderDTO;
import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.data.entity.OrderInfo;
import com.springboot.base.data.exception.PrivateException;
import com.springboot.base.service.PaypalService;
import com.springboot.base.util.BindingResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/web/order")
@Slf4j
public class OrderController {

    @Autowired
    private PaypalService paypalService;

    @RequestMapping(method = RequestMethod.POST)
    public String pay(@RequestBody @Validated OrderDTO orderDTO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("参数错误！{}", BindingResultUtils.getErrorMessage(bindingResult.getAllErrors()));
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        OrderInfo order = new OrderInfo();
        BeanUtils.copyProperties(orderDTO, order);
        return  paypalService.createPayment(order);
    }

    @RequestMapping(value = "/success", method = RequestMethod.POST)
    public void successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) throws Exception {
        paypalService.executePayment(paymentId, payerId);
    }

}

package com.springboot.base.controller.advice.web;

import com.springboot.base.data.dto.OrderDTO;
import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.data.entity.OrderInfo;
import com.springboot.base.data.exception.PrivateException;
import com.springboot.base.data.vo.PayVO;
import com.springboot.base.service.PaypalService;
import com.springboot.base.util.BindingResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping("/web/order")
@Slf4j
public class OrderController {

    @Inject
    private PaypalService paypalService;

    @RequestMapping(method = RequestMethod.POST)
    public PayVO pay(@RequestBody @Validated OrderDTO orderDTO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("参数错误！{}", BindingResultUtils.getErrorMessage(bindingResult.getAllErrors()));
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        OrderInfo order = new OrderInfo();
        BeanUtils.copyProperties(orderDTO, order);
        //处理一下数据，receiverCountry 和  receiverArea互换
        String receiverCountry = order.getReceiverArea();
        order.setReceiverArea(order.getReceiverCountry());
        order.setReceiverCountry(receiverCountry);
        return new PayVO(paypalService.createPayment(order));
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public void successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) throws Exception {
        paypalService.executePayment(paymentId, payerId);
    }

}

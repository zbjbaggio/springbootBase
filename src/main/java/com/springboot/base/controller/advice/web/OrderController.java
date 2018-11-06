package com.springboot.base.controller.advice.web;

import com.springboot.base.data.dto.OrderDTO;
import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.data.entity.OrderInfo;
import com.springboot.base.data.exception.PrivateException;
import com.springboot.base.data.vo.PayVO;
import com.springboot.base.service.PaypalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URLDecoder;

@RestController
@RequestMapping("/web/order")
@Slf4j
public class OrderController {

    @Inject
    private PaypalService paypalService;

/*    @Inject
    private WebSocket webSocket;*/

    @PostMapping
    public PayVO pay(@RequestBody @Validated OrderDTO orderDTO, BindingResult bindingResult) throws Exception {
        OrderInfo order = new OrderInfo();
        BeanUtils.copyProperties(orderDTO, order);
        //处理一下数据，receiverCountry 和  receiverArea互换
        order.setEmail(URLDecoder.decode(order.getEmail(), "UTF-8"));
        String receiverCountry = order.getReceiverArea();
        order.setReceiverArea(order.getReceiverCountry());
        order.setReceiverCountry(receiverCountry);
        //webSocket.onMessage("订单到了！");
        return new PayVO(paypalService.createPayment(order));
    }

    @GetMapping(value = "/success")
    public void successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) throws Exception {
        paypalService.executePayment(paymentId, payerId);
    }

}

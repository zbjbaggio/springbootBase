package com.springboot.base.controller.advice.manager;

import com.springboot.base.data.base.Page;
import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.data.enmus.OrderStatus;
import com.springboot.base.data.exception.PrivateException;
import com.springboot.base.data.vo.OrderVO;
import com.springboot.base.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * 描述：订单管理
 * Created by jay on 2017-9-29.
 */
@RestController
@RequestMapping("/manage/user/orderInfo")
@Slf4j
public class OrderInfoController {

    @Inject
    private OrderService orderService;

    /**
     * 订单查询
     *
     * @param limit
     * @param offset
     * @param searchStr
     * @param status
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Page list(@RequestParam(value = "limit", defaultValue = "10") int limit,
                     @RequestParam(value = "offset", defaultValue = "0") int offset,
                     @RequestParam(value = "searchStr", defaultValue = "-1") String searchStr,
                     @RequestParam(value = "status", defaultValue = "-1") int status,
                     @RequestParam(value = "orderBy", defaultValue = "") String orderBy,
                     @RequestParam(value = "desc") boolean desc) {
        return orderService.listPage(limit, offset, searchStr, status, orderBy, desc);
    }

    /**
     * 订单详情
     *
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public OrderVO detail(@RequestParam(value = "orderId") Long orderId) {
        return orderService.getDetail(orderId);
    }

    /**
     * 订单删除
     * @param orderIds 订单lid
     * @throws Exception 异常
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void delete(@RequestParam Long[] orderIds) throws Exception {
        if (orderIds == null || orderIds.length <= 0) {
            log.info("productIds为空！");
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        orderService.delete(orderIds);
    }

    /**
     * 订单完成
     * @param orderId 订单lid
     * @throws Exception 异常
     */
    @RequestMapping(value = "/success", method = RequestMethod.POST)
    public void success(@RequestParam Long orderId) throws Exception {
        orderService.updateStatusByOrderId(orderId, OrderStatus.SUCCESS.getIndex(), OrderStatus.PAY_SUCCESS.getIndex());
    }

}

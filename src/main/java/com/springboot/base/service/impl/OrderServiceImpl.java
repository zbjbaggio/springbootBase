package com.springboot.base.service.impl;

import com.springboot.base.data.base.Page;
import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.data.enmus.OrderStatus;
import com.springboot.base.data.entity.OrderDetail;
import com.springboot.base.data.entity.OrderInfo;
import com.springboot.base.data.exception.PrivateException;
import com.springboot.base.data.vo.OrderDetailVO;
import com.springboot.base.data.vo.OrderVO;
import com.springboot.base.data.vo.ProductVO;
import com.springboot.base.mapper.OrderDetailMapper;
import com.springboot.base.mapper.OrderMapper;
import com.springboot.base.service.OrderService;
import com.springboot.base.service.ProductService;
import com.springboot.base.util.NOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Inject
    private OrderMapper orderMapper;

    @Inject
    private OrderDetailMapper orderDetailMapper;

    @Inject
    private ProductService productService;

    @Override
    public Page listPage(int limit, int offset, String searchStr, int status, String orderBy, boolean desc) {
        if (!"-1".equals(searchStr)) {
            searchStr = "%" + searchStr + "%";
        }
        String descStr = "";
        if (!StringUtils.isEmpty(orderBy) && desc) {
            descStr = "desc";
        }
        Page page = new Page();
        Long count = orderMapper.count(searchStr, status);
        if (count != 0) {
            page.setCount(count);
            List<OrderVO> orderVOs = orderMapper.listPage(limit, offset, searchStr, status, orderBy, descStr);
            List<Long> orderIds = new ArrayList<>();
            for (OrderVO orderVO : orderVOs) {
                orderIds.add(orderVO.getId());
            }
            List<OrderDetailVO> orderDetails = orderDetailMapper.listOrderDetail(orderIds);
            Map<Long, List<OrderDetailVO>> map = new HashMap<>();
            List<OrderDetailVO> list;
            for (OrderDetailVO orderDetail : orderDetails) {
                list = map.get(orderDetail.getOrder_id());
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(orderDetail);
                map.put(orderDetail.getOrder_id(), list);
            }
            for (OrderVO orderVO : orderVOs) {
                orderVO.setOrderDetailVOList(map.get(orderVO.getId()));
            }
            page.setList(orderVOs);
        }
        return page;
    }

    @Override
    public OrderVO getDetail(Long orderId) {
        return null;
    }

    @Override
    public void delete(Long[] orderIds) throws Exception {
        int i = orderMapper.updateDr(orderIds);
        if (i <= 0) {
            throw new PrivateException(ErrorInfo.DELETE_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderInfo save(OrderInfo order) throws Exception {
        //处理订单
        order.setStatus(OrderStatus.PAYING.getIndex());
        order.setOrderNo(NOUtils.getGeneratorNO());
        List<Long> productIds = new ArrayList<>();
        Map<Long, ProductVO> map = new HashMap<>();
        List<OrderDetail> orderDetailList = order.getOrderDetailList();
        for (OrderDetail orderDetail : orderDetailList) {
            productIds.add(orderDetail.getProductId());
        }
        List<ProductVO> productVOfoList = productService.getByIds(productIds);
        if (productVOfoList == null || productVOfoList.size() <= 0 || productVOfoList.size() != productIds.size()) {
            log.error("有商品被删除或者已下架或者找不到商品！商品id{}", productIds);
            throw new PrivateException(ErrorInfo.PRODUCT_ERROR);
        }
        for (ProductVO productVO : productVOfoList) {
            map.put(productVO.getId(), productVO);
        }
        BigDecimal orderAmount = new BigDecimal(0);
        for (OrderDetail orderDetail : orderDetailList) {
            ProductVO productVO = map.get(orderDetail.getProductId());
            orderDetail.setProductName(productVO.getName());
            orderDetail.setPrice(productVO.getPrice());
            BigDecimal amount = productVO.getPrice().multiply(BigDecimal.valueOf(orderDetail.getNumber()));
            orderDetail.setAmount(amount);
            orderAmount = orderAmount.add(amount);
        }
        order.setAmount(orderAmount);
        int count = orderMapper.save(order);
        if (count != 1) {
            log.error("订单主表保存失败！");
            throw new PrivateException(ErrorInfo.SAVE_ERROR);
        }
        for (OrderDetail orderDetail : orderDetailList) {
            orderDetail.setOrderId(order.getId());
        }
        count = orderMapper.saveDetails(orderDetailList);
        if (count != orderDetailList.size()) {
            log.error("订单子表保存失败！");
            throw new PrivateException(ErrorInfo.SAVE_ERROR);
        }
        return order;
    }

    @Override
    public void updatePaymentId(Long orderId, String paymentId) throws Exception {
        int count = orderMapper.updatePaymentId(orderId, paymentId);
        if (count != 1) {
            log.error("订单子表保存失败！");
            throw new PrivateException(ErrorInfo.SAVE_ERROR);
        }
    }

    @Override
    public void updateStatusByPaymentId(String paymentId, byte newOrderStatus, byte oldOrderStatus) throws Exception {
        int count = orderMapper.updateStatusByPaymentId(paymentId, newOrderStatus, oldOrderStatus);
        if (count != 1) {
            log.error("订单状态保存失败！");
            throw new PrivateException(ErrorInfo.SAVE_ERROR);
        }
    }

    @Override
    public void updateStatusByOrderId(Long orderId, byte newOrderStatus, byte oldOrderStatus) throws Exception {
        int count = orderMapper.updateStatusByOrderId(orderId, newOrderStatus, oldOrderStatus);
        if (count != 1) {
            log.error("订单状态保存失败！");
            throw new PrivateException(ErrorInfo.SAVE_ERROR);
        }
    }

    @Override
    public void updateStatusByOrderId(Long orderId, byte newOrderStatus) throws Exception {
        int count = orderMapper.updateStatusByOrderId(orderId, newOrderStatus);
        if (count != 1) {
            log.error("订单状态保存失败！");
            throw new PrivateException(ErrorInfo.SAVE_ERROR);
        }
    }

}

package com.springboot.base.service.impl;

import com.springboot.base.data.base.Page;
import com.springboot.base.data.vo.OrderVO;
import com.springboot.base.mapper.OrderMapper;
import com.springboot.base.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.inject.Inject;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Inject
    private OrderMapper orderMapper;

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
            page.setList(orderMapper.listPage(limit, offset, searchStr, status, orderBy, descStr));
        }
        return page;
    }

    @Override
    public OrderVO getDetail(Long orderId) {
        return null;
    }

    @Override
    public void delete(Long[] orderIds) {

    }
}

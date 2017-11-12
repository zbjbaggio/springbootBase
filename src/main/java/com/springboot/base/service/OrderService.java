package com.springboot.base.service;

import com.springboot.base.data.base.Page;
import com.springboot.base.data.vo.OrderVO;

public interface OrderService {

    Page listPage(int limit, int offset, String searchStr, int status, String orderBy, boolean desc);

    OrderVO getDetail(Long orderId);

    void delete(Long[] orderIds);

}

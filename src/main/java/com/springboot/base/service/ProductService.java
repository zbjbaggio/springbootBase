package com.springboot.base.service;

import com.springboot.base.data.base.Page;
import com.springboot.base.enmus.ProductStatus;
import com.springboot.base.data.entity.ProductInfo;
import com.springboot.base.data.exception.PrivateException;
import com.springboot.base.data.vo.ProductVO;

import java.util.List;

public interface ProductService {

    Page listPage(int limit, int offset, String searchStr, int status, String orderBy, boolean desc);

    ProductInfo save(ProductInfo productInfo) throws Exception;

    ProductInfo update(ProductInfo productInfo) throws Exception;

    ProductVO getDetail(Long productId);

    void updateStatus(Long productId, ProductStatus offShelves) throws PrivateException;

    void delete(Long[] productIds) throws PrivateException, Exception;

    List<ProductVO> getByIds(List<Long> productIds);
}

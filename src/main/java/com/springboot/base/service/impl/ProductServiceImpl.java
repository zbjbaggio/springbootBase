package com.springboot.base.service.impl;

import com.springboot.base.data.base.Page;
import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.data.enmus.ProductStatus;
import com.springboot.base.data.entity.ProductInfo;
import com.springboot.base.data.exception.PrivateException;
import com.springboot.base.data.vo.ProductVO;
import com.springboot.base.mapper.ProductMapper;
import com.springboot.base.service.ProductService;
import com.springboot.base.util.ValueHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Inject
    private ProductMapper productMapper;

    @Inject
    private ValueHolder valueHolder;

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
        Long count = productMapper.count(searchStr, status);
        if (count != 0) {
            page.setCount(count);
            page.setList(productMapper.listPage(limit, offset, searchStr, status, orderBy, descStr));
        }
        return page;
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) throws Exception {
        checkProductNo(productInfo);
        productInfo.setOperatorId(valueHolder.getUserIdHolder());
        int count = productMapper.save(productInfo);
        if (count > 0) {
            return productInfo;
        }
        return null;
    }

    @Override
    public ProductInfo update(ProductInfo productInfo) throws Exception {
        checkProductNo(productInfo);
        productInfo.setOperatorId(valueHolder.getUserIdHolder());
        int count = productMapper.update(productInfo);
        if (count > 0) {
            return productInfo;
        }
        return null;
    }

    @Override
    public ProductVO getDetail(Long productId) {
        return productMapper.getDetailById(productId);
    }

    @Override
    public void updateStatus(Long productId, ProductStatus offShelves) throws Exception {
        int i = productMapper.updateStatus(offShelves.getIndex(), productId);
        if (i <= 0) {
            throw new PrivateException(ErrorInfo.STATUS_ERROR);
        }
    }

    @Override
    public void delete(Long[] productIds) throws Exception {
        int i = productMapper.updateDr(productIds);
        if (i <= 0) {
            throw new PrivateException(ErrorInfo.DELETE_ERROR);
        }
    }

    @Override
    public List<ProductVO> getByIds(List<Long> productIds) {
        return productMapper.getByIds(productIds);
    }

    private void checkProductNo(ProductInfo productInfo) throws Exception {
        ProductInfo newProductInfo;
        if (productInfo.getId() == null) {
            newProductInfo = productMapper.getProductNoStateNoId(productInfo.getProductNo());
        } else {
            newProductInfo = productMapper.getProductNoState(productInfo.getProductNo(), productInfo.getId());
        }
        if (newProductInfo != null) {
            throw new PrivateException(ErrorInfo.NO_SAME);
        }
    }
}

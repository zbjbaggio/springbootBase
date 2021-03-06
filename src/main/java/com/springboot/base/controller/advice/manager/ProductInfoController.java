package com.springboot.base.controller.advice.manager;

import com.springboot.base.data.base.Page;
import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.data.enmus.ProductStatus;
import com.springboot.base.data.entity.ProductInfo;
import com.springboot.base.data.exception.PrivateException;
import com.springboot.base.data.vo.ProductVO;
import com.springboot.base.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * 描述：产品管理
 * Created by jay on 2017-9-29.
 */
@RestController
@RequestMapping("/manage/user/productInfo")
@Slf4j
public class ProductInfoController {

    @Inject
    private ProductService productService;

    /**
     * 产品查询
     *
     * @param limit
     * @param offset
     * @param searchStr
     * @param status
     * @return
     */
    @GetMapping(value = "/list")
    public Page list(@RequestParam(value = "limit", defaultValue = "10") int limit,
                     @RequestParam(value = "offset", defaultValue = "0") int offset,
                     @RequestParam(value = "searchStr", defaultValue = "-1") String searchStr,
                     @RequestParam(value = "status", defaultValue = "-1") int status,
                     @RequestParam(value = "orderBy", defaultValue = "") String orderBy,
                     @RequestParam(value = "desc") boolean desc) {
        return productService.listPage(limit, offset, searchStr, status, orderBy, desc);
    }

    /**
     * 添加产品
     *
     * @param productInfo
     * @return
     */
    @PostMapping(value = "/add")
    public ProductInfo add(@RequestBody @Validated(ProductInfo.BaseInfo.class) ProductInfo productInfo, BindingResult bindingResult) throws Exception {
        return productService.save(productInfo);
    }

    /**
     * 产品详情
     *
     * @param productId
     * @return
     */
    @GetMapping(value = "/getDetail")
    public ProductVO getDetail(@RequestParam(value = "productId") Long productId) {
        return productService.getDetail(productId);
    }

    /**
     * 产品修改
     * @param productInfo
     * @param bindingResult
     * @throws Exception
     */
    @PostMapping(value = "/update")
    public void update(@RequestBody @Validated(ProductInfo.Modify.class) ProductInfo productInfo, BindingResult bindingResult) throws Exception {
        productService.update(productInfo);
    }

    /**
     * 产品下架
     * @param productId
     * @throws Exception
     */
    @PostMapping(value = "/offShelves")
    public void offShelves(@RequestParam Long productId) throws Exception {
        if (productId == null) {
            log.info("productId为空！");
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        productService.updateStatus(productId, ProductStatus.OFF_SHELVES);
    }

    /**
     * 产品删除
     * @param productIds
     * @throws Exception
     */
    @PostMapping(value = "/delete")
    public void delete(@RequestParam Long[] productIds) throws Exception {
        if (productIds == null || productIds.length <= 0) {
            log.info("productIds为空！");
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        productService.delete(productIds);
    }
}

package com.springboot.base.controller.advice.web;

import com.springboot.base.data.vo.ProductVO;
import com.springboot.base.service.ProductService;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * 描述：前端产品
 * Created by jay on 2017-11-13.
 */
@RestController
@RequestMapping("/web/product")
@Log4j
public class ProductController {

    @Inject
    private ProductService productService;

    @PostMapping(value = "detail")
    public ProductVO detail(@RequestBody Long productId) {
        return productService.getDetail(productId);
    }

}

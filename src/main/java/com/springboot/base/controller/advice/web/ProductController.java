package com.springboot.base.controller.advice.web;

import com.springboot.base.data.vo.ProductVO;
import com.springboot.base.service.ProductService;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "detail", method = RequestMethod.POST)
    public ProductVO detail(@RequestBody Long productId) throws Exception {
        return productService.getDetail(productId);
    }

}

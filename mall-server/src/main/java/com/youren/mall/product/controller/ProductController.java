package com.youren.mall.product.controller;

import com.youren.mall.common.result.Result;
import com.youren.mall.common.result.PageResult;
import com.youren.mall.product.service.ProductService;
import com.youren.mall.product.vo.ProductVO;
import com.youren.mall.product.dto.ProductCreateDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public Result<Long> createProduct(@Valid @RequestBody ProductCreateDTO dto) {
        Long id = productService.createProduct(dto);
        return Result.success(id);
    }

    @GetMapping
    public Result<PageResult<ProductVO>> getProducts(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId
    ) {
        return Result.success(productService.getProducts(page, size, keyword, categoryId));
    }
}

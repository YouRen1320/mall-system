package com.youren.mall.product.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductVO {

    private Long id;

    private Long categoryId;

    private String name;

    private BigDecimal price;

    private Integer stock;

    private String description;

    private String imageUrl;
}
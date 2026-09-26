package com.youren.mall.product.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youren.mall.common.result.PageResult;
import com.youren.mall.product.converter.ProductConverter;
import com.youren.mall.product.entity.Product;
import com.youren.mall.product.dto.ProductCreateDTO;
import com.youren.mall.product.mapper.ProductMapper;
import com.youren.mall.product.vo.ProductVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;
    private final ProductConverter productConverter;

    public Long createProduct(ProductCreateDTO dto) {
        // 显式映射允许新增的字段，新商品默认上架。
        Product product = new Product();
        product.setCategoryId(dto.categoryId());
        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setStock(dto.stock());
        product.setDescription(dto.description());
        product.setImageUrl(dto.imageUrl());
        product.setStatus(1);

        // AUTO 主键在插入成功后由 MyBatis-Plus 回填。
        productMapper.insert(product);
        return product.getId();
    }

    public PageResult<ProductVO> getProducts(long page, long size, String keyword, Long categoryId) {
        // 只展示上架商品；搜索与分类条件可选，同时提供时取交集。
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Product::getStatus, 1)
                .like(StringUtils.hasText(keyword), Product::getName, keyword)
                .eq(categoryId != null, Product::getCategoryId, categoryId)
                .orderByDesc(Product::getId);

        Page<Product> productPage = new Page<>(page, size);
        productMapper.selectPage(productPage, wrapper);

        // 保留数据库分页元数据，仅将当前页的 Entity 转换为公开的 VO。
        return new PageResult<>(
                productPage.getTotal(),
                productPage.getPages(),
                productPage.getCurrent(),
                productPage.getSize(),
                productConverter.toVOList(productPage.getRecords())
        );
    }
}

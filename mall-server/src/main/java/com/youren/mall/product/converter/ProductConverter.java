package com.youren.mall.product.converter;

import com.youren.mall.product.entity.Product;
import com.youren.mall.product.vo.ProductVO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductConverter {

    ProductVO toVO(Product product);

    List<ProductVO> toVOList(List<Product> products);
}
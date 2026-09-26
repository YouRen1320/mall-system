package com.youren.mall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youren.mall.product.entity.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}

package com.youren.mall.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

// 新增请求只接受可编辑字段，主键、状态和时间由服务端或数据库管理。
public record ProductCreateDTO(
        @NotNull(message = "分类不能为空")
        Long categoryId,

        @NotBlank(message = "商品名称不能为空")
        String name,

        @NotNull(message = "价格不能为空")
        @Positive(message = "价格必须大于0")
        BigDecimal price,

        @NotNull(message = "库存不能为空")
        @Min(value = 0, message = "库存不能小于0")
        Integer stock,

        String description,
        String imageUrl
) {
}

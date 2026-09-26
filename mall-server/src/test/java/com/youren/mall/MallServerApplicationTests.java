package com.youren.mall;

import com.youren.mall.product.converter.ProductConverter;
import com.youren.mall.product.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MallServerApplicationTests {

    @Autowired
    private ProductConverter productConverter;

    @Test
    void contextLoads() {
    }

    @Test
    void converterBeanMapsLombokProperties() {
        // Verify both Spring registration and accessor discovery by the annotation processors.
        Product product = new Product();
        product.setId(1L);
        product.setCategoryId(2L);
        product.setName("Test product");
        product.setPrice(new BigDecimal("19.90"));
        product.setStock(3);
        product.setDescription("Test description");
        product.setImageUrl("/test-product.png");

        var results = productConverter.toVOList(List.of(product));

        assertThat(results).hasSize(1);
        assertThat(results.getFirst())
                .usingRecursiveComparison()
                .isEqualTo(product);
    }

}

package com.youren.mall;

import com.youren.mall.product.controller.ProductController;
import com.youren.mall.product.converter.ProductConverter;
import com.youren.mall.product.entity.Product;
import com.youren.mall.product.mapper.ProductMapper;
import com.youren.mall.product.service.ProductService;
import com.youren.mall.common.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductCreateTests {
    private ProductMapper mapper;
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        // 真实 Controller、Service 和 Validation，仅隔离数据库，避免留下测试商品。
        mapper = mock(ProductMapper.class);
        ProductService service = new ProductService(mapper, mock(ProductConverter.class));
        mvc = MockMvcBuilders.standaloneSetup(new ProductController(service))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void createsProductAndReturnsGeneratedId() throws Exception {
        when(mapper.insert(any(Product.class))).thenAnswer(invocation -> {
            Product product = invocation.getArgument(0);
            assertThat(product.getId()).isNull();
            assertThat(product.getCategoryId()).isEqualTo(1L);
            assertThat(product.getName()).isEqualTo("华为 Mate 80");
            assertThat(product.getPrice()).isEqualByComparingTo("6999.00");
            assertThat(product.getStock()).isZero();
            assertThat(product.getDescription()).isEqualTo("华为旗舰手机");
            assertThat(product.getImageUrl()).isNull();
            assertThat(product.getStatus()).isEqualTo(1);
            product.setId(5L);
            return 1;
        });

        mvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON).content("""
                {"categoryId":1,"name":"华为 Mate 80","price":6999.00,
                 "stock":0,"description":"华为旗舰手机","imageUrl":null}
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data").value(5));
        verify(mapper).insert(any(Product.class));
    }

    @Test
    void rejectsInvalidFieldsBeforeDatabaseInsert() throws Exception {
        mvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON).content("""
                {"categoryId":1,"name":" ","price":-1,"stock":-10}
                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("商品名称不能为空"))
                .andExpect(jsonPath("$.data").doesNotExist());
        verifyNoInteractions(mapper);
    }

    @Test
    void rejectsMissingRequiredFields() throws Exception {
        mvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(mapper);
    }

    @Test
    void rejectsZeroPrice() throws Exception {
        mvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON).content("""
                {"categoryId":1,"name":"商品","price":0,"stock":0}
                """))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(mapper);
    }

    @Test
    void returnsSafeJsonForUnexpectedException() throws Exception {
        doThrow(new IllegalStateException("database credentials must not leak"))
                .when(mapper).insert(any(Product.class));

        mvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON).content("""
                {"categoryId":1,"name":"商品","price":1,"stock":0}
                """))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("服务器内部错误"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}

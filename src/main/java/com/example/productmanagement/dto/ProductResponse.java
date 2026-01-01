package com.example.productmanagement.dto;

import com.example.productmanagement.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * 商品レスポンス
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String sku;
    private String name;
    private String description;
    private Product.ProductStatus status;
    private OffsetDateTime discontinuedAt;
    private String discontinuedNote;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}


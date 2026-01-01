package com.example.productmanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * 商品エンティティ
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private String sku;
    private String name;
    private String description;
    private ProductStatus status;
    private OffsetDateTime discontinuedAt;
    private String discontinuedNote;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    /**
     * 販売状態
     */
    public enum ProductStatus {
        ACTIVE,
        INACTIVE
    }
}


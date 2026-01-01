package com.example.productmanagement.dto;

import com.example.productmanagement.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 商品作成リクエスト
 */
@Data
public class ProductCreateRequest {
    @NotBlank(message = "SKUは必須です")
    @Size(max = 64, message = "SKUは64文字以内で入力してください")
    private String sku;

    @NotBlank(message = "商品名は必須です")
    @Size(max = 200, message = "商品名は200文字以内で入力してください")
    private String name;

    @Size(max = 5000, message = "説明は5000文字以内で入力してください")
    private String description;

    private Product.ProductStatus status;
}


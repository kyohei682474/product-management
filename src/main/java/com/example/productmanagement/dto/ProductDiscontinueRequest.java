package com.example.productmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 商品絶版リクエスト
 */
@Data
public class ProductDiscontinueRequest {
    @NotBlank(message = "絶版理由は必須です")
    @Size(max = 500, message = "絶版理由は500文字以内で入力してください")
    private String note;
}


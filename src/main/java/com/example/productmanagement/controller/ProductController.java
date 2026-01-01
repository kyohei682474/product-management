package com.example.productmanagement.controller;

import com.example.productmanagement.dto.ProductCreateRequest;
import com.example.productmanagement.dto.ProductDiscontinueRequest;
import com.example.productmanagement.dto.ProductResponse;
import com.example.productmanagement.dto.ProductUpdateRequest;
import com.example.productmanagement.entity.Product;
import com.example.productmanagement.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品コントローラー
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 商品一覧を取得
     * GET /api/products?status=ACTIVE
     */
    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll(
            @RequestParam(required = false) Product.ProductStatus status) {
        List<ProductResponse> products = productService.findAll(status);
        return ResponseEntity.ok(products);
    }

    /**
     * 商品をIDで取得
     * GET /api/products/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        ProductResponse product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    /**
     * 商品を作成
     * POST /api/products
     */
    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductCreateRequest request) {
        ProductResponse product = productService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    /**
     * 商品を更新
     * PUT /api/products/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductUpdateRequest request) {
        ProductResponse product = productService.update(id, request);
        return ResponseEntity.ok(product);
    }

    /**
     * 商品を絶版にする
     * POST /api/products/{id}/discontinue
     */
    @PostMapping("/{id}/discontinue")
    public ResponseEntity<ProductResponse> discontinue(
            @PathVariable Long id,
            @Valid @RequestBody ProductDiscontinueRequest request) {
        ProductResponse product = productService.discontinue(id, request);
        return ResponseEntity.ok(product);
    }

    /**
     * 商品を削除
     * DELETE /api/products/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}


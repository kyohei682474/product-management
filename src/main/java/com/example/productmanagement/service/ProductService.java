package com.example.productmanagement.service;

import com.example.productmanagement.dto.ProductCreateRequest;
import com.example.productmanagement.dto.ProductDiscontinueRequest;
import com.example.productmanagement.dto.ProductResponse;
import com.example.productmanagement.dto.ProductUpdateRequest;
import com.example.productmanagement.entity.Product;
import com.example.productmanagement.exception.ResourceNotFoundException;
import com.example.productmanagement.exception.ValidationException;
import com.example.productmanagement.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品サービス
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;

    /**
     * 商品一覧を取得
     */
    @Transactional(readOnly = true)
    public List<ProductResponse> findAll(Product.ProductStatus status) {
        List<Product> products = productMapper.findAll(status);
        return products.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * 商品をIDで取得
     */
    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        Product product = productMapper.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("商品が見つかりません: id=" + id));
        return toResponse(product);
    }

    /**
     * 商品を作成
     */
    @Transactional
    public ProductResponse create(ProductCreateRequest request) {
        // SKU重複チェック
        if (productMapper.findBySku(request.getSku()).isPresent()) {
            throw new ValidationException("SKUが既に存在します: " + request.getSku());
        }

        Product product = Product.builder()
                .sku(request.getSku())
                .name(request.getName())
                .description(request.getDescription())
                .status(request.getStatus() != null ? request.getStatus() : Product.ProductStatus.ACTIVE)
                .discontinuedAt(null)
                .discontinuedNote(null)
                .build();

        productMapper.insert(product);
        return toResponse(product);
    }

    /**
     * 商品を更新
     */
    @Transactional
    public ProductResponse update(Long id, ProductUpdateRequest request) {
        Product product = productMapper.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("商品が見つかりません: id=" + id));

        // 更新可能な項目のみ更新
        if (request.getName() != null) {
            product.setName(request.getName());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
        if (request.getStatus() != null) {
            product.setStatus(request.getStatus());
            // ステータスがACTIVEに変更された場合、絶版情報をクリア
            if (request.getStatus() == Product.ProductStatus.ACTIVE) {
                product.setDiscontinuedAt(null);
                product.setDiscontinuedNote(null);
            }
        }

        productMapper.update(product);
        return toResponse(product);
    }

    /**
     * 商品を絶版にする
     */
    @Transactional
    public ProductResponse discontinue(Long id, ProductDiscontinueRequest request) {
        Product product = productMapper.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("商品が見つかりません: id=" + id));

        product.setStatus(Product.ProductStatus.INACTIVE);
        product.setDiscontinuedAt(OffsetDateTime.now());
        product.setDiscontinuedNote(request.getNote());

        productMapper.update(product);
        return toResponse(product);
    }

    /**
     * 商品を削除
     */
    @Transactional
    public void delete(Long id) {
        Product product = productMapper.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("商品が見つかりません: id=" + id));
        productMapper.delete(id);
    }

    /**
     * エンティティをレスポンスDTOに変換
     */
    private ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .description(product.getDescription())
                .status(product.getStatus())
                .discontinuedAt(product.getDiscontinuedAt())
                .discontinuedNote(product.getDiscontinuedNote())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}


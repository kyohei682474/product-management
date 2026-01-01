package com.example.productmanagement.mapper;

import com.example.productmanagement.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * 商品Mapper
 */
@Mapper
public interface ProductMapper {
    /**
     * 商品をIDで検索
     */
    Optional<Product> findById(@Param("id") Long id);

    /**
     * 商品をSKUで検索
     */
    Optional<Product> findBySku(@Param("sku") String sku);

    /**
     * 商品一覧を取得（ステータスでフィルタ可能）
     */
    List<Product> findAll(@Param("status") Product.ProductStatus status);

    /**
     * 商品を作成
     */
    void insert(Product product);

    /**
     * 商品を更新
     */
    void update(Product product);

    /**
     * 商品を削除
     */
    void delete(@Param("id") Long id);

    /**
     * SKUの重複チェック（更新時、自分以外）
     */
    boolean existsBySkuExcludingId(@Param("sku") String sku, @Param("id") Long id);
}


package com.chao.sell.service;

import com.chao.sell.dataobject.ProductInfo;
import com.chao.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductInfo findOne(String productId);
    //List<ProductInfo> findByCategoryType(Integer categoryId);
    /**
     * 查询所有在架商品
     * @return
     */
    List<ProductInfo> findOnAll();
    //查询结果过多，分页查询
    Page<ProductInfo> findAll(Pageable pageable);
    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increase(List<CartDTO> cartDTOList);
    //减库存
    void decrease(List<CartDTO> cartDTOList);

}

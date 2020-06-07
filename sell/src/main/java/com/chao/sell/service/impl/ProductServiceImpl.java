package com.chao.sell.service.impl;

import com.chao.sell.dataobject.ProductInfo;
import com.chao.sell.dto.CartDTO;
import com.chao.sell.enums.OrderStatusEnum;
import com.chao.sell.enums.ProductStatusEnum;
import com.chao.sell.enums.ResultEnum;
import com.chao.sell.exception.SellException;
import com.chao.sell.repository.ProductInfoRepository;
import com.chao.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductInfoRepository repository;
    @Override
    public ProductInfo findOne(String productId) {
        return repository.findById(productId).get();
    }
    @Override
    public List<ProductInfo> findOnAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional
    public void increase(List<CartDTO> cartDTOList) {
        Integer result;
        for(CartDTO cartDTO:cartDTOList){
            if(cartDTO.getProductQuantity()<=0)
                throw new SellException(OrderStatusEnum.PRODUCT__QUANTITY_ERROR);
            ProductInfo productInfo = findOne(cartDTO.getProductId());
            if(productInfo==null)
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decrease(List<CartDTO> cartDTOList) {
        Integer result;
        for(CartDTO cartDTO:cartDTOList){
            ProductInfo productInfo = findOne(cartDTO.getProductId());
            if(productInfo==null)
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            //计算(库存-购买数量)
            result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if(result<0)
                throw new SellException(ResultEnum.STOCK_NOT_ENOUGH);
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }
}

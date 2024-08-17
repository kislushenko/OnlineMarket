package com.online.market.orderservice.service;

import com.online.market.orderservice.entity.Product;

public interface ProductService {

    void updateProductWithLatestData(String productCode);

    Product getProductByCode(String code);

}

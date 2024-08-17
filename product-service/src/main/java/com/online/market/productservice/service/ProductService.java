package com.online.market.productservice.service;

import com.online.market.productservice.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getAllProducts();

    ProductDTO getProductByCode(String code);

}

package com.online.market.productservice.service.impl;

import com.online.market.productservice.entity.Product;
import com.online.market.productservice.repository.ProductRepository;
import com.online.market.productservice.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

}

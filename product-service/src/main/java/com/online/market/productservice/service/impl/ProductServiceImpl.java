package com.online.market.productservice.service.impl;

import com.online.market.productservice.dto.ProductDTO;
import com.online.market.productservice.entity.Product;
import com.online.market.productservice.repository.ProductRepository;
import com.online.market.productservice.service.ProductService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ModelMapper modelMapper;

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
    }

    @Override
    public ProductDTO getProductByCode(String code) {
        final Product product = productRepository.findById(code).orElse(null);
        if (product != null) {
            return modelMapper.map(product, ProductDTO.class);
        }
        return null;
    }

}

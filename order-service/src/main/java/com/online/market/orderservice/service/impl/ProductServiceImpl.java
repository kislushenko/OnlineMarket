package com.online.market.orderservice.service.impl;

import com.online.market.orderservice.dto.ProductVO;
import com.online.market.orderservice.entity.Product;
import com.online.market.orderservice.repository.ProductRepository;
import com.online.market.orderservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    final ProductRepository productRepository;
    final RestTemplate restTemplate;

    @Override
    public void updateProductWithLatestData(String productCode) {
        final ProductVO productVO = restTemplate.getForObject("http://product-service/products/" + productCode, ProductVO.class);
        if (productVO != null) {
            Product product = productRepository.findById(productCode).orElse(null);
            if (product != null) {
                updateProduct(product, productVO);
            } else {
                createProduct(productVO);
            }
        }
        else {
            throw new IllegalArgumentException("Product does not exist");
        }
    }

    @Override
    public Product getProductByCode(String code) {
        Product product = productRepository.findById(code).orElse(null);
        if (product == null) {
            throw new IllegalArgumentException("Product does not exist");
        }
        return product;
    }

    private void createProduct(ProductVO productVO) {
        Product product = new Product();
        product.setCode(productVO.getCode());
        updateProduct(product, productVO);

    }

    private void updateProduct(final Product product, final ProductVO productVO) {
        product.setName(productVO.getName());
        product.setPrice(productVO.getPrice());
        productRepository.save(product);
    }
}

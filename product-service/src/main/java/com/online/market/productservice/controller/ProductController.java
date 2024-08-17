package com.online.market.productservice.controller;

import com.online.market.productservice.dto.ProductDTO;
import com.online.market.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{productCode}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable("productCode") String productCode) {
        return ResponseEntity.ok(productService.getProductByCode(productCode));
    }
}

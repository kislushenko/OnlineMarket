package com.online.market.productservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {
    private String code;
    private String name;
    private BigDecimal price;
}

package com.online.market.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductVO {

    private String code;
    private String name;
    private BigDecimal price;

}

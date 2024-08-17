package com.online.market.orderservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartEntryDTO {

    private ProductDTO product;
    private int quantity;
    private BigDecimal basePrice;
    private BigDecimal totalPrice;
}

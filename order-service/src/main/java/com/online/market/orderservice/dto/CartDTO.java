package com.online.market.orderservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartDTO {

    private Long id;
    private BigDecimal totalPrice;
    private List<CartEntryDTO> items;

}

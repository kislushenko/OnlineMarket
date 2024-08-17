package com.online.market.orderservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDTO {

    private Long orderId;
    private BigDecimal totalPrice;
    private List<CartEntryDTO> items;

}

package com.online.market.orderservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_code")
    private Product product;
    @NotNull
    private int quantity;
    @NotNull
    private BigDecimal basePrice;
    @NotNull
    private BigDecimal totalPrice;


}

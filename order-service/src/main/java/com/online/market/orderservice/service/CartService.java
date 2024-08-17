package com.online.market.orderservice.service;

import com.online.market.orderservice.dto.ItemDTO;
import com.online.market.orderservice.entity.Cart;

public interface CartService {

    //TODO: return DTO object
    Cart getOrCreateCartForUser(String email);

    void addItemToCart(String ownerEmail, ItemDTO item);

}

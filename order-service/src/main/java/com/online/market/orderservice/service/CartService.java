package com.online.market.orderservice.service;

import com.online.market.orderservice.dto.CartDTO;
import com.online.market.orderservice.dto.ItemDTO;
import com.online.market.orderservice.entity.Cart;

public interface CartService {

    CartDTO getCartForUser(String email);

    void addItemToCart(String ownerEmail, ItemDTO item);

}

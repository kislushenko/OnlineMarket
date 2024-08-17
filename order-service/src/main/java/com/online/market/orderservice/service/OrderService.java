package com.online.market.orderservice.service;

import com.online.market.orderservice.dto.OrderDTO;

public interface OrderService {

    OrderDTO placeOrder(String email);

}

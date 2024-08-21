package com.online.market.orderservice.service;

import com.online.market.orderservice.dto.OrderDTO;
import com.online.market.orderservice.exception.OrderPlaceException;

public interface OrderService {

    OrderDTO placeOrder(String email) throws OrderPlaceException;

}

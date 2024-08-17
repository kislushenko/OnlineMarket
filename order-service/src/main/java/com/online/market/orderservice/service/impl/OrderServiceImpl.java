package com.online.market.orderservice.service.impl;

import com.online.market.orderservice.dto.OrderDTO;
import com.online.market.orderservice.entity.Cart;
import com.online.market.orderservice.entity.Item;
import com.online.market.orderservice.entity.Order;
import com.online.market.orderservice.repository.CartRepository;
import com.online.market.orderservice.repository.OrderRepository;
import com.online.market.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderDTO placeOrder(String email) {
        final Cart cart = cartRepository.findByOwner(email).orElse(null);
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found for user " + email);
        }
        final Order order = new Order();
        final List<Item> items = new ArrayList<>(cart.getItems());
        order.setItems(items);
        order.setOwner(email);
        order.setTotalPrice(cart.getTotalPrice());

        orderRepository.save(order);

        cart.getItems().clear();
        cartRepository.save(cart);


        return modelMapper.map(order, OrderDTO.class);
    }

}

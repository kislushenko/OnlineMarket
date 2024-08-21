package com.online.market.orderservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.market.orderservice.dto.OrderDTO;
import com.online.market.orderservice.entity.Cart;
import com.online.market.orderservice.entity.Item;
import com.online.market.orderservice.entity.Order;
import com.online.market.orderservice.entity.OutboxEvent;
import com.online.market.orderservice.exception.OrderPlaceException;
import com.online.market.orderservice.repository.CartRepository;
import com.online.market.orderservice.repository.OrderRepository;
import com.online.market.orderservice.repository.OutboxEventRepository;
import com.online.market.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OutboxEventRepository outboxEventRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public OrderDTO placeOrder(String email) throws OrderPlaceException {
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
        cart.setTotalPrice(new BigDecimal("0.0"));
        cartRepository.save(cart);

        final OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);

        try {
            final OutboxEvent outboxEvent = new OutboxEvent();
            outboxEvent.setEventType("OrderCreated");
            outboxEvent.setEventPayload(new ObjectMapper().writeValueAsString(orderDTO));
            outboxEventRepository.save(outboxEvent);
        } catch (JsonProcessingException e) {
            throw new OrderPlaceException("An error occurred while placing the order.", e);
        }

        return orderDTO;
    }

    private String buildOrderCreatedEventPayload(OrderDTO order) {
        // Logic to construct the event payload
        // Include relevant information like order ID, customer details, etc.
        return "OrderCreatedPayload";
    }

}

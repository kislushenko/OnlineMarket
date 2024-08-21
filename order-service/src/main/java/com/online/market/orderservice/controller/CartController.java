package com.online.market.orderservice.controller;

import com.online.market.orderservice.dto.CartDTO;
import com.online.market.orderservice.dto.ItemDTO;
import com.online.market.orderservice.dto.OrderDTO;
import com.online.market.orderservice.exception.OrderPlaceException;
import com.online.market.orderservice.service.CartService;
import com.online.market.orderservice.service.OrderService;
import com.online.market.orderservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<CartDTO> getCart(@RequestHeader("Authorization") String token) {
        final String email = jwtUtil.extractEmail(token);
        return ResponseEntity.ok(cartService.getCartForUser(email));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addItem(@RequestBody ItemDTO itemDto,
                                        @RequestHeader("Authorization") String token) {
        final String email = jwtUtil.extractEmail(token);
        cartService.addItemToCart(email, itemDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/place")
    public ResponseEntity<OrderDTO> placeOrder(@RequestHeader("Authorization") String token) {
        final String email = jwtUtil.extractEmail(token);
        try {
            OrderDTO order = orderService.placeOrder(email);
            return ResponseEntity.ok(order);
        } catch (OrderPlaceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}

package com.online.market.orderservice.service.impl;

import com.online.market.orderservice.dto.CartDTO;
import com.online.market.orderservice.dto.ItemDTO;
import com.online.market.orderservice.entity.Cart;
import com.online.market.orderservice.entity.Item;
import com.online.market.orderservice.entity.Product;
import com.online.market.orderservice.repository.CartRepository;
import com.online.market.orderservice.service.CartService;
import com.online.market.orderservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final ModelMapper modelMapper;


    @Override
    public CartDTO getCartForUser(final String email) {
        final Cart cart = getOrCreateCartForUser(email);
        return modelMapper.map(cart, CartDTO.class);
    }

    //TODO: implement change quantity logic when we add the same product several times
    @Override
    public void addItemToCart(String ownerEmail, ItemDTO item) {
        final Cart cart = getOrCreateCartForUser(ownerEmail);
        productService.updateProductWithLatestData(item.getProductCode());
        createCartItem(cart, item);
        updateTotalPrice(cart);
        cartRepository.save(cart);
    }

    private Cart getOrCreateCartForUser(String email) {
        final Cart cart = cartRepository.findByOwner(email).orElse(null);
        if (cart == null) {
            final Cart newCart = new Cart();
            newCart.setOwner(email);
            newCart.setTotalPrice(new BigDecimal(0));
            cartRepository.save(newCart);
            return newCart;
        }
        return cart;
    }

    private void updateTotalPrice(final Cart cart) {
        final BigDecimal totalPrice = cart.getItems().stream().map(Item::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalPrice(totalPrice);
    }

    private void createCartItem(final Cart cart, ItemDTO itemDto) {
        final Product product = productService.getProductByCode(itemDto.getProductCode());
        final List<Item> cartItems = cart.getItems();
        final Item item = new Item();
        item.setProduct(product);
        item.setQuantity(itemDto.getQuantity());
        item.setBasePrice(product.getPrice());
        item.setTotalPrice(product.getPrice().multiply(new BigDecimal(itemDto.getQuantity())));
        cartItems.add(item);
    }

}

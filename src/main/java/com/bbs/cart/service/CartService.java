package com.bbs.cart.service;

import com.bbs.cart.connector.CartDataConnector;
import com.bbs.cart.helper.CartServiceHelper;
import com.bbs.cart.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CartService {
    @Autowired
    private CartDataConnector cartDataConnector;

    @Autowired
    private CartServiceHelper cartServiceHelper;

    public Mono<Cart> addToCart(Cart cart) {
        return Mono.just(cart)
                .map(cartServiceHelper::setTime)
                .flatMap(cartDataConnector::createCart)
                .log();
    }
}

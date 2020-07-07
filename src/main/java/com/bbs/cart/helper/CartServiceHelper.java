package com.bbs.cart.helper;

import com.bbs.cart.model.Cart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class CartServiceHelper {

    public Cart setTime(Cart cart){
        cart.setCreatedDate(new Date());
        cart.setLastUpdatedTime(new Date());
        return cart;
    }
}

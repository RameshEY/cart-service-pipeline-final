package com.bbs.cart.controller;

import com.bbs.cart.common.CommonConstants;
import com.bbs.cart.model.Cart;
import com.bbs.cart.service.CartService;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Date;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @ApiOperation(value = "Get Cart", produces = CommonConstants.STREAM_JSON_TYPE,
            notes = "This API used to get the cart", response = Cart.class)
    @ApiResponses(value = {@ApiResponse(code = 400, message = CommonConstants.BAD_REQUEST),
            @ApiResponse(code = 500, message = CommonConstants.SERVER_ERROR)})

    @GetMapping(value = "/cart",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE})
    @ResponseStatus(code = HttpStatus.OK)
    @Timed
    public Mono<Cart> findCart() {
        return Mono.just(Cart.builder().cartId("123").createdDate(new Date()).userId("9876")
                .userType("REGISTERED").lastUpdatedTime(new Date()).build());
    }

    @ApiOperation(value = "Create the Cart", produces = CommonConstants.STREAM_JSON_TYPE,
            notes = "This API used to add cart", response = Cart.class)
    @ApiResponses(value = {@ApiResponse(code = 400, message = CommonConstants.BAD_REQUEST),
            @ApiResponse(code = 500, message = CommonConstants.SERVER_ERROR)})

    @PostMapping(value = "/cart",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE})
    @ResponseStatus(code = HttpStatus.OK)
    @Timed
    public Mono<Cart> addToCart(@RequestBody(required = true) Cart cart) {
        return cartService.addToCart(cart);
    }


}

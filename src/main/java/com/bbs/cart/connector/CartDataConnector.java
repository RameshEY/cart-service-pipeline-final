package com.bbs.cart.connector;

import com.bbs.cart.model.Cart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;

@Component
@Slf4j
public class CartDataConnector {

    public static final String CART_ID_PATH_PARAM = "{cartId}";
    public static final String CART_ID = "cartId";
    private WebClient cartDataWebClient;

    public CartDataConnector(@Value("${cart.data.service.baseUrl}") String cartDataServiceUrl) {
        cartDataWebClient = WebClient.builder().baseUrl(cartDataServiceUrl).build();
    }

    /**
     * This method is used to create the cart
     * @param cart
     * @return the cart
     */
    public Mono<Cart> createCart(Cart cart) {
        return cartDataWebClient.post().uri(uriBuilder -> uriBuilder.pathSegment("new").build())
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(cart))
                .retrieve().bodyToMono(Cart.class)
                .log();
    }

    /**
     * This method is used to update the cart
     * @param cart cart to be updated
     * @return the cart
     */
    public Mono<Cart> updateCart(Cart cart) {
        return requestBodySpec(cartDataWebClient, uriBuilder -> uriBuilder.path(CART_ID_PATH_PARAM)
                .build(Collections.singletonMap(CART_ID, cart.getCartId())), HttpMethod.PUT)
                .body(BodyInserters.fromValue(cart))
                .retrieve().bodyToMono(Cart.class)
                .log();
    }

    /**
     * This method is used to delete the cart based on cart id.
     * @param cartId the cart id
     * @return
     */
    public Mono<Void> deleteCart(String cartId) {
        return requestBodySpec(cartDataWebClient, uriBuilder -> uriBuilder.path(CART_ID_PATH_PARAM)
                .build(Collections.singletonMap(CART_ID, cartId)), HttpMethod.DELETE)
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .retrieve().bodyToMono(Void.class)
                .log();
    }

    /**
     * This method is used to find the cart based on cart id.
     * @param cartId the cart id
     * @return cart
     */
    public Mono<Cart> findCart(String cartId) {
        return cartDataWebClient.get().uri(uriBuilder -> uriBuilder.path(CART_ID_PATH_PARAM).build(Collections.singletonMap(CART_ID, cartId)))
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .retrieve()
                .bodyToMono(Cart.class)
                .log();
    }

    public static WebClient.RequestBodySpec requestBodySpec(WebClient webClient, Function<UriBuilder, URI> uriFunction, HttpMethod httpMethod) {
        return Optional.ofNullable(uriFunction)
                .map(uri -> webClient.method(httpMethod).uri(uri))
                .orElse(webClient.method(httpMethod));
    }
}

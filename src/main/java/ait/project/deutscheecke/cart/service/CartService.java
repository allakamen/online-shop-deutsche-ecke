package ait.project.deutscheecke.cart.service;


import ait.project.deutscheecke.cart.dto.AddToCartDto;

public interface CartService {
    boolean addProductToCart(Long userId, AddToCartDto addToCartDto);
    boolean removeProductFromCart(Long userId, Long cartItemId);
    boolean decreaseProductQuantity(Long userId, Long cartItemId);
}

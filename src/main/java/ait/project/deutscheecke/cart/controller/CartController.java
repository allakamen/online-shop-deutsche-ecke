package ait.project.deutscheecke.cart.controller;



import ait.project.deutscheecke.cart.dto.AddToCartDto;
import ait.project.deutscheecke.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CartController  {
    final CartService cartService;

    @PostMapping("/user/{userId}/cart/items/")
    public boolean addProductToCart(@PathVariable Long userId, @RequestBody AddToCartDto addToCartDto) {
        return cartService.addProductToCart(userId, addToCartDto);
    }

    @DeleteMapping("/user/{userId}/cart/items/{itemId}")
    public boolean removeProductFromCart(@PathVariable Long userId, @PathVariable Long itemId) {
        return cartService.removeProductFromCart(userId, itemId);
    }

    @PostMapping("/user/{userId}/cart/items/{itemId}/decrease-quantity")
    public boolean decreaseProductQuantity(@PathVariable Long userId, @PathVariable Long itemId) {
        return cartService.decreaseProductQuantity(userId, itemId);
    }

}

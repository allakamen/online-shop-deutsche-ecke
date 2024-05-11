package ait.project.deutscheecke.cart.service;



import ait.project.deutscheecke.cart.dao.CartItemRepository;
import ait.project.deutscheecke.cart.dao.CartRepository;
import ait.project.deutscheecke.cart.dto.AddToCartDto;
import ait.project.deutscheecke.cart.dto.exceptions.EntityNotFoundException;
import ait.project.deutscheecke.cart.model.Cart;
import ait.project.deutscheecke.cart.model.CartItem;
import ait.project.deutscheecke.product.dao.ProductRepository;
import ait.project.deutscheecke.product.model.Product;
import ait.project.deutscheecke.users.dao.UserRepository;
import ait.project.deutscheecke.users.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final ModelMapper modelMapper;


    @Override
    public boolean addProductToCart(Long userId, AddToCartDto addToCartDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Cart cart = user.getCart();

            if (cart == null) {
                cart = new Cart();
                cart.setId(userId);
                cart.setUser(user);
                user.setCart(cart);
            }

            Product product = productRepository.findById(addToCartDto.getProductId()).orElseThrow(EntityNotFoundException::new);
            if (product != null) {
                int quantityInCart = cart.getItems().stream()
                        .filter(item -> item.getProduct().getId().equals(addToCartDto.getProductId()))
                        .mapToInt(CartItem::getQuantity)
                        .sum();

                int totalQuantity = quantityInCart + addToCartDto.getQuantity();
                if (totalQuantity <= product.getQuantity()) {
                    Optional<CartItem> existingCartItem = cart.getItems().stream()
                            .filter(item -> item.getProduct().getId().equals(addToCartDto.getProductId()))
                            .findFirst();

                    if (existingCartItem.isPresent()) {
                        existingCartItem.get().setQuantity(totalQuantity);
                        cartItemRepository.save(existingCartItem.get());
                        return true;
                    } else {
                        CartItem cartItem = new CartItem();
                        cartItem.setProduct(product);
                        cartItem.setQuantity(addToCartDto.getQuantity());
                        cartItem.setCart(cart);
                        cart.getItems().add(cartItem);
                        userRepository.save(user);
                        return true;
                    }
                }
            }
        }
        return false;
    }


    @Override
    public boolean removeProductFromCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            CartItem removedItem = cartItemRepository.findFirstByProductIdAndCartId(productId, cart.getId());
            if (removedItem != null) {
                cartItemRepository.delete(removedItem);
                cartRepository.save(cart);
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean decreaseProductQuantity(Long userId, Long cartItemId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Cart cart = user.getCart();
            if (cart != null) {
                CartItem cartItem = cartItemRepository.findFirstByProductIdAndCartId(cartItemId, cart.getId());
                if (cartItem != null) {
                    int newQuantity = cartItem.getQuantity() - 1;
                    if (newQuantity > 0) {
                        cartItem.setQuantity(newQuantity);
                        cartItemRepository.save(cartItem);
                    } else {
                        cart.getItems().remove(cartItem);
                        cartRepository.save(cart);
                        cartItemRepository.delete(cartItem);
                    }
                    return true;
                }
            }
        }
        return false;
    }

}

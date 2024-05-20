package ait.project.deutscheecke.cart.dao;

import ait.project.deutscheecke.cart.model.Cart;
import ait.project.deutscheecke.cart.model.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findFirstByProductIdAndCartId(Long productId, Long cartId);
    @Transactional
    void deleteByCart(Cart cart);
    }

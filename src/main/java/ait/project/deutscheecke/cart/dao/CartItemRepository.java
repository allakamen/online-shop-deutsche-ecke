package ait.project.deutscheecke.cart.dao;

import ait.project.deutscheecke.cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findFirstByProductIdAndCartId(Long productId, Long cartId);
}

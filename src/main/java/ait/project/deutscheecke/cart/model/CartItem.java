package ait.project.deutscheecke.cart.model;


import ait.project.deutscheecke.product.model.Product;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "products_id")
    private Product product;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "carts_id")
    private Cart cart;

}

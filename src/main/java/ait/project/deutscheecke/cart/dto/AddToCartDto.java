package ait.project.deutscheecke.cart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCartDto {
    private Long productId;
    private int quantity;
}

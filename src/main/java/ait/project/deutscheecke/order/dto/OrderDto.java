package ait.project.deutscheecke.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDto {
    private Long userId;
    private List<Long> productIds;
    private String shippingAddress;
    private String paymentMethod;
}

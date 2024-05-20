package ait.project.deutscheecke.order.service;

import ait.project.deutscheecke.order.dto.OrderDto;
import ait.project.deutscheecke.order.model.OrderStatus;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);

    List<OrderDto> getOrdersByUserId(Long userId);

    OrderDto getOrderById(Long orderId);

    boolean deleteOrder(Long orderId);

    List<OrderDto> getAllOrders();

    List<OrderDto> getOrdersByStatus(String status);

    OrderDto updateOrderStatus(Long orderId, OrderStatus newStatus);

    OrderDto updateOrderDetails(Long orderId, OrderDto orderDto);
}

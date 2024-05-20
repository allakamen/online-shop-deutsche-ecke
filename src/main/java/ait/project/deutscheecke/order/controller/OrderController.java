package ait.project.deutscheecke.order.controller;


import ait.project.deutscheecke.order.dto.OrderDto;
import ait.project.deutscheecke.order.dto.OrderStatusDto;
import ait.project.deutscheecke.order.model.OrderStatus;
import ait.project.deutscheecke.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController  {

    final OrderService orderService;

    @PostMapping("/orders/")
    public OrderDto createOrder(@RequestBody OrderDto orderDto) {
        return orderService.createOrder(orderDto);
    }

    @GetMapping("/orders/user/{userId}")
    public List<OrderDto> getOrdersByUserId(@PathVariable Long userId) {
        return orderService.getOrdersByUserId(userId);
    }

    @GetMapping("/orders/{orderId}")
    public OrderDto getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @PutMapping("/orders/{orderId}")
    public OrderDto updateOrderDetails(@PathVariable Long orderId, @RequestBody OrderDto orderDto) {
        return orderService.updateOrderDetails(orderId, orderDto);
    }

    @PutMapping("/orders/{orderId}/status")
    public OrderDto updateOrderStatus(@PathVariable Long orderId, @RequestBody OrderStatusDto orderStatusDto) {
        OrderStatus orderStatus = OrderStatus.valueOf(orderStatusDto.getStatus().toUpperCase());
        return orderService.updateOrderStatus(orderId, orderStatus);
    }

    @DeleteMapping("/orders/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
    }

    @GetMapping("/orders/search/all/")
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/orders/search/status/{status}")
    public List<OrderDto> getOrdersByStatus(@PathVariable String status) {
        return orderService.getOrdersByStatus(String.valueOf(OrderStatus.valueOf(status)));
    }
}

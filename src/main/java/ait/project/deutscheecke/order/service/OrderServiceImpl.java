package ait.project.deutscheecke.order.service;


import ait.project.deutscheecke.cart.dao.CartItemRepository;
import ait.project.deutscheecke.cart.dao.CartRepository;
import ait.project.deutscheecke.cart.dto.exceptions.EntityNotFoundException;
import ait.project.deutscheecke.cart.model.Cart;
import ait.project.deutscheecke.cart.model.CartItem;
import ait.project.deutscheecke.order.dao.OrderItemRepository;
import ait.project.deutscheecke.order.dao.OrderRepository;
import ait.project.deutscheecke.order.dto.OrderDto;
import ait.project.deutscheecke.order.model.Order;
import ait.project.deutscheecke.order.model.OrderItem;
import ait.project.deutscheecke.order.model.OrderStatus;

import ait.project.deutscheecke.product.dao.ProductRepository;
import ait.project.deutscheecke.product.model.Product;
import ait.project.deutscheecke.users.dao.UserRepository;
import ait.project.deutscheecke.users.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;


    @Override
    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = modelMapper.map(orderDto, Order.class);
        order.setStatus(OrderStatus.IN_PROCESS);

        User user = userRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        order.setUser(user);

        Order savedOrder = orderRepository.save(order);

        Cart cart = user.getCart();
        List<CartItem> cartItems = cart != null ? cart.getItems() : Collections.emptyList();

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItems.add(orderItem);

            Product product = cartItem.getProduct();
            int remainingQuantity = product.getQuantity() - cartItem.getQuantity();
            product.setQuantity(remainingQuantity);
            productRepository.save(product);
        }


        orderItemRepository.saveAll(orderItems);
        if (cart != null) {
            cartItemRepository.deleteAll(cartItems);
            cart.setItems(Collections.emptyList());
            cartRepository.save(cart);
        }

        return mapOrderToOrderDto(savedOrder);
    }


    @Override
    public List<OrderDto> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(this::mapOrderToOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::mapOrderToOrderDto)
                .orElse(null);
    }


    @Override
    @Transactional
    public OrderDto updateOrderStatus(Long orderId, OrderStatus newStatus) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    order.setStatus(newStatus);
                    Order updatedOrder = orderRepository.save(order);
                    return mapOrderToOrderDto(updatedOrder);
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public OrderDto updateOrderDetails(Long orderId, OrderDto orderDto) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    order.setShippingAddress(orderDto.getShippingAddress());
                    order.setPaymentMethod(orderDto.getPaymentMethod());
                    Order updatedOrder = orderRepository.save(order);
                    return mapOrderToOrderDto(updatedOrder);
                })
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public boolean deleteOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    orderRepository.delete(order);
                    return true;
                })
                .orElse(false);
    }


    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::mapOrderToOrderDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<OrderDto> getOrdersByStatus(String status) {
        OrderStatus orderStatus = OrderStatus.valueOf(status);
        try {
            List<Order> orders = orderRepository.findByStatus(orderStatus);
            return orders.stream()
                    .map(this::mapOrderToOrderDto)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            return Collections.emptyList();
        }
    }

    public OrderDto mapOrderToOrderDto(Order order) {
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        orderDto.setUserId(order.getUser().getId());
        if (order.getItems() != null) {
            List<Long> productIds = order.getItems().stream()
                    .map(item -> item.getProduct().getId())
                    .collect(Collectors.toList());
            orderDto.setProductIds(productIds);
        }
        return orderDto;
    }
}

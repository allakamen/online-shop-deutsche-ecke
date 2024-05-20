package ait.project.deutscheecke.order.dao;

import ait.project.deutscheecke.order.model.Order;
import ait.project.deutscheecke.order.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.status = ?1")
    List<Order> findByStatus(OrderStatus status);

    List<Order> findByUserId(Long userId);

}

package org.dev.ecomm.repository;

import org.dev.ecomm.model.Order;
import org.dev.ecomm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o from Order o JOIN FETCH o.user")
    List<Order> findAllOrderWithUsers();

    List<Order> findAllByUser(User user);
}

package com.nderitu.ecommerce.repository;

import com.nderitu.ecommerce.entity.Order;
import com.nderitu.ecommerce.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    //return type will be order

    Order findByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);
}

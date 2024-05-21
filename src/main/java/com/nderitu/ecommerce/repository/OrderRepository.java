package com.nderitu.ecommerce.repository;

import com.nderitu.ecommerce.entity.Order;
import com.nderitu.ecommerce.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    //return type will be order

    Order findByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);
//These are method signatures that define queries, but the actual implementation of
// these queries would typically reside within the service layer.
    List<Order> findAllByOrderStatusIn(List<OrderStatus> orderStatusList);

    List<Order> findByUserIdAndOrderStatusIn(Long userId, List<OrderStatus> orderStatus);



}

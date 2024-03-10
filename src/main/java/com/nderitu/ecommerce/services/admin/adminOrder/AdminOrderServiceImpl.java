package com.nderitu.ecommerce.services.admin.adminOrder;

import com.nderitu.ecommerce.dto.OrderDto;
import com.nderitu.ecommerce.entity.Order;
import com.nderitu.ecommerce.enums.OrderStatus;
import com.nderitu.ecommerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
 public class AdminOrderServiceImpl implements AdminOrderService{
    private final OrderRepository orderRepository;

    public List<OrderDto> getAllPlacedOrders() {
//        a variable of list of orders
        List<Order> orderList = orderRepository.
                findAllByOrderStatusIn(List.of(OrderStatus.Placed,
                        OrderStatus.Shipped,
                        OrderStatus.Delivered));  //not getting pending orders

        return orderList.stream().map(Order::getOrderDto).collect(Collectors.toList());
    }

    //    API to create action button in orders
        public OrderDto changeOrderStatus(Long orderId, String status){
            Optional<Order> optionalOrder=orderRepository.findById(orderId);
            if(optionalOrder.isPresent()){
                Order order=optionalOrder.get();

                if(Objects.equals(status, "Shipped")){
                    order.setOrderStatus(OrderStatus.Shipped);

                }else if(Objects.equals(status, "Delivered")) {
                    order.setOrderStatus(OrderStatus.Delivered);
                }

                return orderRepository.save(order).getOrderDto();
            }
            return null;

        }
    }


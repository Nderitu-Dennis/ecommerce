package com.nderitu.ecommerce.services.admin.adminOrder;

import com.nderitu.ecommerce.dto.OrderDto;

import java.util.List;

public interface AdminOrderService {

    List<OrderDto> getAllPlacedOrders();

    OrderDto changeOrderStatus(Long orderId, String status);
}

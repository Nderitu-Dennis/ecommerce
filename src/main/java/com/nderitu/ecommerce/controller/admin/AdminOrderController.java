package com.nderitu.ecommerce.controller.admin;

import com.nderitu.ecommerce.dto.OrderDto;
import com.nderitu.ecommerce.services.admin.adminOrder.AdminOrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor

public class AdminOrderController {
//    object for admin service
    private final AdminOrderService adminOrderService;

//    endpoint for get all placed orders
    @GetMapping("/placedOrders")
    public ResponseEntity<List<OrderDto>> getAllPlacedOrders(){
        return ResponseEntity.ok(adminOrderService.getAllPlacedOrders());
    }

    @GetMapping("/order/{orderId}/{status}")
    public ResponseEntity<?> changeOrderStatus(@PathVariable Long orderId, @PathVariable String status) {
        OrderDto orderDto = adminOrderService.changeOrderStatus(orderId, status);
        if (orderDto == null)
            return new ResponseEntity<>("Something went wrong!,orderDto is null", HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
        }

    }









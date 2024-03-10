package com.nderitu.ecommerce.controller.admin;

import com.nderitu.ecommerce.entity.Coupon;
import com.nderitu.ecommerce.exceptions.ValidationException;
import com.nderitu.ecommerce.services.admin.coupon.AdminCouponService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/coupons")
@RequiredArgsConstructor

public class AdminCouponController {

    //injecting service

    private final AdminCouponService adminCouponService;

//    endpoints for create coupon API
    @PostMapping
    public ResponseEntity<?>createCoupon(@RequestBody Coupon coupon){
        try{
            Coupon createdCoupon=adminCouponService.createCoupon(coupon);
            return ResponseEntity.ok(createdCoupon);

        }catch (ValidationException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

//    endpoint for getting all coupons
    @GetMapping
    public ResponseEntity<List<Coupon>> getAllCoupons(){
        return ResponseEntity.ok(adminCouponService.getAllCoupons());
    }


}

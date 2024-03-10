package com.nderitu.ecommerce.services.admin.coupon;

import com.nderitu.ecommerce.entity.Coupon;

import java.util.List;

public interface AdminCouponService {

    Coupon createCoupon (Coupon coupon);

    List<Coupon> getAllCoupons();
}

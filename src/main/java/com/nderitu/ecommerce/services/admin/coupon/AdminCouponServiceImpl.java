package com.nderitu.ecommerce.services.admin.coupon;

import com.nderitu.ecommerce.entity.Coupon;
import com.nderitu.ecommerce.exceptions.ValidationException;
import com.nderitu.ecommerce.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCouponServiceImpl implements AdminCouponService{

    private final CouponRepository couponRepository;

    public Coupon createCoupon (Coupon coupon){
        if(couponRepository.existsByCode(coupon.getCode())){
            throw new ValidationException( "Coupon code already exists!");
        }
        return couponRepository.save(coupon);
    }
//    method to get all coupons
    public List<Coupon> getAllCoupons(){
        return couponRepository.findAll();
    }
}

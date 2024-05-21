package com.nderitu.ecommerce.repository;

import com.nderitu.ecommerce.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    //method signature to check if coupon exists by the code

    boolean existsByCode(String code);

    Optional<Coupon> findByCode (String code);


}

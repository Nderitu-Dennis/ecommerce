package com.nderitu.ecommerce.services.customer.cart;

import com.nderitu.ecommerce.dto.AddProductInCartDto;
import com.nderitu.ecommerce.dto.CartItemsDto;
import com.nderitu.ecommerce.dto.OrderDto;
import com.nderitu.ecommerce.dto.PlaceOrderDto;
import com.nderitu.ecommerce.entity.*;
import com.nderitu.ecommerce.enums.OrderStatus;
import com.nderitu.ecommerce.exceptions.ValidationException;
import com.nderitu.ecommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.math.RoundingMode.HALF_UP;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CouponRepository couponRepository;

    public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending); //getting the current active order
        Optional<CartItems> optionalCartItems = cartItemsRepository.findByProductIdAndOrderIdAndUserId
                (addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId());

        if (optionalCartItems.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } else {
            Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
            Optional<User> optionalUser = userRepository.findById(addProductInCartDto.getUserId());

            if (optionalProduct.isPresent() && optionalUser.isPresent()) {
                CartItems cart = new CartItems();

                cart.setProduct(optionalProduct.get());
                cart.setPrice(optionalProduct.get().getPrice());
                cart.setQuantity(1);
                cart.setUser(optionalUser.get());
                cart.setOrder(activeOrder);

                CartItems updatedCart = cartItemsRepository.save(cart);

                activeOrder.setTotalAmount(activeOrder.getTotalAmount().add(cart.getPrice()));
                activeOrder.setAmount(activeOrder.getAmount().add(cart.getPrice()));
                activeOrder.getCartItems().add(cart);

                orderRepository.save(activeOrder);

                return ResponseEntity.status(HttpStatus.CREATED).body(cart);

            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or product not found");
            }
        }
    }

    public OrderDto getCartByUserId(Long userId) {
//        get the current active order
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        List<CartItemsDto> cartItemsDtoList = activeOrder.getCartItems().stream().map(CartItems::getCartDto).collect(Collectors.toList());

        OrderDto orderDto = new OrderDto();

        orderDto.setAmount(activeOrder.getAmount());
        orderDto.setId(activeOrder.getId());
        orderDto.setOrderStatus(activeOrder.getOrderStatus());
        orderDto.setDiscount(activeOrder.getDiscount());
        orderDto.setTotalAmount(activeOrder.getTotalAmount());

        orderDto.setCartItems(cartItemsDtoList);

        if(activeOrder.getCoupon() !=null){
            orderDto.setCouponName(activeOrder.getCoupon().getName());
        }

        return orderDto;
    }

    public OrderDto applyCoupon(Long userId, String code){
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        Coupon coupon = couponRepository.findByCode(code).orElseThrow(()-> new ValidationException("Coupon not found"));

        if(couponIsExpired(coupon)){
            throw new ValidationException("Coupon has expired!");
        }

        BigDecimal discountAmount = coupon.getDiscount().divide(BigDecimal.valueOf(100.0), RoundingMode.HALF_UP);
        BigDecimal  netAmount = activeOrder.getTotalAmount().subtract(activeOrder.getTotalAmount().multiply(discountAmount));  //todo accordance to bigdecimal

        activeOrder.setAmount(BigDecimal.valueOf((long)netAmount));
        activeOrder.setDiscount(BigDecimal.valueOf((long)discountAmount));//todo
        activeOrder.setCoupon(coupon);

        orderRepository.save(activeOrder);
        return activeOrder.getOrderDto();
    }

    private boolean couponIsExpired(Coupon coupon){
        Date currentdate = new Date();
        Date expirationDate = coupon.getExpirationDate();

        return expirationDate != null &&  currentdate.after(expirationDate);
    }

    public OrderDto increaseProductQuantity (AddProductInCartDto addProductInCartDto) {

        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
        Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());

        Optional<CartItems> optionalCartItem = cartItemsRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId()
        );

        if (optionalProduct.isPresent() && optionalCartItem.isPresent()) {
            CartItems cartItem = optionalCartItem.get();
            Product product = optionalProduct.get();

            //update the details  -amount & quantity
            activeOrder.setAmount(activeOrder.getAmount().add(product.getPrice()));
            activeOrder.setTotalAmount(activeOrder.getTotalAmount().add(product.getPrice()));

            cartItem.setQuantity(cartItem.getQuantity() + 1);

            if (activeOrder.getCoupon() != null) {
                BigDecimal discountAmount = coupon.getDiscount().divide(BigDecimal.valueOf(100.0), RoundingMode.HALF_UP);
                BigDecimal netAmount = activeOrder.getTotalAmount().subtract(activeOrder.getTotalAmount().multiply(discountAmount));  //todo accordance to bigdecimal

                activeOrder.setAmount(BigDecimal.valueOf((long) netAmount));
                activeOrder.setDiscount(BigDecimal.valueOf((long) discountAmount));//todo
            }


        // if there's no coupon
        cartItemsRepository.save(cartItem);
        orderRepository.save(activeOrder);

        return activeOrder.getOrderDto();
    }
        return null;  //rep BAD_REQUEST
    }

    public OrderDto decreaseProductQuantity (AddProductInCartDto addProductInCartDto) {

        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
        Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());

        Optional<CartItems> optionalCartItem = cartItemsRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId()
        );

        if (optionalProduct.isPresent() && optionalCartItem.isPresent()) {
            CartItems cartItem = optionalCartItem.get();
            Product product = optionalProduct.get();

            //update the details  -amount & quantity
            activeOrder.setAmount(activeOrder.getAmount().subtract(product.getPrice()));
            activeOrder.setTotalAmount(activeOrder.getTotalAmount().subtract(product.getPrice()));

            cartItem.setQuantity(cartItem.getQuantity() - 1);

            if (activeOrder.getCoupon() != null) {
                BigDecimal discountAmount = coupon.getDiscount().divide(BigDecimal.valueOf(100.0), RoundingMode.HALF_UP);
                BigDecimal netAmount = activeOrder.getTotalAmount().subtract(activeOrder.getTotalAmount().multiply(discountAmount));  //todo accordance to bigdecimal

                activeOrder.setAmount(BigDecimal.valueOf((long) netAmount));
                activeOrder.setDiscount(BigDecimal.valueOf((long) discountAmount));//todo
            }


            // if there's no coupon
            cartItemsRepository.save(cartItem);
            orderRepository.save(activeOrder);

            return activeOrder.getOrderDto();
        }
        return null;  //rep BAD_REQUEST
    }

//    placing the order
    public OrderDto placeOrder(PlaceOrderDto placeOrderDto){
//        getting the active order of the user
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(placeOrderDto.getUserId(), OrderStatus.Pending);



    }






}







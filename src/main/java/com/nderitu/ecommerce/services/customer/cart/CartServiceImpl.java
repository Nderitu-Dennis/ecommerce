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
import java.util.UUID;
import java.util.stream.Collectors;


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

            // Check if activeOrder is null before proceeding
            if (activeOrder == null) {
                // Handle the case when activeOrder is null
                // For example, create a new order and save it
                User user = userRepository.findById(addProductInCartDto.getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found"));
                activeOrder = new Order();
                activeOrder.setUser(user);
                activeOrder.setOrderStatus(OrderStatus.Pending);
                activeOrder.setAmount(BigDecimal.ZERO);
                activeOrder.setTotalAmount(BigDecimal.ZERO);
                activeOrder.setDiscount(BigDecimal.ZERO);
                activeOrder = orderRepository.save(activeOrder);
            }

        Optional<CartItems> optionalCartItems = cartItemsRepository.findByProductIdAndOrderIdAndUserId
                (addProductInCartDto.getProductId(),activeOrder.getId(), addProductInCartDto.getUserId());

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

        if (activeOrder.getCoupon() != null) {
            orderDto.setCouponName(activeOrder.getCoupon().getName());
        }

        return orderDto;
    }

    public OrderDto applyCoupon(Long userId, String code) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        Coupon coupon = couponRepository.findByCode(code).orElseThrow(() -> new ValidationException("Coupon not found"));

        if (couponIsExpired(coupon)) {
            throw new ValidationException("Coupon has expired!");
        }

        // Calculate the discount amount
        // BigDecimal discountAmount = activeOrder.getCoupon().getDiscount().divide(BigDecimal.valueOf(100.0), RoundingMode.HALF_UP).multiply(activeOrder.getTotalAmount());

        BigDecimal discountAmount = coupon.getDiscount().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP).multiply(activeOrder.getTotalAmount());// Calculate the net amount todo check this for errors in front end
        BigDecimal netAmount = activeOrder.getTotalAmount().subtract(discountAmount);

// Update the order with the calculated net amount and discount
        activeOrder.setAmount(netAmount);
        activeOrder.setDiscount(discountAmount);
        activeOrder.setCoupon(coupon);

        orderRepository.save(activeOrder);
        return activeOrder.getOrderDto();
    }

    private boolean couponIsExpired(Coupon coupon) {
        Date currentdate = new Date();
        Date expirationDate = coupon.getExpirationDate();

        return expirationDate != null && currentdate.after(expirationDate);
    }

    public OrderDto increaseProductQuantity(AddProductInCartDto addProductInCartDto) {

        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
        Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());

//        getting the cart item
        Optional<CartItems> optionalCartItem = cartItemsRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId()
        );

        if (optionalProduct.isPresent() && optionalCartItem.isPresent()) {
            CartItems cartItem = optionalCartItem.get();
            Product product = optionalProduct.get();

            //update the details  -amount & total amount
            activeOrder.setAmount(activeOrder.getAmount().add(product.getPrice()));
            activeOrder.setTotalAmount(activeOrder.getTotalAmount().add(product.getPrice()));

//            updating cart quantity
            cartItem.setQuantity(cartItem.getQuantity() + 1);


//            checking if we have coupon in the order or not
            if (activeOrder.getCoupon() != null) {
                BigDecimal discountAmount = activeOrder.getCoupon().getDiscount().divide(BigDecimal.valueOf(100.0), RoundingMode.HALF_UP).multiply(activeOrder.getTotalAmount());
//double discountAmount=((activeOrder.getCoupon().getDiscount()/ 100.0)*activeOrder.getTotalAmount());todo
                BigDecimal netAmount = activeOrder.getTotalAmount().subtract(discountAmount);

                activeOrder.setAmount(netAmount);
                activeOrder.setDiscount(discountAmount);
            }

            // if there's no coupon
            cartItemsRepository.save(cartItem);
            orderRepository.save(activeOrder);

            return activeOrder.getOrderDto();
        }
        return null;  //rep BAD_REQUEST if product is not present
    }
    public OrderDto decreaseProductQuantity(AddProductInCartDto addProductInCartDto) {

        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
        Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());

        Optional<CartItems> optionalCartItem = cartItemsRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId()
        );

        if (optionalProduct.isPresent() && optionalCartItem.isPresent()) {
            CartItems cartItem = optionalCartItem.get();
            Product product = optionalProduct.get();

            //update the details  -amount & total amount
            activeOrder.setAmount(activeOrder.getAmount().subtract(product.getPrice()));
            activeOrder.setTotalAmount(activeOrder.getTotalAmount().subtract(product.getPrice()));

            cartItem.setQuantity(cartItem.getQuantity() - 1);  //decreasing quantity on cart

            if (activeOrder.getCoupon() != null) {
                BigDecimal discountAmount = activeOrder.getCoupon().getDiscount().divide(BigDecimal.valueOf(100.0), RoundingMode.HALF_UP).multiply(activeOrder.getTotalAmount());

                // double discountAmount=((activeOrder.getCoupon().getDiscount()/100.0)*activeOrder.getTotalAmount());todo
                BigDecimal netAmount = activeOrder.getTotalAmount().subtract(discountAmount);

                activeOrder.setAmount( netAmount);
                activeOrder.setDiscount(discountAmount);
            }


            // if there's no coupon
            cartItemsRepository.save(cartItem);
            orderRepository.save(activeOrder);

            return activeOrder.getOrderDto();
        }
        return null;  //rep BAD_REQUEST
    }

    //    placing the order
    public OrderDto placeOrder(PlaceOrderDto placeOrderDto) {
//        getting the active order of the user
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(placeOrderDto.getUserId(), OrderStatus.Pending);
//getting the user
        Optional<User> optionalUser = userRepository.findById(placeOrderDto.getUserId());
        if (optionalUser.isPresent()) {
//            if user is present then we'll update details in the active order
            activeOrder.setOrderDescription(placeOrderDto.getOrderDescription());
            activeOrder.setAddress(placeOrderDto.getAddress());
            activeOrder.setDate(new Date());
            activeOrder.setOrderStatus(OrderStatus.Placed);
            activeOrder.setTrackingId(UUID.randomUUID());

            orderRepository.save(activeOrder);

            Order order = new Order();
            order.setAmount(BigDecimal.ZERO);
            order.setTotalAmount(BigDecimal.ZERO);
            order.setDiscount(BigDecimal.ZERO);
            order.setUser(optionalUser.get());
            order.setOrderStatus(OrderStatus.Pending);
            orderRepository.save(order);

            return activeOrder.getOrderDto(); //converts the active order into the Dto
        }
        return null;

    }
    //    API to get orders for customers
    public List<OrderDto> getMyPlacedOrders(Long userId){

        return orderRepository.findByUserIdAndOrderStatusIn(userId,
                List.of(OrderStatus.Placed,OrderStatus.Shipped,
                        OrderStatus.Delivered)).stream().map(Order::getOrderDto).collect(Collectors.toList());

    }

}



















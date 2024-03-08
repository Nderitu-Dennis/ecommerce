package com.nderitu.ecommerce.services.customer.cart;

import com.nderitu.ecommerce.dto.AddProductInCartDto;
import com.nderitu.ecommerce.entity.CartItems;
import com.nderitu.ecommerce.entity.Order;
import com.nderitu.ecommerce.entity.Product;
import com.nderitu.ecommerce.entity.User;
import com.nderitu.ecommerce.enums.OrderStatus;
import com.nderitu.ecommerce.repository.CartItemsRepository;
import com.nderitu.ecommerce.repository.OrderRepository;
import com.nderitu.ecommerce.repository.ProductRepository;
import com.nderitu.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;
    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto){
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending); //getting the current active order
        Optional<CartItems> optionalCartItems =cartItemsRepository.findByProductIdAndOrderIdAndUserId
                (addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId());

        if(optionalCartItems.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }else{
            Optional<Product> optionalProduct =productRepository.findById(addProductInCartDto.getProductId());
            Optional<User> optionalUser = userRepository.findById(addProductInCartDto.getUserId());

            if(optionalProduct.isPresent() && optionalUser.isPresent()) {
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

            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or product not found");
            }

        }

    }
}

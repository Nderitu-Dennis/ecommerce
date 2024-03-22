package com.nderitu.ecommerce.services.customer.review;

import com.nderitu.ecommerce.dto.OrderedProductResponseDto;
import com.nderitu.ecommerce.dto.ProductDto;
import com.nderitu.ecommerce.dto.ReviewDto;
import com.nderitu.ecommerce.entity.*;
import com.nderitu.ecommerce.repository.OrderRepository;
import com.nderitu.ecommerce.repository.ProductRepository;
import com.nderitu.ecommerce.repository.ReviewRepository;
import com.nderitu.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

//    injecting order repository
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public OrderedProductResponseDto getOrderedProductsDetailsByOrderId(Long orderId) {
        Optional <Order> optionalOrder= orderRepository.findById(orderId);
        OrderedProductResponseDto orderedProductResponseDto =new OrderedProductResponseDto();
        if(optionalOrder.isPresent()){
            orderedProductResponseDto.setOrderAmount(optionalOrder.get().getAmount());

            List<ProductDto> productDtoList = new ArrayList<>();
            for(CartItems cartItems: optionalOrder.get().getCartItems()){
                ProductDto productDto = new ProductDto();
                //            setting the product details

                productDto.setId(cartItems.getProduct().getId());
                productDto.setName(cartItems.getProduct().getName());
                productDto.setPrice(cartItems.getPrice());
                productDto.setQuantity(Long.valueOf(cartItems.getQuantity()));

                //setting the image
                productDto.setByteImg(cartItems.getProduct().getImg());

//                adding this productDto into productDto list
                productDtoList.add(productDto);

            }
            orderedProductResponseDto.setProductDtoList(productDtoList);
        }
        return orderedProductResponseDto;

        }

        public ReviewDto giveReview(ReviewDto reviewDto) throws IOException {
        //getting the product and user

            Optional<Product> optionalProduct = productRepository.findById(reviewDto.getProductId());
            Optional<User> optionalUser = userRepository.findById(reviewDto.getUserId());

//            check if only optional product and optional user then only create the review
            if(optionalProduct.isPresent() && optionalUser.isPresent()){
                Review review = new Review();

                review.setRating(reviewDto.getRating());
                review.setDescription(reviewDto.getDescription());
                review.setUser(optionalUser.get());
                review.setProduct(optionalProduct.get());
                review.setImg(reviewDto.getImg().getBytes());

                return reviewRepository.save(review).getDto();

            }
            return null;




        }


}

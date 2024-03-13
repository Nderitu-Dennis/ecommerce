package com.nderitu.ecommerce.services.customer.review;

import com.nderitu.ecommerce.dto.OrderedProductResponseDto;
import com.nderitu.ecommerce.dto.ProductDto;
import com.nderitu.ecommerce.entity.CartItems;
import com.nderitu.ecommerce.entity.Order;
import com.nderitu.ecommerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

//    injecting order repository
    private final OrderRepository orderRepository;

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
}

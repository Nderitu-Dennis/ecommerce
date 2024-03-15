package com.nderitu.ecommerce.services.auth;

import com.nderitu.ecommerce.dto.SignupRequest;
import com.nderitu.ecommerce.dto.UserDto;
import com.nderitu.ecommerce.entity.Order;
import com.nderitu.ecommerce.entity.User;
import com.nderitu.ecommerce.enums.OrderStatus;
import com.nderitu.ecommerce.enums.UserRole;
import com.nderitu.ecommerce.repository.OrderRepository;
import com.nderitu.ecommerce.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service

public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private OrderRepository orderRepository;

    public UserDto createUser(SignupRequest signupRequest) {
        //create a new user
        User user = new User();

        //put user details into the user class
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword())); //encode the password first
        user.setRole(UserRole.CUSTOMER);
        User createdUser = userRepository.save(user); //save the new user into the DB

        Order order =new Order();
        order.setAmount(BigDecimal.ZERO);
        order.setTotalAmount(BigDecimal.ZERO);
        order.setDiscount(BigDecimal.ZERO);
        order.setUser(createdUser);
        order.setOrderStatus(OrderStatus.Pending);
        orderRepository.save(order);

        UserDto userDto = new UserDto();
        userDto.setId(createdUser.getId());  //setting id for the created user

        return userDto;
    }


//check if there is a user with the same email/existing email
    public Boolean hasUserWithEmail(String email){

        return userRepository.findFirstByEmail(email).isPresent();
//        checks if we have user with the same email
    }

    //creating the admin account

    @PostConstruct //this method will be called automatically after the constructor invoked after dependency injection b4 the bean is put into service
    public void createAdminAccount(){
        User adminAccount=userRepository.findByRole(UserRole.ADMIN);
        if(null==adminAccount){   //if only the admin is null then we create a new admin otherwise skip
            User user=new User();
            user.setEmail("admin@test.com");
            user.setName("admin");
            user.setRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user); //save the admin
        }
    }
}

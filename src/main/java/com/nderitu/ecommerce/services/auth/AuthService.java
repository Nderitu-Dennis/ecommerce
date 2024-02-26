package com.nderitu.ecommerce.services.auth;

import com.nderitu.ecommerce.dto.SignupRequest;
import com.nderitu.ecommerce.dto.UserDto;

public interface AuthService {

    UserDto createUser(SignupRequest signupRequest);

    Boolean hasUserWithEmail(String email);
}

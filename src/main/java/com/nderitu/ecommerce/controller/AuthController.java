package com.nderitu.ecommerce.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.nderitu.ecommerce.dto.AuthenticationRequest;
import com.nderitu.ecommerce.dto.SignupRequest;
import com.nderitu.ecommerce.dto.UserDto;
import com.nderitu.ecommerce.entity.User;
import com.nderitu.ecommerce.repository.UserRepository;
import com.nderitu.ecommerce.services.auth.AuthService;
import com.nderitu.ecommerce.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    public static final String  TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";

    //creating object for authService
    private final AuthService authService;
    //login API call

    /*@PostMapping("/authenticate")  //sends data to the server
    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws IOException, JSONException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        }
        //catching the wrong credentials exception
        catch (BadCredentialsException e) {
            throw new BadCredentialsException("Wrong username or password");
        }
        //user credentials are correct,so lets get the user's details

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        Optional<User> optionalUser = userRepository.findByEmail(userDetails.getUsername());

        //calling JwtUtil to generate the token
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        if (optionalUser.isPresent()) {
            //if user is present then add the details in the response
            response.getWriter().write(new JSONObject()
                            .put("userId", optionalUser.get().getId())
                            .put("role", optionalUser.get().getRole())
                            .toString()
            );
            response.addHeader("Access-Control-Expose-Headers","Authorization");
            response.addHeader("Access-Control-Allow-Headers","Authorization, X-PINGOTHER ,Origin, " +  "X-Requested-With, Content-Type, Accept, X-Custom-header ");

            response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
        }    }*/
    @PostMapping("/authenticate")
    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws IOException, JSONException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Wrong username or password");
        }

        // User credentials are correct, so let's get the user's details
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        Optional<User> optionalUser = userRepository.findByEmail(userDetails.getUsername());

        // Generate the JWT token only if the user is present
        if (optionalUser.isPresent()) {
            // Generate the JWT token with a non-null key
            final String jwt = jwtUtil.generateToken(userDetails.getUsername());

            // Add user details to the response
            JSONObject responseObject = new JSONObject()
                    .put("userId", optionalUser.get().getId())
                    .put("role", optionalUser.get().getRole());

            // Write the response and add JWT token to the headers
            response.getWriter().write(responseObject.toString());
            response.addHeader("Access-Control-Expose-Headers", "Authorization");
            response.addHeader("Access-Control-Allow-Headers", "Authorization, X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-header");
            response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
        }
    }


    //endpoint for the  sign-up API
    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
        /*ResponseEntity is a class that rep a HTTP Response,allowing to control the HTTP
        * header,body and status code*/

        if(authService.hasUserWithEmail(signupRequest.getEmail())){
            return new ResponseEntity<>("User already exists", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto userDto = authService.createUser(signupRequest);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }}




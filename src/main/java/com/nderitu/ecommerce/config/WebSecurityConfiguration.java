package com.nderitu.ecommerce.config;

import com.nderitu.ecommerce.filters.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static javax.management.Query.and;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class WebSecurityConfiguration {

    private final JwtRequestFilter authFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /*security filter chain is a series pf filters that intercept incoming HTTP requests and
        * outgoing responses to enforce security measures.Ensures that only authorized users can
        * access protected resources */
        //todo configure with spring 3.0.0



               /* .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // If CSRF protection is needed
                 // Or omit this block if disabling CSRF
                .authorizeRequests()
                .requestMatchers("/authenticate", "/sign-up", "/order/**")
                .permitAll()
                .and()
                .securityMatcher("/api/**")
                .authenticated()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER) // For stateless configuration
                .addFilterAt(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();*/
        return http    .csrf()/* Cross-Site Request Forgery(a type of website attack) where a malicious
                    website tricks a browser to make an unintended HTTP request to another site where
                    the user is authenticated*/
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/authenticate", "/sign-up", "/order/**")
                .permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/**")
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();


        //todo research on this above part
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    }




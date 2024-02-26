package com.nderitu.ecommerce.config;

//a class for cors so we can call API's from the angular application

/*CORS Cross-Origin Resource Sharing
* a mechanism that allows web servers to specify which origins are permitted to access
* the resources of a web page
* */

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.FileFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Filter;
import java.util.logging.LogRecord;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)

public class SimpleCorsFilter implements Filter {

    /*CORS filter is implemented on the server side to enforce CORS policies  by inspecting
     * and handling incoming HTTP requests
     * CORS-*/

   @Value("${app.client.url}")
   private String clientAppUrl="";
//todo review here

    public SimpleCorsFilter() {
    }

    //ALLOWING ALL API CALLS FROM THE FRONT END APP

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws
            IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        Map<String, String> map = new HashMap<>();
        String originHeader = request.getHeader("origin");
        response.setHeader("Access-Control-Allow-Origin", originHeader);
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
    }

    public void init(FilterConfig filterConfig) {
        //todo check this method

    }
    public void destroy() {
        //todo check this method

    }
    @Override
    public boolean isLoggable(LogRecord logRecord) {
        return false;
    }
}





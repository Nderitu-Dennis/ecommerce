package com.nderitu.ecommerce.config;

//a class for cors so we can call API's from the angular application

/*CORS Cross-Origin Resource Sharing
* a mechanism that allows web servers to specify which origins are permitted to access
* the resources of a web page
* */

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCorsFilter implements Filter {

    @Value("${app.client.url}")
//    its value will be injected from the application properties file
//     specifies the URL of the client application that is allowed to make cross-origin requests.
    private String clientAppUrl;

    @Override
    /* doFilter method intercepts incoming requests and modifies the response headers to
    enable CORS if the request origin matches the client application URL.*/
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        String originHeader = request.getHeader("Origin");
        if (originHeader != null && originHeader.startsWith(clientAppUrl)) {
            response.setHeader("Access-Control-Allow-Origin", originHeader);
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Max-Age", "3600");
        }

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
//        no initialization logic needed for this filter.hence empty
    }

    @Override
    public void destroy() {
//        method is empty, as there's no cleanup logic needed for this filter.
//        method allows the filter to release any resources it may have acquired during its lifetime,
//        such as closing database connections or releasing file handles.
    }
}

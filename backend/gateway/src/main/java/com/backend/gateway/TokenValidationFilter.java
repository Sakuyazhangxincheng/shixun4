package com.backend.gateway;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

//@Component
public class TokenValidationFilter implements WebFilter {

    private static final String SECRETE_KEY = "DarLinDeShiXun123456789012345678901234567890";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        //response.addHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        System.out.println(exchange);

        // 获取请求路径
        String path = request.getPath().value();

        return chain.filter(exchange);
       /* // 判断是否为注册或登录相关的请求，如果是，则直接允许请求服务
        if (path.startsWith("/users/register") || path.startsWith("/users/login") || path.startsWith("/users/sendEmail")
                || path.startsWith("/users/verifyCode") || path.startsWith("/users/loginByUsername") ||
<<<<<<< HEAD
                path.startsWith("/users/registerWithoutCode") ||path.startsWith("/users/getAllUsers") ) {
=======
                path.startsWith("/users/registerWithoutCode") || path.startsWith("/users/forgetPassword")) {
>>>>>>> eb5563573dd551e8000603a12e235b7fc5ae3d30
            return chain.filter(exchange);
        }

        // 验证token
        String token = extractTokenFromRequest(request);
        if (token == null) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        // 解析和验证token
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRETE_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            // 在请求的attributes中添加用户信息，以便后续处理
            exchange.getAttributes().put("userName", claims.getSubject());
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        return chain.filter(exchange);*/
    }

    private String extractTokenFromRequest(ServerHttpRequest request) {
        // 从请求头中获取token
        String authHeader = request.getHeaders().getFirst("Authorization");
        System.out.println(authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}


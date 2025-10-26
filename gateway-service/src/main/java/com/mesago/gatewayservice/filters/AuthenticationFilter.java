package com.mesago.gatewayservice.filters;

import com.mesago.gatewayservice.config.jwt.JwtUtils;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

@Slf4j
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    private final List<String> publicApiEndpoints = List.of(
            "/api/auth/login"
    );

    public AuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        log.info("Gateway Filter: Petición entrante a {}", request.getRequestURI());

        Predicate<HttpServletRequest> isPublic =
                req -> publicApiEndpoints.stream().anyMatch(uri -> req.getRequestURI().startsWith(uri));

        if (isPublic.test(request)) {
            log.info("Gateway Filter: Ruta pública, permitiendo acceso sin validación de token.");
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            log.warn("Gateway Filter: Petición rechazada. Encabezado de autorización ausente o incorrecto.");
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Se requiere un token de autorización.");
            return;
        }

        final String token = authHeader.substring(7);

        try {
            jwtUtils.validateToken(token);
        } catch (JwtException e) {
            log.error("Gateway Filter: Petición rechazada. Token JWT inválido: {}", e.getMessage());
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Token JWT inválido o expirado.");
            return;
        }

        log.info("Gateway Filter: Token JWT válido. Petición autorizada para continuar.");
        filterChain.doFilter(request, response);

    }

}

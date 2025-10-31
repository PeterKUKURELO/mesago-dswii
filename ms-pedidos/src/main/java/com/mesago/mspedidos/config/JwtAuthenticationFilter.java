package com.mesago.mspedidos.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();
        logger.info("🔍 [JWT FILTER] → Request recibido: {} {}", method, path);

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null) {
            logger.warn("⚠️  [JWT FILTER] No se encontró header Authorization en la petición.");
        } else if (!authHeader.startsWith("Bearer ")) {
            logger.warn("⚠️  [JWT FILTER] El header Authorization no empieza con 'Bearer '. Valor: {}", authHeader);
        } else {
            String jwt = authHeader.substring(7);
            logger.info("🪪 [JWT FILTER] Token detectado: {}...", jwt.substring(0, Math.min(15, jwt.length())));

            try {
                if (jwtUtil.validateToken(jwt)) {
                    String username = jwtUtil.getUsernameFromToken(jwt);
                    List<String> roles = jwtUtil.getRolesFromToken(jwt);

                    logger.info("✅ [JWT FILTER] Token válido para usuario: {}", username);
                    logger.info("🧩 [JWT FILTER] Roles detectados: {}", roles);

                    List<SimpleGrantedAuthority> authorities = roles.stream()
                            .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    var authToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    logger.info("🔐 [JWT FILTER] Autenticación establecida en el contexto de seguridad.");
                } else {
                    logger.warn("🚫 [JWT FILTER] Token inválido o expirado.");
                }
            } catch (Exception e) {
                logger.error("💥 [JWT FILTER] Error al procesar el token: {}", e.getMessage(), e);
            }
        }

        filterChain.doFilter(request, response);
    }
}
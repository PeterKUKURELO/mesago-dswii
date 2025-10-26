package com.mesago.gatewayservice.config.jwt;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.Arrays;
import java.util.List;

@Configuration
public class GatewayMvcConfig {

    // ==================== URLS DE MICRO SERVICIOS ====================
    private static final String MS_AUTH = "http://ms-auth:8081";
    private static final String MS_CLIENTES = "http://ms-clientes:8082";
    private static final String MS_FACTURACION = "http://ms-facturacion:8083";
    private static final String MS_INVENTARIO = "http://ms-inventario:8084";
    private static final String MS_PEDIDOS = "http://ms-pedidos:8085";
    private static final String MS_PROVEEDORES = "http://ms-proveedores:8086";
    private static final String MS_RESERVAS = "http://ms-reservas:8087";
    private static final String MS_CATALOGO_MENU = "http://ms-catalogo-menu:8088";

    // ==================== ENRUTAMIENTO ====================
    @Bean
    public RouterFunction<ServerResponse> gatewayRouter() {
        return GatewayRouterFunctions.route()

                // --- Autenticación y Usuarios ---
                .route(req -> matches(req.path(), "/api/auth", "/api/users", "/api/admin"),
                        HandlerFunctions.http(MS_AUTH))

                // --- Clientes ---
                .route(req -> matches(req.path(), "/api/clientes"),
                        HandlerFunctions.http(MS_CLIENTES))

                // --- Facturación ---
                .route(req -> matches(req.path(), "/api/facturacion"),
                        HandlerFunctions.http(MS_FACTURACION))

                // --- Inventario ---
                .route(req -> matches(req.path(), "/api/insumos"),
                        HandlerFunctions.http(MS_INVENTARIO))

                // --- Pedidos ---
                .route(req -> matches(req.path(), "/api/pedidos", "/api/detalles", "/api/mesas"),
                        HandlerFunctions.http(MS_PEDIDOS))

                // --- Proveedores ---
                .route(req -> matches(req.path(), "/api/proveedores"),
                        HandlerFunctions.http(MS_PROVEEDORES))

                // --- Reservas ---
                .route(req -> matches(req.path(), "/api/reservas"),
                        HandlerFunctions.http(MS_RESERVAS))

                // --- Catálogo / Menú ---
                .route(req -> matches(req.path(), "/api/categorias", "/api/menu"),
                        HandlerFunctions.http(MS_CATALOGO_MENU))

                .build();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of(
                "http://localhost:5173",
                "http://127.0.0.1:5173"
        ));

        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true);
        config.addExposedHeader("Authorization");
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

    private boolean matches(String path, String... prefixes) {
        for (String prefix : prefixes) {
            if (path.startsWith(prefix)) return true;
        }
        return false;
    }
}
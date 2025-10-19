package com.mesago.gatewayservice.config.jwt;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class GatewayMvcConfig {

    @Bean
    public RouterFunction<ServerResponse> gatewayRouter() {

        return GatewayRouterFunctions.route()
                // Autenticación
                .route(request -> request.path().startsWith("/api/auth")
                                || request.path().startsWith("/api/users")
                                || request.path().startsWith("/api/admin"),
                        HandlerFunctions.http("http://localhost:8081"))

                // Pedidos
                .route(request -> request.path().startsWith("/api/pedidos"),
                        HandlerFunctions.http("http://localhost:8082"))

                // Inventario
                .route(request -> request.path().startsWith("/api/insumos"),
                        HandlerFunctions.http("http://localhost:8083"))

                // Clientes
                .route(request -> request.path().startsWith("/api/clientes"),
                        HandlerFunctions.http("http://localhost:8084"))

                .build();
    }
}

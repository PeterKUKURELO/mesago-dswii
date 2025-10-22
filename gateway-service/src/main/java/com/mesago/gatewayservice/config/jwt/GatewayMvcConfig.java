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
                .route(request -> request.path().startsWith("/api/auth")
                                || request.path().startsWith("/api/users")
                                || request.path().startsWith("/api/admin"),
                        HandlerFunctions.http("http://ms-auth:8081"))

                .route(request -> request.path().startsWith("/api/insumos"),
                        HandlerFunctions.http("http://ms-inventario:8083"))


                .route(request -> request.path().startsWith("/api/proveedores"),
                        HandlerFunctions.http("http://ms-proveedores:8084"))
                .build();
    }
}

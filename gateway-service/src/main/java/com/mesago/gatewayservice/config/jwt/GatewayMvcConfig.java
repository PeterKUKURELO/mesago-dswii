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
                // ms-auth (Puerto: 8081)
                .route(request -> request.path().startsWith("/api/auth")
                                || request.path().startsWith("/api/users")
                                || request.path().startsWith("/api/admin"),
                        HandlerFunctions.http("http://ms-auth:8081"))

                // ms-clientes (Puerto: 8082)
                .route(request -> request.path().startsWith("/api/clientes"),
                        HandlerFunctions.http("http://ms-clientes:8082"))

                // ms-facturacion (Puerto: 8083)
                .route(request -> request.path().startsWith("/api/facturacion"),
                        HandlerFunctions.http("http://ms-facturacion:8083"))

                // ms-inventario (Puerto: 8084)
                .route(request -> request.path().startsWith("/api/insumos"),
                        HandlerFunctions.http("http://ms-inventario:8084"))

                // ms-pedidos (Puerto: 8085)
                .route(request -> request.path().startsWith("/api/pedidos")
                                || request.path().startsWith("/api/detalles")
                                || request.path().startsWith("/api/mesas"),
                        HandlerFunctions.http("http://ms-pedidos:8085"))

                // ms-proveedores (Puerto: 8086)
                .route(request -> request.path().startsWith("/api/proveedores"),
                        HandlerFunctions.http("http://ms-proveedores:8086"))

                // ms-reservas (Puerto: 8087)
                .route(request -> request.path().startsWith("/api/reservas"),
                        HandlerFunctions.http("http://ms-reservas:8087"))

                //ms-catalogo-menu (Puerto: 8088)
                .route(request -> request.path().startsWith("/api/categorias")
                                || request.path().startsWith("/api/menu"),
                        HandlerFunctions.http("http://ms-catalogo-menu:8088"))
                .build();
    }
}

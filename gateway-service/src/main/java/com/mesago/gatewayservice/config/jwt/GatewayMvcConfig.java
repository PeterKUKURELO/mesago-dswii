package com.mesago.gatewayservice.config.jwt;

import com.mesago.gatewayservice.config.SwaggerUiConfigProperties;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.ProxyExchangeHandlerFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;
import java.util.Map;

@Configuration
public class GatewayMvcConfig {

    private static final String MS_AUTH = "http://ms-auth:8081";
    private static final String MS_CLIENTES = "http://ms-clientes:8082";
    private static final String MS_FACTURACION = "http://ms-facturacion:8083";
    private static final String MS_INVENTARIO = "http://ms-inventario:8084";
    private static final String MS_PEDIDOS = "http://ms-pedidos:8085";
    private static final String MS_PROVEEDORES = "http://ms-proveedores:8086";
    private static final String MS_RESERVAS = "http://ms-reservas:8087";
    private static final String MS_CATALOGO_MENU = "http://ms-catalogo-menu:8088";

    private final SwaggerUiConfigProperties swaggerUiConfigProperties;

    public GatewayMvcConfig(SwaggerUiConfigProperties swaggerUiConfigProperties) {
        this.swaggerUiConfigProperties = swaggerUiConfigProperties;
    }

    @Bean
    public RouterFunction<ServerResponse> gatewayRouter() {
        return GatewayRouterFunctions.route()
                .route(RequestPredicates.path("/swagger-config/urls.json"),
                        request -> ServerResponse.ok().body(Map.of("urls", swaggerUiConfigProperties.getUrls()))
                )

                .route(RequestPredicates.path("/v3/api-docs/auth"), HandlerFunctions.http(MS_AUTH + "/v3/api-docs"))
                .route(RequestPredicates.path("/v3/api-docs/clientes"), HandlerFunctions.http(MS_CLIENTES + "/v3/api-docs"))
                .route(RequestPredicates.path("/v3/api-docs/facturacion"), HandlerFunctions.http(MS_FACTURACION + "/v3/api-docs"))
                .route(RequestPredicates.path("/v3/api-docs/inventario"), HandlerFunctions.http(MS_INVENTARIO + "/v3/api-docs"))
                .route(RequestPredicates.path("/v3/api-docs/pedidos"), HandlerFunctions.http(MS_PEDIDOS + "/v3/api-docs"))
                .route(RequestPredicates.path("/v3/api-docs/proveedores"), HandlerFunctions.http(MS_PROVEEDORES + "/v3/api-docs"))
                .route(RequestPredicates.path("/v3/api-docs/reservas"), HandlerFunctions.http(MS_RESERVAS + "/v3/api-docs"))
                .route(RequestPredicates.path("/v3/api-docs/catalogo-menu"), HandlerFunctions.http(MS_CATALOGO_MENU + "/v3/api-docs"))

                .route(req -> matches(req.path(), "/api/auth", "/api/users", "/api/admin"), HandlerFunctions.http(MS_AUTH))
                .route(req -> matches(req.path(), "/api/clientes"), HandlerFunctions.http(MS_CLIENTES))
                .route(req -> matches(req.path(), "/api/facturacion"), HandlerFunctions.http(MS_FACTURACION))
                .route(req -> matches(req.path(), "/api/insumos"), HandlerFunctions.http(MS_INVENTARIO))
                .route(req -> matches(req.path(), "/api/pedidos", "/api/detalles", "/api/mesas"), HandlerFunctions.http(MS_PEDIDOS))
                .route(req -> matches(req.path(), "/api/proveedores"), HandlerFunctions.http(MS_PROVEEDORES))
                .route(req -> matches(req.path(), "/api/reservas"), HandlerFunctions.http(MS_RESERVAS))
                .route(req -> matches(req.path(), "/api/categorias", "/api/menu"), HandlerFunctions.http(MS_CATALOGO_MENU))
                .build();
    }

    private boolean matches(String path, String... prefixes) {
        for (String prefix : prefixes) {
            if (path.startsWith(prefix)) return true;
        }
        return false;
    }
}
package com.mesago.gatewayservice.config;


import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("ms-auth")
                .pathsToMatch("/api/auth/**", "/api/users/**", "/api/admin/**")
                .build();
    }

    @Bean
    public GroupedOpenApi clientesApi() {
        return GroupedOpenApi.builder()
                .group("ms-clientes")
                .pathsToMatch("/api/clientes/**")
                .build();
    }

    @Bean
    public GroupedOpenApi facturacionApi() {
        return GroupedOpenApi.builder()
                .group("ms-facturacion")
                .pathsToMatch("/api/facturacion/**")
                .build();
    }

    @Bean
    public GroupedOpenApi inventarioApi() {
        return GroupedOpenApi.builder()
                .group("ms-inventario")
                .pathsToMatch("/api/insumos/**")
                .build();
    }

    @Bean
    public GroupedOpenApi pedidosApi() {
        return GroupedOpenApi.builder()
                .group("ms-pedidos")
                .pathsToMatch("/api/pedidos/**", "/api/detalles/**", "/api/mesas/**")
                .build();
    }

    @Bean
    public GroupedOpenApi proveedoresApi() {
        return GroupedOpenApi.builder()
                .group("ms-proveedores")
                .pathsToMatch("/api/proveedores/**")
                .build();
    }

    @Bean
    public GroupedOpenApi reservasApi() {
        return GroupedOpenApi.builder()
                .group("ms-reservas")
                .pathsToMatch("/api/reservas/**")
                .build();
    }

    @Bean
    public GroupedOpenApi catalogoMenuApi() {
        return GroupedOpenApi.builder()
                .group("ms-catalogo-menu")
                .pathsToMatch("/api/categorias/**", "/api/menu/**")
                .build();
    }
}

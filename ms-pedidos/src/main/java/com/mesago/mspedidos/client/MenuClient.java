package com.mesago.mspedidos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@FeignClient(name = "ms-catalogo-menu", url = "http://localhost:8084/api/menu")
public interface MenuClient {

    // Este endpoint debe existir en el ms-catalogo-menu
    @GetMapping("/{id}")
    MenuResponse obtenerMenuPorId(@PathVariable Long id);

    // DTO local para recibir datos del ms-catalogo-menu
    record MenuResponse(Long idMenu, String nombre, String descripcion, BigDecimal precio, String estado) {}
}
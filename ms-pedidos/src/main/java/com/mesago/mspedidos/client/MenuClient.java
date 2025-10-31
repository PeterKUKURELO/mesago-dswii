package com.mesago.mspedidos.client;

import com.mesago.mspedidos.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.Map;

@FeignClient(
        name = "ms-catalogo-menu",
        url = "http://ms-catalogo-menu:8088/api/menu",
        configuration = FeignClientConfig.class
)
public interface MenuClient {
    @GetMapping("/{id}")
    Map<String, Object> obtenerMenuPorId(@PathVariable("id") Long idMenu);
}

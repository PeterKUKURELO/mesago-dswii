package com.mesago.msfacturacion.api.controller;

import com.mesago.msfacturacion.aplication.services.FacturacionService;
import com.mesago.msfacturacion.domain.entities.Facturacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturacion")
@CrossOrigin(origins = "*")
public class FacturacionController {

    @Autowired
    private FacturacionService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public List<Facturacion> listar() {
        return service.listar();
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public Facturacion obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public Facturacion crear(@RequestBody Facturacion facturacion) {
        return service.guardar(facturacion);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public Facturacion actualizar(@PathVariable Long id, @RequestBody Facturacion facturacion) {
        return service.actualizar(id, facturacion);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
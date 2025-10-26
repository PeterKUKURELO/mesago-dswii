package com.mesago.msproveedores.api.controller;

import com.mesago.msproveedores.application.services.ProveedorService;
import com.mesago.msproveedores.domain.entities.Proveedor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorService service;

    public ProveedorController(ProveedorService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public List<Proveedor> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public Proveedor obtener(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public Proveedor crear(@RequestBody Proveedor proveedor) {
        return service.guardar(proveedor);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public Proveedor actualizar(@PathVariable Long id, @RequestBody Proveedor proveedor) {
        return service.actualizar(id, proveedor);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
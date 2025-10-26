package com.mesago.api.controller;

import com.mesago.application.services.InsumoService;
import com.mesago.domain.entities.Insumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insumos")
@CrossOrigin(origins = "*")
public class InsumoController {

    @Autowired
    private InsumoService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public List<Insumo> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public Insumo obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public Insumo crear(@RequestBody Insumo insumo) {
        return service.guardar(insumo);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public Insumo actualizar(@PathVariable Long id, @RequestBody Insumo insumo) {
        insumo.setIdInsumo(id);
        return service.guardar(insumo);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
package com.mesago.msinventario.api.controller;

import com.mesago.msinventario.application.services.InsumoService;
import com.mesago.msinventario.domain.entities.Insumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insumos")
@CrossOrigin(origins = "*")
public class InsumoController {

    @Autowired
    private InsumoService service;

    @GetMapping
    public List<Insumo> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Insumo obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PostMapping
    public Insumo crear(@RequestBody Insumo insumo) {
        return service.guardar(insumo);
    }

    @PutMapping("/{id}")
    public Insumo actualizar(@PathVariable Long id, @RequestBody Insumo insumo) {
        insumo.setIdInsumo(id);
        return service.guardar(insumo);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
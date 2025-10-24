package com.mesago.mspedidos.controller;

import com.mesago.mspedidos.entity.Mesa;
import com.mesago.mspedidos.service.MesaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mesas")
public class MesaController {

    private final MesaService mesaService;

    public MesaController(MesaService mesaService) {
        this.mesaService = mesaService;
    }

    @GetMapping
    public ResponseEntity<List<Mesa>> listar() {
        return ResponseEntity.ok(mesaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mesa> obtenerPorId(@PathVariable Long id) {
        return mesaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Mesa> guardar(@RequestBody Mesa mesa) {
        return ResponseEntity.ok(mesaService.guardar(mesa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mesa> actualizar(@PathVariable Long id, @RequestBody Mesa mesa) {
        return mesaService.obtenerPorId(id)
                .map(existing -> {
                    mesa.setIdMesa(id);
                    return ResponseEntity.ok(mesaService.guardar(mesa));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mesaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}


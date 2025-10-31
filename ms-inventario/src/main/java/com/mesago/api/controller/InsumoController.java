package com.mesago.api.controller;

import com.mesago.application.services.InsumoService;
import com.mesago.domain.entities.Insumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insumos")
public class InsumoController {

    @Autowired
    private InsumoService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<Insumo>>> listar() {
        List<Insumo> insumos = service.listar();
        if (insumos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ApiResponse<>(false, "No hay insumos registrados.", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de insumos obtenida correctamente.", insumos));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<ApiResponse<Insumo>> obtenerPorId(@PathVariable Long id) {
        Insumo insumo = service.obtenerPorId(id);
        if (insumo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "No se encontró el insumo con ID: " + id, null));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Insumo obtenido correctamente.", insumo));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<ApiResponse<Insumo>> crear(@RequestBody Insumo insumo) {
        // Validaciones básicas
        if (insumo.getNombre() == null || insumo.getNombre().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "El nombre del insumo es obligatorio.", null));
        }
        if (insumo.getUnidadMedida() == null || insumo.getUnidadMedida().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "La unidad de medida es obligatoria.", null));
        }
        if (insumo.getStockMinimo() == null || insumo.getStockMinimo() < 0) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "El stock mínimo debe ser un número válido (>= 0).", null));
        }
        if (insumo.getStockActual() == null || insumo.getStockActual() < 0) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "El stock actual debe ser un número válido (>= 0).", null));
        }

        insumo.setEstado("Activo");
        Insumo nuevo = service.guardar(insumo);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Insumo creado exitosamente.", nuevo));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<ApiResponse<Insumo>> actualizar(@PathVariable Long id, @RequestBody Insumo insumo) {
        Insumo existente = service.obtenerPorId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "No se encontró el insumo con ID: " + id, null));
        }

        if (insumo.getNombre() == null || insumo.getNombre().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "El nombre del insumo es obligatorio.", null));
        }
        if (insumo.getUnidadMedida() == null || insumo.getUnidadMedida().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "La unidad de medida es obligatoria.", null));
        }
        if (insumo.getStockMinimo() == null || insumo.getStockMinimo() < 0) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "El stock mínimo debe ser un número válido (>= 0).", null));
        }
        if (insumo.getStockActual() == null || insumo.getStockActual() < 0) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "El stock actual debe ser un número válido (>= 0).", null));
        }

        insumo.setIdInsumo(id);
        Insumo actualizado = service.guardar(insumo);
        return ResponseEntity.ok(new ApiResponse<>(true, "Insumo actualizado correctamente.", actualizado));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        Insumo existente = service.obtenerPorId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "No se encontró el insumo con ID: " + id, null));
        }

        service.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Insumo eliminado correctamente.", null));
    }
}
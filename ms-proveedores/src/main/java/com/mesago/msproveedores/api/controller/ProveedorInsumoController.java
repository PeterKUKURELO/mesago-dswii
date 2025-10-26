package com.mesago.msproveedores.api.controller;

import com.mesago.msproveedores.api.dto.ProveedorInsumoRequest;
import com.mesago.msproveedores.api.dto.ProveedorInsumoResponse;
import com.mesago.msproveedores.application.services.ProveedorInsumoService;
import com.mesago.msproveedores.domain.entities.ProveedorInsumo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/proveedor-insumo")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProveedorInsumoController {

    private final ProveedorInsumoService proveedorInsumoService;

    @PostMapping
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<ProveedorInsumoResponse> registrar(@RequestBody ProveedorInsumoRequest request) {
        ProveedorInsumoResponse response = proveedorInsumoService.registrar(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<List<ProveedorInsumoResponse>> listar() {
        List<ProveedorInsumoResponse> lista = proveedorInsumoService.listar();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<ProveedorInsumoResponse> buscarPorId(@PathVariable Long id) {
        ProveedorInsumoResponse response = proveedorInsumoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<ProveedorInsumoResponse> actualizar(
            @PathVariable Long id,
            @RequestBody ProveedorInsumoRequest request
    ) {
        ProveedorInsumoResponse response = proveedorInsumoService.actualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        proveedorInsumoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
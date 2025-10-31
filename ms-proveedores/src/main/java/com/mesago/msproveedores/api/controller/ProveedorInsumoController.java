package com.mesago.msproveedores.api.controller;

import com.mesago.msproveedores.api.dto.ProveedorInsumoRequest;
import com.mesago.msproveedores.api.dto.ProveedorInsumoResponse;
import com.mesago.msproveedores.application.services.ProveedorInsumoService;
import com.mesago.msproveedores.domain.entities.ProveedorInsumo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/proveedor-insumo")
@RequiredArgsConstructor
public class ProveedorInsumoController {

    private final ProveedorInsumoService proveedorInsumoService;

    // ✅ LISTAR TODAS LAS RELACIONES
    @GetMapping
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<ProveedorInsumoResponse>>> listar() {
        List<ProveedorInsumoResponse> lista = proveedorInsumoService.listar();

        if (lista == null || lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ApiResponse<>(false, "No hay relaciones proveedor-insumo registradas.", null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de relaciones obtenida correctamente.", lista));
    }

    // ✅ BUSCAR POR ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<ApiResponse<ProveedorInsumoResponse>> buscarPorId(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "El ID proporcionado no es válido.", null));
        }

        ProveedorInsumoResponse response = proveedorInsumoService.buscarPorId(id);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "No se encontró una relación proveedor-insumo con el ID: " + id, null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "Relación proveedor-insumo obtenida correctamente.", response));
    }

    // ✅ REGISTRAR
    @PostMapping
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<ApiResponse<ProveedorInsumoResponse>> registrar(@RequestBody ProveedorInsumoRequest request) {
        if (request.getIdProveedor() == null || request.getIdInsumo() == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Debe indicar tanto el ID del proveedor como el del insumo.", null));
        }

        ProveedorInsumoResponse response = proveedorInsumoService.registrar(request);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "No se encontró el proveedor o insumo especificado.", null));
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Relación proveedor-insumo registrada exitosamente.", response));
    }

    // ✅ ACTUALIZAR
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<ApiResponse<ProveedorInsumoResponse>> actualizar(@PathVariable Long id, @RequestBody ProveedorInsumoRequest request) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "El ID proporcionado no es válido.", null));
        }

        ProveedorInsumoResponse response = proveedorInsumoService.actualizar(id, request);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "No se encontró una relación proveedor-insumo con el ID: " + id, null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "Relación proveedor-insumo actualizada correctamente.", response));
    }

    // ✅ ELIMINAR
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "El ID proporcionado no es válido.", null));
        }

        var existente = proveedorInsumoService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "No se encontró una relación proveedor-insumo con el ID: " + id, null));
        }

        proveedorInsumoService.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Relación proveedor-insumo eliminada correctamente.", null));
    }
}
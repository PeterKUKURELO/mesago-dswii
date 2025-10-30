package com.mesago.msproveedores.api.controller;

import com.mesago.msproveedores.application.services.ProveedorService;
import com.mesago.msproveedores.domain.entities.Proveedor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@CrossOrigin(origins = "*")
public class ProveedorController {

    private final ProveedorService service;

    public ProveedorController(ProveedorService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<Proveedor>>> listar() {
        List<Proveedor> proveedores = service.listar();

        if (proveedores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ApiResponse<>(false, "No hay proveedores registrados.", null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de proveedores obtenida correctamente.", proveedores));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<ApiResponse<Proveedor>> obtener(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "El ID proporcionado no es válido.", null));
        }

        Proveedor proveedor = service.buscarPorId(id);
        if (proveedor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "No se encontró un proveedor con el ID: " + id, null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "Proveedor obtenido correctamente.", proveedor));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<ApiResponse<Proveedor>> crear(@RequestBody Proveedor proveedor) {
        if (proveedor == null) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "El proveedor no puede ser nulo.", null));
        }

        if (proveedor.getNombre() == null || proveedor.getNombre().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "El nombre del proveedor es obligatorio.", null));
        }

        Proveedor nuevo = service.guardar(proveedor);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Proveedor creado exitosamente.", nuevo));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<ApiResponse<Proveedor>> actualizar(@PathVariable Long id, @RequestBody Proveedor proveedor) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "El ID proporcionado no es válido.", null));
        }

        Proveedor existente = service.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "No se encontró un proveedor con el ID: " + id, null));
        }

        proveedor.setIdProveedor(id);
        Proveedor actualizado = service.actualizar(id, proveedor);

        return ResponseEntity.ok(new ApiResponse<>(true, "Proveedor actualizado correctamente.", actualizado));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "El ID proporcionado no es válido.", null));
        }

        Proveedor existente = service.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "No se encontró un proveedor con el ID: " + id, null));
        }

        service.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Proveedor eliminado correctamente.", null));
    }
}
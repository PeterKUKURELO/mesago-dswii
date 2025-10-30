package com.mesago.msfacturacion.api.controller;

import com.mesago.msfacturacion.aplication.services.FacturacionService;
import com.mesago.msfacturacion.domain.entities.Facturacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturacion")
@CrossOrigin(origins = "*")
public class FacturacionController {

    @Autowired
    private FacturacionService service;

    // LISTAR
    @GetMapping
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<Facturacion>>> listar() {
        List<Facturacion> facturas = service.listar();

        if (facturas == null || facturas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ApiResponse<>(false, "No hay facturas registradas.", null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "Listado de facturas obtenido correctamente.", facturas));
    }

    // OBTENER POR ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<ApiResponse<Facturacion>> obtenerPorId(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "El ID proporcionado no es válido.", null));
        }

        Facturacion factura = service.obtenerPorId(id);
        if (factura == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "No se encontró una factura con el ID: " + id, null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "Factura encontrada correctamente.", factura));
    }

    // CREAR
    @PostMapping
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<ApiResponse<Facturacion>> crear(@RequestBody Facturacion facturacion) {
        if (facturacion == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Los datos de la factura no pueden estar vacíos.", null));
        }

        if (facturacion.getFechaEmision() == null ||
                facturacion.getMetodoPago() == null || facturacion.getMetodoPago().isBlank() ||
                facturacion.getMontoTotal() == null ||
                facturacion.getNumeroFactura() == null || facturacion.getNumeroFactura().isBlank()) {

            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Campos obligatorios faltantes: fechaEmision, metodoPago, montoTotal o numeroFactura.", null));
        }

        Facturacion nueva = service.guardar(facturacion);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Factura creada exitosamente.", nueva));
    }

    // ACTUALIZAR
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<ApiResponse<Facturacion>> actualizar(@PathVariable Long id, @RequestBody Facturacion facturacion) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "El ID proporcionado no es válido.", null));
        }

        Facturacion existente = service.obtenerPorId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "No existe una factura con el ID: " + id, null));
        }

        if (facturacion == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Los datos de actualización no pueden estar vacíos.", null));
        }

        if (facturacion.getFechaEmision() == null ||
                facturacion.getMetodoPago() == null || facturacion.getMetodoPago().isBlank() ||
                facturacion.getMontoTotal() == null ||
                facturacion.getNumeroFactura() == null || facturacion.getNumeroFactura().isBlank()) {

            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Campos obligatorios faltantes: fechaEmision, metodoPago, montoTotal o numeroFactura.", null));
        }

        Facturacion actualizada = service.actualizar(id, facturacion);
        return ResponseEntity.ok(new ApiResponse<>(true, "Factura actualizada correctamente.", actualizada));
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "El ID proporcionado no es válido.", null));
        }

        Facturacion existente = service.obtenerPorId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "No se encontró una factura con el ID: " + id, null));
        }

        service.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Factura con ID " + id + " eliminada correctamente.", null));
    }
}
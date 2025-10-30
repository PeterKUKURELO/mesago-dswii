package com.mesago.clientes.api.controller;

import com.mesago.clientes.application.services.ClienteService;
import com.mesago.clientes.domain.entities.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteService service;

    // LISTAR CLIENTES
    @GetMapping
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<Cliente>>> listar() {
        List<Cliente> clientes = service.listar();

        if (clientes == null || clientes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ApiResponse<>(false, "No hay clientes registrados.", null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "Listado de clientes obtenido correctamente.", clientes));
    }

    // OBTENER POR ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<ApiResponse<Cliente>> obtenerPorId(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "El ID proporcionado no es válido.", null));
        }

        Cliente cliente = service.obtenerPorId(id);
        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "No se encontró un cliente con el ID: " + id, null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "Cliente encontrado correctamente.", cliente));
    }

    // CREAR CLIENTE
    @PostMapping
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<ApiResponse<Cliente>> crear(@RequestBody Cliente cliente) {
        if (cliente == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Los datos del cliente no pueden estar vacíos.", null));
        }

        if (cliente.getNombre() == null || cliente.getNombre().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "El nombre del cliente es obligatorio.", null));
        }

        if (cliente.getEmail() != null && !cliente.getEmail().isBlank() && !cliente.getEmail().contains("@")) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "El correo electrónico no es válido.", null));
        }

        Cliente nuevo = service.guardar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Cliente creado exitosamente.", nuevo));
    }

    // ACTUALIZAR CLIENTE
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<ApiResponse<Cliente>> actualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "El ID proporcionado no es válido.", null));
        }

        Cliente existente = service.obtenerPorId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "No existe un cliente con el ID: " + id, null));
        }

        if (cliente == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Los datos de actualización no pueden estar vacíos.", null));
        }

        if (cliente.getNombre() == null || cliente.getNombre().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "El nombre del cliente es obligatorio.", null));
        }

        Cliente actualizado = service.actualizar(id, cliente);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cliente actualizado correctamente.", actualizado));
    }

    // ELIMINAR CLIENTE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "El ID proporcionado no es válido.", null));
        }

        Cliente existente = service.obtenerPorId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "No se encontró un cliente con el ID: " + id, null));
        }

        service.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cliente con ID " + id + " eliminado correctamente.", null));
    }
}
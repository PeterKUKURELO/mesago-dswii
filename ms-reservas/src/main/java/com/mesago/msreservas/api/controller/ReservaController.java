package com.mesago.msreservas.api.controller;

import com.mesago.msreservas.application.services.ReservaService;
import com.mesago.msreservas.domain.entities.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*")
public class ReservaController {

    @Autowired
    private ReservaService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<Reserva>>> listar() {
        List<Reserva> reservas = service.listar();
        if (reservas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ApiResponse<>(false, "No hay reservas registradas.", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de reservas obtenida exitosamente.", reservas));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<ApiResponse<Reserva>> obtenerPorId(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "El ID proporcionado no es válido.", null));
        }

        Reserva reserva = service.obtenerPorId(id);
        if (reserva == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "No se encontró una reserva con el ID: " + id, null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "Reserva encontrada.", reserva));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<ApiResponse<Reserva>> crear(@RequestBody Reserva reserva) {
        // Validaciones básicas
        if (reserva == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Los datos de la reserva no pueden ser nulos.", null));
        }
        if (reserva.getFechaReserva() == null || reserva.getFechaReserva().isBefore(LocalDate.now())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "La fecha de la reserva no puede ser nula ni anterior a hoy.", null));
        }
        if (reserva.getHora() == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Debe indicar una hora para la reserva.", null));
        }
        if (reserva.getNumeroPersonas() == null || reserva.getNumeroPersonas() <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Debe indicar un número válido de personas.", null));
        }

        Reserva nueva = service.guardar(reserva);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Reserva creada exitosamente.", nueva));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<ApiResponse<Reserva>> actualizar(@PathVariable Long id, @RequestBody Reserva reserva) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "El ID proporcionado no es válido.", null));
        }

        Reserva existente = service.obtenerPorId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "No se encontró una reserva con el ID: " + id, null));
        }

        // Validaciones básicas
        if (reserva.getFechaReserva() == null || reserva.getFechaReserva().isBefore(LocalDate.now())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "La fecha de la reserva no puede ser nula ni anterior a hoy.", null));
        }
        if (reserva.getHora() == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Debe indicar una hora para la reserva.", null));
        }
        if (reserva.getNumeroPersonas() == null || reserva.getNumeroPersonas() <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Debe indicar un número válido de personas.", null));
        }

        reserva.setIdReserva(id);
        Reserva actualizada = service.guardar(reserva);

        return ResponseEntity.ok(new ApiResponse<>(true, "Reserva actualizada correctamente.", actualizada));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "El ID proporcionado no es válido.", null));
        }

        Reserva existente = service.obtenerPorId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "No se encontró una reserva con el ID: " + id, null));
        }

        service.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Reserva eliminada correctamente.", null));
    }
}
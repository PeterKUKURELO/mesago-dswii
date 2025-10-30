package com.mesago.mspedidos.controller;

import com.mesago.mspedidos.dto.DetallePedidoDTO;
import com.mesago.mspedidos.entity.DetallePedido;
import com.mesago.mspedidos.service.DetallePedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalles")
@CrossOrigin(origins = "*")
public class DetallePedidoController {

    private final DetallePedidoService detalleService;

    public DetallePedidoController(DetallePedidoService detalleService) {
        this.detalleService = detalleService;
    }

    // ✅ CREAR DETALLE
    @PostMapping
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<ApiResponse<DetallePedidoDTO>> crear(@RequestBody DetallePedidoDTO dto) {
        if (dto == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "El detalle no puede ser nulo.", null));
        }
        if (dto.getIdPedido() == null || dto.getIdPedido() <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Debe indicar un ID de pedido válido.", null));
        }
        if (dto.getIdMenu() == null || dto.getIdMenu() <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Debe indicar un ID de insumo válido.", null));
        }
        if (dto.getCantidad() == null || dto.getCantidad() <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "La cantidad debe ser mayor a 0.", null));
        }

        DetallePedidoDTO creado = detalleService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Detalle de pedido creado exitosamente.", creado));
    }

    // ✅ LISTAR DETALLES POR PEDIDO
    @GetMapping("/pedido/{idPedido}")
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<DetallePedidoDTO>>> listarPorPedido(@PathVariable Long idPedido) {
        if (idPedido == null || idPedido <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "El ID del pedido no es válido.", null));
        }

        List<DetallePedidoDTO> lista = detalleService.listarPorPedido(idPedido);
        if (lista == null || lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ApiResponse<>(false, "No se encontraron detalles para el pedido con ID: " + idPedido, null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "Detalles del pedido obtenidos correctamente.", lista));
    }

    // ✅ OBTENER DETALLE POR ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<ApiResponse<DetallePedidoDTO>> obtenerPorId(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "El ID proporcionado no es válido.", null));
        }

        DetallePedidoDTO detalle = detalleService.obtenerPorId(id);
        if (detalle == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "No se encontró un detalle de pedido con el ID: " + id, null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "Detalle de pedido encontrado.", detalle));
    }

    // ✅ ACTUALIZAR DETALLE
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<ApiResponse<DetallePedidoDTO>> actualizar(@PathVariable Long id, @RequestBody DetallePedidoDTO dto) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "El ID proporcionado no es válido.", null));
        }
        if (dto == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Los datos del detalle no pueden ser nulos.", null));
        }
        if (dto.getCantidad() == null || dto.getCantidad() <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "La cantidad debe ser mayor a 0.", null));
        }

        DetallePedidoDTO actualizado = detalleService.actualizar(id, dto);
        if (actualizado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "No se encontró el detalle de pedido con el ID: " + id, null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "Detalle de pedido actualizado exitosamente.", actualizado));
    }

    // ✅ ELIMINAR DETALLE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "El ID proporcionado no es válido.", null));
        }

        DetallePedidoDTO existente = detalleService.obtenerPorId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "No se encontró un detalle con el ID: " + id, null));
        }

        detalleService.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Detalle de pedido eliminado correctamente.", null));
    }
}
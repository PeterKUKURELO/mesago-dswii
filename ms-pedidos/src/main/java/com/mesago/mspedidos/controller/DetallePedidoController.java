package com.mesago.mspedidos.controller;

import com.mesago.mspedidos.dto.DetallePedidoDTO;
import com.mesago.mspedidos.entity.DetallePedido;
import com.mesago.mspedidos.service.DetallePedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalles")
public class DetallePedidoController {

    private final DetallePedidoService detalleService;

    public DetallePedidoController(DetallePedidoService detalleService) {
        this.detalleService = detalleService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<DetallePedidoDTO> crear(@RequestBody DetallePedidoDTO dto) {
        return ResponseEntity.ok(detalleService.crear(dto));
    }

    @GetMapping("/pedido/{idPedido}")
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<List<DetallePedidoDTO>> listarPorPedido(@PathVariable Long idPedido) {
        return ResponseEntity.ok(detalleService.listarPorPedido(idPedido));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<DetallePedidoDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(detalleService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<DetallePedidoDTO> actualizar(@PathVariable Long id, @RequestBody DetallePedidoDTO dto) {
        return ResponseEntity.ok(detalleService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        detalleService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
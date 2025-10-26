package com.mesago.mspedidos.controller;

import com.mesago.mspedidos.dto.PedidoRequestDTO;
import com.mesago.mspedidos.dto.PedidoResponseDTO;
import com.mesago.mspedidos.entity.Pedido;
import com.mesago.mspedidos.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<PedidoResponseDTO> crear(@RequestBody PedidoRequestDTO dto) {
        Pedido pedido = pedidoService.crear(dto);
        return ResponseEntity.ok(pedidoService.toResponseDTO(pedido));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<List<PedidoResponseDTO>> listar() {
        return ResponseEntity.ok(pedidoService.listar());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<PedidoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return pedidoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<PedidoResponseDTO> actualizar(@PathVariable Long id, @RequestBody PedidoRequestDTO dto) {
        Pedido actualizado = pedidoService.actualizar(id, dto);
        return ResponseEntity.ok(pedidoService.toResponseDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
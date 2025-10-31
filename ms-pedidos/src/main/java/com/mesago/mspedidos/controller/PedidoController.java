package com.mesago.mspedidos.controller;

import com.mesago.mspedidos.dto.PedidoRequestDTO;
import com.mesago.mspedidos.dto.PedidoResponseDTO;
import com.mesago.mspedidos.entity.Pedido;
import com.mesago.mspedidos.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<ApiResponse<PedidoResponseDTO>> crear(@RequestBody PedidoRequestDTO dto) {
        Pedido pedido = pedidoService.crear(dto);
        PedidoResponseDTO response = pedidoService.toResponseDTO(pedido);
        return ResponseEntity.ok(new ApiResponse<>(true, "Pedido creado correctamente", response));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<PedidoResponseDTO>>> listar() {
        List<PedidoResponseDTO> pedidos = pedidoService.listar();
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de pedidos obtenida correctamente", pedidos));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<ApiResponse<PedidoResponseDTO>> obtenerPorId(@PathVariable Long id) {
        Optional<PedidoResponseDTO> pedidoOpt = pedidoService.obtenerPorId(id);

        if (pedidoOpt.isPresent()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Pedido encontrado correctamente", pedidoOpt.get()));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "Pedido no encontrado", null));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<ApiResponse<PedidoResponseDTO>> actualizar(@PathVariable Long id, @RequestBody PedidoRequestDTO dto) {
        try {
            Pedido actualizado = pedidoService.actualizar(id, dto);
            PedidoResponseDTO response = pedidoService.toResponseDTO(actualizado);
            return ResponseEntity.ok(new ApiResponse<>(true, "Pedido actualizado correctamente", response));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        try {
            pedidoService.eliminar(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Pedido eliminado correctamente", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
}
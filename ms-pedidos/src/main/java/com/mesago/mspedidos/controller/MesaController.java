package com.mesago.mspedidos.controller;

import com.mesago.mspedidos.entity.Mesa;
import com.mesago.mspedidos.service.MesaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mesas")
public class MesaController {

    private final MesaService mesaService;

    public MesaController(MesaService mesaService) {
        this.mesaService = mesaService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<Mesa>>> listar() {
        List<Mesa> mesas = mesaService.listar();
        return ResponseEntity.ok(new ApiResponse<>(true, "Lista de mesas obtenida correctamente", mesas));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<ApiResponse<Mesa>> obtenerPorId(@PathVariable Long id) {
        Optional<Mesa> mesaOpt = mesaService.obtenerPorId(id);

        if (mesaOpt.isPresent()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Mesa encontrada correctamente", mesaOpt.get()));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "Mesa no encontrada", null));
        }
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<ApiResponse<Mesa>> guardar(@RequestBody Mesa mesa) {
        Mesa nueva = mesaService.guardar(mesa);
        return ResponseEntity.ok(new ApiResponse<>(true, "Mesa creada correctamente", nueva));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<ApiResponse<Mesa>> actualizar(@PathVariable Long id, @RequestBody Mesa mesa) {
        Optional<Mesa> existente = mesaService.obtenerPorId(id);

        if (existente.isPresent()) {
            mesa.setIdMesa(id);
            Mesa actualizada = mesaService.guardar(mesa);
            return ResponseEntity.ok(new ApiResponse<>(true, "Mesa actualizada correctamente", actualizada));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "No se encontró la mesa a actualizar", null));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        Optional<Mesa> existente = mesaService.obtenerPorId(id);

        if (existente.isPresent()) {
            mesaService.eliminar(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Mesa eliminada correctamente", null));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "No se encontró la mesa a eliminar", null));
        }
    }
}

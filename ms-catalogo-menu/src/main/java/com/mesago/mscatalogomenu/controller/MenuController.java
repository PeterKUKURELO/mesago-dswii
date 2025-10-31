package com.mesago.mscatalogomenu.controller;

import com.mesago.mscatalogomenu.dto.MenuResponseDTO;
import com.mesago.mscatalogomenu.entity.Menu;
import com.mesago.mscatalogomenu.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CHEF', 'MESERO', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<Menu>>> listar() {
        List<Menu> menus = menuService.listar();

        if (menus.isEmpty()) {
            return ResponseEntity.status(204)
                    .body(new ApiResponse<>(true, "No hay menús registrados", null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "Listado de menús obtenido correctamente", menus));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<ApiResponse<Menu>> obtenerPorId(@PathVariable Long id) {
        Optional<Menu> menuOpt = menuService.obtenerPorId(id);

        if (menuOpt.isPresent()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Menú encontrado correctamente", menuOpt.get()));
        } else {
            return ResponseEntity.status(404)
                    .body(new ApiResponse<>(false, "No se encontró un menú con ID: " + id, null));
        }
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<ApiResponse<MenuResponseDTO>> guardar(@RequestBody Menu menu) {
        // Validaciones básicas
        if (menu.getNombre() == null || menu.getNombre().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "El nombre del menú es obligatorio", null));
        }
        if (menu.getPrecio() == null || menu.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(false, "El precio debe ser mayor que 0", null)
            );
        }
        if (menu.getStock() == null || menu.getStock() < 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "El stock no puede ser negativo", null));
        }

        menu.setEstado("Activo");
        Menu nuevo = menuService.guardar(menu);

        MenuResponseDTO response = new MenuResponseDTO(
                nuevo.getIdMenu(),
                nuevo.getNombre(),
                nuevo.getDescripcion(),
                nuevo.getPrecio(),
                nuevo.getStock(),
                nuevo.getEstado(),
                nuevo.getCategoriaMenu() != null ? nuevo.getCategoriaMenu().getIdCategoriaMenu() : null
        );

        return ResponseEntity.ok(new ApiResponse<>(true, "Menú creado correctamente", response));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<ApiResponse<Menu>> actualizar(@PathVariable Long id, @RequestBody Menu menu) {
        Optional<Menu> existente = menuService.obtenerPorId(id);

        if (existente.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(new ApiResponse<>(false, "No se encontró un menú con ID: " + id, null));
        }

        // Validaciones básicas
        if (menu.getNombre() == null || menu.getNombre().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "El nombre del menú es obligatorio", null));
        }
        if (menu.getPrecio() == null || menu.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>(false, "El precio debe ser mayor que 0", null)
            );
        }
        if (menu.getStock() == null || menu.getStock() < 0) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "El stock no puede ser negativo", null));
        }

        menu.setIdMenu(id);
        Menu actualizado = menuService.guardar(menu);
        return ResponseEntity.ok(new ApiResponse<>(true, "Menú actualizado correctamente", actualizado));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        Optional<Menu> existente = menuService.obtenerPorId(id);

        if (existente.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(new ApiResponse<>(false, "No se encontró un menú con ID: " + id, null));
        }

        menuService.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Menú eliminado correctamente", null));
    }
}
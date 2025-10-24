package com.mesago.mscatalogomenu.controller;

import com.mesago.mscatalogomenu.dto.MenuResponseDTO;
import com.mesago.mscatalogomenu.entity.Menu;
import com.mesago.mscatalogomenu.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public ResponseEntity<List<Menu>> listar() {
        return ResponseEntity.ok(menuService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Menu> obtenerPorId(@PathVariable Long id) {
        return menuService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ ESTE ES EL POST CORRECTO
    @PostMapping
    public ResponseEntity<MenuResponseDTO> guardar(@RequestBody Menu menu) {
        Menu nuevo = menuService.guardar(menu);

        // Convertimos la entidad guardada en un DTO de respuesta
        MenuResponseDTO response = new MenuResponseDTO(
                nuevo.getIdMenu(),
                nuevo.getNombre(),
                nuevo.getDescripcion(),
                nuevo.getPrecio(),
                nuevo.getStock(),
                nuevo.getEstado(),
                nuevo.getCategoriaMenu() != null ? nuevo.getCategoriaMenu().getIdCategoriaMenu() : null
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Menu> actualizar(@PathVariable Long id, @RequestBody Menu menu) {
        return menuService.obtenerPorId(id)
                .map(existing -> {
                    menu.setIdMenu(id);
                    return ResponseEntity.ok(menuService.guardar(menu));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        menuService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
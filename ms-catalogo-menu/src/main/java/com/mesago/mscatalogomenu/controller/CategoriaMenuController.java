package com.mesago.mscatalogomenu.controller;

import com.mesago.mscatalogomenu.entity.CategoriaMenu;
import com.mesago.mscatalogomenu.service.CategoriaMenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaMenuController {

    private final CategoriaMenuService categoriaMenuService;

    public CategoriaMenuController(CategoriaMenuService categoriaMenuService) {
        this.categoriaMenuService = categoriaMenuService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<List<CategoriaMenu>> listar() {
        return ResponseEntity.ok(categoriaMenuService.listar());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<CategoriaMenu> obtenerPorId(@PathVariable Long id) {
        return categoriaMenuService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<CategoriaMenu> guardar(@RequestBody CategoriaMenu categoriaMenu) {
        return ResponseEntity.ok(categoriaMenuService.guardar(categoriaMenu));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<CategoriaMenu> actualizar(@PathVariable Long id, @RequestBody CategoriaMenu categoriaMenu) {
        return categoriaMenuService.obtenerPorId(id)
                .map(existing -> {
                    categoriaMenu.setIdCategoriaMenu(id);
                    return ResponseEntity.ok(categoriaMenuService.guardar(categoriaMenu));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        categoriaMenuService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

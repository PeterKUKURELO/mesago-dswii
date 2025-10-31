package com.mesago.mscatalogomenu.controller;

import com.mesago.mscatalogomenu.entity.CategoriaMenu;
import com.mesago.mscatalogomenu.service.CategoriaMenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaMenuController {

    private final CategoriaMenuService categoriaMenuService;

    public CategoriaMenuController(CategoriaMenuService categoriaMenuService) {
        this.categoriaMenuService = categoriaMenuService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<CategoriaMenu>>> listar() {
        List<CategoriaMenu> categorias = categoriaMenuService.listar();

        if (categorias.isEmpty()) {
            return ResponseEntity.status(204)
                    .body(new ApiResponse<>(true, "No hay categorías registradas", null));
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "Listado de categorías obtenido correctamente", categorias));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<ApiResponse<CategoriaMenu>> obtenerPorId(@PathVariable Long id) {
        Optional<CategoriaMenu> categoriaOpt = categoriaMenuService.obtenerPorId(id);

        if (categoriaOpt.isPresent()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Categoría encontrada correctamente", categoriaOpt.get()));
        } else {
            return ResponseEntity.status(404)
                    .body(new ApiResponse<>(false, "Categoría no encontrada con ID: " + id, null));
        }
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<ApiResponse<CategoriaMenu>> guardar(@RequestBody CategoriaMenu categoriaMenu) {
        if (categoriaMenu.getNombre() == null || categoriaMenu.getNombre().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "El nombre de la categoría es obligatorio", null));
        }

        CategoriaMenu nueva = categoriaMenuService.guardar(categoriaMenu);
        return ResponseEntity.ok(new ApiResponse<>(true, "Categoría creada correctamente", nueva));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<ApiResponse<CategoriaMenu>> actualizar(@PathVariable Long id, @RequestBody CategoriaMenu categoriaMenu) {
        Optional<CategoriaMenu> existente = categoriaMenuService.obtenerPorId(id);

        if (existente.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(new ApiResponse<>(false, "No se encontró una categoría con ID: " + id, null));
        }

        if (categoriaMenu.getNombre() == null || categoriaMenu.getNombre().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "El nombre de la categoría es obligatorio", null));
        }

        categoriaMenu.setIdCategoriaMenu(id);
        CategoriaMenu actualizada = categoriaMenuService.guardar(categoriaMenu);
        return ResponseEntity.ok(new ApiResponse<>(true, "Categoría actualizada correctamente", actualizada));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CHEF', 'ADMIN')")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        Optional<CategoriaMenu> existente = categoriaMenuService.obtenerPorId(id);

        if (existente.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(new ApiResponse<>(false, "No se encontró una categoría con ID: " + id, null));
        }

        categoriaMenuService.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Categoría eliminada correctamente", null));
    }
}
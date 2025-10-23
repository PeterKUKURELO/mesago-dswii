package com.mesago.mscatalogomenu.service;

import com.mesago.mscatalogomenu.entity.CategoriaMenu;

import java.util.List;
import java.util.Optional;

public interface CategoriaMenuService {

    List<CategoriaMenu> listar();
    Optional<CategoriaMenu> obtenerPorId(Long id);
    CategoriaMenu guardar(CategoriaMenu categoriaMenu);
    void eliminar(Long id);
}
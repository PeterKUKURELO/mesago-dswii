package com.mesago.mscatalogomenu.service;

import com.mesago.mscatalogomenu.entity.Menu;

import java.util.List;
import java.util.Optional;

public interface MenuService {
    List<Menu> listar();
    Optional<Menu> obtenerPorId(Long id);
    Menu guardar(Menu menu);
    void eliminar(Long id);
}
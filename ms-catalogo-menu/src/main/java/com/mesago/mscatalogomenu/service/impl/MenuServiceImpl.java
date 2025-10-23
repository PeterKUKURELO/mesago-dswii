package com.mesago.mscatalogomenu.service.impl;

import com.mesago.mscatalogomenu.entity.Menu;
import com.mesago.mscatalogomenu.repository.MenuRepository;
import com.mesago.mscatalogomenu.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    public MenuServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public List<Menu> listar() {
        return menuRepository.findAll();
    }

    @Override
    public Optional<Menu> obtenerPorId(Long id) {
        return menuRepository.findById(id);
    }

    @Override
    public Menu guardar(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public void eliminar(Long id) {
        menuRepository.deleteById(id);
    }
}

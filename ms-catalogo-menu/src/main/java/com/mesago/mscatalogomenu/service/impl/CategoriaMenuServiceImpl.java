package com.mesago.mscatalogomenu.service.impl;

import com.mesago.mscatalogomenu.entity.CategoriaMenu;
import com.mesago.mscatalogomenu.repository.CategoriaMenuRepository;
import com.mesago.mscatalogomenu.service.CategoriaMenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaMenuServiceImpl implements CategoriaMenuService {

    private final CategoriaMenuRepository categoriaMenuRepository;

    public CategoriaMenuServiceImpl(CategoriaMenuRepository categoriaMenuRepository) {
        this.categoriaMenuRepository = categoriaMenuRepository;
    }

    @Override
    public List<CategoriaMenu> listar() {
        return categoriaMenuRepository.findAll();
    }

    @Override
    public Optional<CategoriaMenu> obtenerPorId(Long id) {
        return categoriaMenuRepository.findById(id);
    }

    @Override
    public CategoriaMenu guardar(CategoriaMenu categoriaMenu) {
        return categoriaMenuRepository.save(categoriaMenu);
    }

    @Override
    public void eliminar(Long id) {
        categoriaMenuRepository.deleteById(id);
    }
}
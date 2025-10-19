package com.mesago.msinventario.application.services.impl;

import com.mesago.msinventario.application.services.InsumoService;
import com.mesago.msinventario.domain.entities.Insumo;
import com.mesago.msinventario.domain.repositories.InsumoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsumoServiceImpl implements InsumoService {

    @Autowired
    private InsumoRepository repo;

    @Override
    public List<Insumo> listar() {
        return repo.findAll();
    }

    @Override
    public Insumo obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Insumo guardar(Insumo insumo) {
        return repo.save(insumo);
    }

    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
package com.mesago.mspedidos.service.impl;

import com.mesago.mspedidos.entity.Mesa;
import com.mesago.mspedidos.repository.MesaRepository;
import com.mesago.mspedidos.service.MesaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MesaServiceImpl implements MesaService {

    private final MesaRepository mesaRepository;

    public MesaServiceImpl(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    @Override
    public List<Mesa> listar() {
        return mesaRepository.findAll();
    }

    @Override
    public Optional<Mesa> obtenerPorId(Long id) {
        return mesaRepository.findById(id);
    }

    @Override
    public Mesa guardar(Mesa mesa) {
        return mesaRepository.save(mesa);
    }

    @Override
    public void eliminar(Long id) {
        mesaRepository.deleteById(id);
    }
}

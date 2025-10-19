package com.mesago.msreservas.application.services.impl;

import com.mesago.msreservas.application.services.ReservaService;
import com.mesago.msreservas.domain.entities.Reserva;
import com.mesago.msreservas.domain.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReservaServiceImpl implements ReservaService {
    
    @Autowired
    private ReservaRepository repo;
    
    @Override
    public List<Reserva> listar() {
        return repo.findAll();
    }
    
    @Override
    public Reserva obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }
    
    @Override
    public Reserva guardar(Reserva reserva) {
        return repo.save(reserva);
    }
    
    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
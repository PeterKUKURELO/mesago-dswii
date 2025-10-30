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
        if (id == null || id <= 0) {
            return null;
        }
        return repo.findById(id).orElse(null);
    }

    @Override
    public Reserva guardar(Reserva reserva) {
        if (reserva == null) {
            return null;
        }
        return repo.save(reserva);
    }

    @Override
    public void eliminar(Long id) {
        if (id != null && id > 0 && repo.existsById(id)) {
            repo.deleteById(id);
        }
    }
}
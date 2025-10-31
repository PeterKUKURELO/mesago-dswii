package com.mesago.msreservas.application.services;

import com.mesago.msreservas.domain.entities.Reserva;
import java.util.List;

public interface ReservaService {
    List<Reserva> listar();
    Reserva obtenerPorId(Long id);
    Reserva guardar(Reserva reserva);
    void eliminar(Long id);
}
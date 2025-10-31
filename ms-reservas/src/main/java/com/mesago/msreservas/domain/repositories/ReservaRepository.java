package com.mesago.msreservas.domain.repositories;

import com.mesago.msreservas.domain.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
package com.mesago.domain.repositories;

import com.mesago.domain.entities.Insumo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsumoRepository extends JpaRepository<Insumo, Long> {
}
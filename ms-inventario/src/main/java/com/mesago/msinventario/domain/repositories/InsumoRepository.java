package com.mesago.msinventario.domain.repositories;

import com.mesago.msinventario.domain.entities.Insumo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsumoRepository extends JpaRepository<Insumo, Long> {
}
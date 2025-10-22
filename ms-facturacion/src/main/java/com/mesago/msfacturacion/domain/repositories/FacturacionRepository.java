package com.mesago.msfacturacion.domain.repositories;

import com.mesago.msfacturacion.domain.entities.Facturacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturacionRepository extends JpaRepository<Facturacion, Long> {
}

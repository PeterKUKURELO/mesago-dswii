package com.mesago.msfacturacion.domain.repositories;

import com.mesago.msfacturacion.domain.entities.Facturacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacturacionRepository extends JpaRepository<Facturacion, Long> {

    Optional<Facturacion> findByNumeroFactura(String numeroFactura);
}

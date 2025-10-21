package com.mesago.msproveedores.domain.repositories;

import com.mesago.msproveedores.domain.entities.ProveedorInsumo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedorInsumoRepository extends JpaRepository<ProveedorInsumo, Long> {
}
package com.mesago.msproveedores.domain.repositories;

import com.mesago.msproveedores.domain.entities.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
}

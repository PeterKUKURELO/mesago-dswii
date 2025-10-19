package com.mesago.clientes.domain.repositories;

import com.mesago.clientes.domain.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
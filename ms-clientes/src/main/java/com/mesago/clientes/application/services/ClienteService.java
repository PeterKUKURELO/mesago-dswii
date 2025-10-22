package com.mesago.clientes.application.services;

import com.mesago.clientes.domain.entities.Cliente;
import java.util.List;

public interface ClienteService {
    List<Cliente> listar();
    Cliente obtenerPorId(Long id);
    Cliente guardar(Cliente cliente);
    void eliminar(Long id);
}
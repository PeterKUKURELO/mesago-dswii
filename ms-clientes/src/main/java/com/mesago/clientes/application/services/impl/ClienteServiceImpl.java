package com.mesago.clientes.application.services.impl;

import com.mesago.clientes.application.services.ClienteService;
import com.mesago.clientes.domain.entities.Cliente;
import com.mesago.clientes.domain.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository repo;

    // LISTAR TODOS LOS CLIENTES
    public List<Cliente> listar() {
        return repo.findAll();
    }

    // OBTENER POR ID
    public Cliente obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    // GUARDAR CLIENTE
    public Cliente guardar(Cliente cliente) {
        return repo.save(cliente);
    }

    // ACTUALIZAR CLIENTE
    public Cliente actualizar(Long id, Cliente cliente) {
        Cliente existente = repo.findById(id).orElse(null);
        if (existente == null) {
            return null;
        }

        existente.setNombre(cliente.getNombre());
        existente.setEmail(cliente.getEmail());
        existente.setTelefono(cliente.getTelefono());
        existente.setObservaciones(cliente.getObservaciones());

        return repo.save(existente);
    }

    // ELIMINAR CLIENTE
    public void eliminar(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        }
    }
}
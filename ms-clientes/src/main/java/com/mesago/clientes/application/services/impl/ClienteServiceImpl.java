package com.mesago.clientes.application.services.impl;

import com.mesago.clientes.application.services.ClienteService;
import com.mesago.clientes.domain.entities.Cliente;
import com.mesago.clientes.domain.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {
    
    @Autowired
    private ClienteRepository repo;
    
    @Override
    public List<Cliente> listar() {
        return repo.findAll();
    }
    
    @Override
    public Cliente obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }
    
    @Override
    public Cliente guardar(Cliente cliente) {
        return repo.save(cliente);
    }
    
    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
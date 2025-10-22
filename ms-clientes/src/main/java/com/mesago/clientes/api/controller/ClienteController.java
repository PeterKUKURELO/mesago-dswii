package com.mesago.clientes.api.controller;

import com.mesago.clientes.application.services.ClienteService;
import com.mesago.clientes.domain.entities.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {
    
    @Autowired
    private ClienteService service;
    
    @GetMapping
    public List<Cliente> listar() {
        return service.listar();
    }
    
    @GetMapping("/{id}")
    public Cliente obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }
    
    @PostMapping
    public Cliente crear(@RequestBody Cliente cliente) {
        return service.guardar(cliente);
    }
    
    @PutMapping("/{id}")
    public Cliente actualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        cliente.setIdCliente(id);
        return service.guardar(cliente);
    }
    
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
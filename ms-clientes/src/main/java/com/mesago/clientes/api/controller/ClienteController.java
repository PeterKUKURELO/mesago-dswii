package com.mesago.clientes.api.controller;

import com.mesago.clientes.application.services.ClienteService;
import com.mesago.clientes.domain.entities.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {
    
    @Autowired
    private ClienteService service;
    
    @GetMapping
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public List<Cliente> listar() {
        return service.listar();
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public Cliente obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }
    
    @PostMapping
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public Cliente crear(@RequestBody Cliente cliente) {
        return service.guardar(cliente);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MESERO', 'ADMIN')")
    public Cliente actualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        cliente.setIdCliente(id);
        return service.guardar(cliente);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
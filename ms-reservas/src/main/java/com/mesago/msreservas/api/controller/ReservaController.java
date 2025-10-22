package com.mesago.msreservas.api.controller;

import com.mesago.msreservas.application.services.ReservaService;
import com.mesago.msreservas.domain.entities.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*")
public class ReservaController {
    
    @Autowired
    private ReservaService service;
    
    @GetMapping
    public List<Reserva> listar() {
        return service.listar();
    }
    
    @GetMapping("/{id}")
    public Reserva obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }
    
    @PostMapping
    public Reserva crear(@RequestBody Reserva reserva) {
        return service.guardar(reserva);
    }
    
            @PutMapping("/{id}")
            public Reserva actualizar(@PathVariable Long id, @RequestBody Reserva reserva) {
                return service.guardar(reserva);
            }   
    
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
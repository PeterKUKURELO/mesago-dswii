package com.mesago.msfacturacion.aplication.services.impl;

import com.mesago.msfacturacion.aplication.services.FacturacionService;
import com.mesago.msfacturacion.domain.entities.Facturacion;
import com.mesago.msfacturacion.domain.repositories.FacturacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacturacionServiceImpl implements FacturacionService {

    @Autowired
    private FacturacionRepository repo;

    @Override
    public List<Facturacion> listar() {
        return repo.findAll();
    }

    @Override
    public Facturacion obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Facturacion guardar(Facturacion facturacion) {
        return repo.save(facturacion);
    }

    @Override
    public Facturacion actualizar(Long id, Facturacion facturacion) {
        Optional<Facturacion> opt = repo.findById(id);
        if (opt.isPresent()) {
            Facturacion existente = opt.get();

            // ✅ Seteamos los campos actualizables
            existente.setFechaEmision(facturacion.getFechaEmision());
            existente.setMetodoPago(facturacion.getMetodoPago());
            existente.setMontoTotal(facturacion.getMontoTotal());
            existente.setNumeroFactura(facturacion.getNumeroFactura());
            existente.setTipo(facturacion.getTipo());
            existente.setIdPedido(facturacion.getIdPedido());
            existente.setIdReserva(facturacion.getIdReserva());

            return repo.save(existente);
        }
        return null;
    }

    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
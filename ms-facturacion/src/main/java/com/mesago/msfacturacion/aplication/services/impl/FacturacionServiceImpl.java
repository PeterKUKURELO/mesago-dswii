package com.mesago.msfacturacion.aplication.services.impl;

import com.mesago.msfacturacion.aplication.services.FacturacionService;
import com.mesago.msfacturacion.domain.entities.Facturacion;
import com.mesago.msfacturacion.domain.repositories.FacturacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class FacturacionServiceImpl implements FacturacionService {

    @Autowired
    private FacturacionRepository repository;

    // LISTAR TODAS LAS FACTURAS
    public List<Facturacion> listar() {
        return repository.findAll();
    }

    // OBTENER FACTURA POR ID
    public Facturacion obtenerPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    // GUARDAR FACTURA
    public Facturacion guardar(Facturacion facturacion) {
        return repository.save(facturacion);
    }

    // ACTUALIZAR FACTURA
    public Facturacion actualizar(Long id, Facturacion facturacion) {
        Facturacion existente = repository.findById(id).orElse(null);
        if (existente == null) {
            return null;
        }

        // Actualizar campos de la factura
        existente.setFechaEmision(facturacion.getFechaEmision());
        existente.setMetodoPago(facturacion.getMetodoPago());
        existente.setMontoTotal(facturacion.getMontoTotal());
        existente.setNumeroFactura(facturacion.getNumeroFactura());
        existente.setTipo(facturacion.getTipo());
        existente.setIdPedido(facturacion.getIdPedido());
        existente.setIdReserva(facturacion.getIdReserva());

        return repository.save(existente);
    }

    // ELIMINAR FACTURA
    public void eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }
}
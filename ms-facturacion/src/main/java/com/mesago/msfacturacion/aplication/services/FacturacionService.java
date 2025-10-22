package com.mesago.msfacturacion.aplication.services;

import com.mesago.msfacturacion.domain.entities.Facturacion;

import java.util.List;

public interface FacturacionService {
    List<Facturacion> listar();
    Facturacion obtenerPorId(Long id);
    Facturacion guardar(Facturacion facturacion);
    Facturacion actualizar(Long id, Facturacion facturacion);
    void eliminar(Long id);
}

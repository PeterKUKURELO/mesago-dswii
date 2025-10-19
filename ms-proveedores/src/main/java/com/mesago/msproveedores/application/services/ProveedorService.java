package com.mesago.msproveedores.application.services;

import com.mesago.msproveedores.domain.entities.Proveedor;

import java.util.List;

public interface ProveedorService {
    List<Proveedor> listar();
    Proveedor buscarPorId(Long id);
    Proveedor guardar(Proveedor proveedor);
    Proveedor actualizar(Long id, Proveedor proveedor);
    void eliminar(Long id);
}
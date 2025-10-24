package com.mesago.mspedidos.service;

import com.mesago.mspedidos.entity.Mesa;

import java.util.List;
import java.util.Optional;

public interface MesaService {

    List<Mesa> listar();
    Optional<Mesa> obtenerPorId(Long id);
    Mesa guardar(Mesa mesa);
    void eliminar(Long id);
}
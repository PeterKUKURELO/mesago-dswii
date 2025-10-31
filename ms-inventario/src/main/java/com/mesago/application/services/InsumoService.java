package com.mesago.application.services;

import com.mesago.domain.entities.Insumo;

import java.util.List;

public interface InsumoService {
    List<Insumo> listar();
    Insumo obtenerPorId(Long id);
    Insumo guardar(Insumo insumo);
    void eliminar(Long id);
}
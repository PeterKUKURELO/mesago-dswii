package com.mesago.msinventario.application.services;

import com.mesago.msinventario.domain.entities.Insumo;

import java.util.List;

public interface InsumoService {
    List<Insumo> listar();
    Insumo obtenerPorId(Long id);
    Insumo guardar(Insumo insumo);
    void eliminar(Long id);
}
package com.mesago.mspedidos.service;

import com.mesago.mspedidos.entity.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoService {
    List<Pedido> listar();
    Optional<Pedido> obtenerPorId(Long id);
    Pedido guardar(Pedido pedido);
    void eliminar(Long id);
}
package com.mesago.mspedidos.service;

import com.mesago.mspedidos.entity.DetallePedido;

import java.util.List;
import java.util.Optional;

public interface DetallePedidoService {
    List<DetallePedido> listar();
    Optional<DetallePedido> obtenerPorId(Long id);
    DetallePedido guardar(DetallePedido detalle);
    void eliminar(Long id);
}
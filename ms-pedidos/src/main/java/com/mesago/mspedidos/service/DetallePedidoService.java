package com.mesago.mspedidos.service;

import com.mesago.mspedidos.dto.DetallePedidoDTO;
import com.mesago.mspedidos.entity.DetallePedido;

import java.util.List;
import java.util.Optional;

public interface DetallePedidoService {
    DetallePedidoDTO crear(DetallePedidoDTO dto);
    List<DetallePedidoDTO> listarPorPedido(Long idPedido);
    DetallePedidoDTO obtenerPorId(Long id);
    DetallePedidoDTO actualizar(Long id, DetallePedidoDTO dto);
    void eliminar(Long id);
}
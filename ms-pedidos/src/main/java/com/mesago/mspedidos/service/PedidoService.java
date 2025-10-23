package com.mesago.mspedidos.service;

import com.mesago.mspedidos.dto.PedidoRequestDTO;
import com.mesago.mspedidos.dto.PedidoResponseDTO;
import com.mesago.mspedidos.entity.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoService {

    Pedido crear(PedidoRequestDTO dto);

    List<PedidoResponseDTO> listar();

    Optional<PedidoResponseDTO> obtenerPorId(Long id);

    Pedido actualizar(Long id, PedidoRequestDTO dto);

    void eliminar(Long id);

    PedidoResponseDTO toResponseDTO(Pedido pedido);
}
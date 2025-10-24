package com.mesago.mspedidos.service.impl;

import com.mesago.mspedidos.dto.DetallePedidoDTO;
import com.mesago.mspedidos.dto.MesaDTO;
import com.mesago.mspedidos.dto.PedidoRequestDTO;
import com.mesago.mspedidos.dto.PedidoResponseDTO;
import com.mesago.mspedidos.entity.Mesa;
import com.mesago.mspedidos.entity.Pedido;
import com.mesago.mspedidos.repository.MesaRepository;
import com.mesago.mspedidos.repository.PedidoRepository;
import com.mesago.mspedidos.service.PedidoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final MesaRepository mesaRepository;

    public PedidoServiceImpl(PedidoRepository pedidoRepository, MesaRepository mesaRepository) {
        this.pedidoRepository = pedidoRepository;
        this.mesaRepository = mesaRepository;
    }

    // 🟩 CREATE
    @Override
    public Pedido crear(PedidoRequestDTO dto) {
        Mesa mesa = mesaRepository.findById(dto.getIdMesa())
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));

        Pedido pedido = new Pedido();
        pedido.setEstado(dto.getEstado());
        pedido.setFecha(dto.getFecha());
        pedido.setTiempoPreparado(dto.getTiempoPreparado());
        pedido.setTotal(dto.getTotal());
        pedido.setIdCliente(dto.getIdCliente());
        pedido.setIdTrabajador(dto.getIdTrabajador());
        pedido.setMesa(mesa);

        return pedidoRepository.save(pedido);
    }

    // 🟦 READ - LISTAR
    @Override
    public List<PedidoResponseDTO> listar() {
        return pedidoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // 🟦 READ - OBTENER POR ID
    @Override
    public Optional<PedidoResponseDTO> obtenerPorId(Long id) {
        return pedidoRepository.findById(id)
                .map(this::toResponseDTO);
    }

    // 🟨 UPDATE
    @Override
    public Pedido actualizar(Long id, PedidoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        Mesa mesa = mesaRepository.findById(dto.getIdMesa())
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));

        pedido.setEstado(dto.getEstado());
        pedido.setFecha(dto.getFecha());
        pedido.setTiempoPreparado(dto.getTiempoPreparado());
        pedido.setTotal(dto.getTotal());
        pedido.setIdCliente(dto.getIdCliente());
        pedido.setIdTrabajador(dto.getIdTrabajador());
        pedido.setMesa(mesa);

        return pedidoRepository.save(pedido);
    }

    // 🟥 DELETE
    @Override
    public void eliminar(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new RuntimeException("Pedido no encontrado para eliminar");
        }
        pedidoRepository.deleteById(id);
    }

    // 🧩 Conversor Entidad → DTO
    @Override
    public PedidoResponseDTO toResponseDTO(Pedido pedido) {
        MesaDTO mesaDTO = new MesaDTO(
                pedido.getMesa().getIdMesa(),
                pedido.getMesa().getNumero(),
                pedido.getMesa().getCapacidad(),
                pedido.getMesa().getEstado()
        );

        List<DetallePedidoDTO> detallesDTO = pedido.getDetalles() != null
                ? pedido.getDetalles().stream()
                .map(d -> new DetallePedidoDTO(
                        d.getIdDetalle(),
                        d.getCantidad(),
                        d.getPrecioUnitario(),
                        d.getSubtotal(),
                        d.getIdMenu(),
                        pedido.getIdPedido()
                ))
                .collect(Collectors.toList())
                : null;

        return new PedidoResponseDTO(
                pedido.getIdPedido(),
                pedido.getEstado(),
                pedido.getFecha(),
                pedido.getTiempoPreparado(),
                pedido.getTotal(),
                pedido.getIdCliente(),
                pedido.getIdTrabajador(),
                mesaDTO,
                detallesDTO
        );
    }
}

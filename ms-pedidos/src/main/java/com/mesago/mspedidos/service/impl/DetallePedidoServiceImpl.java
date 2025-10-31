package com.mesago.mspedidos.service.impl;

import com.mesago.mspedidos.client.MenuClient;
import com.mesago.mspedidos.dto.DetallePedidoDTO;
import com.mesago.mspedidos.entity.DetallePedido;
import com.mesago.mspedidos.entity.Pedido;
import com.mesago.mspedidos.exception.PedidoNoEncontradoException;
import com.mesago.mspedidos.repository.DetallePedidoRepository;
import com.mesago.mspedidos.repository.PedidoRepository;
import com.mesago.mspedidos.service.DetallePedidoService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DetallePedidoServiceImpl implements DetallePedidoService {

    private final DetallePedidoRepository detalleRepository;
    private final PedidoRepository pedidoRepository;
    private final MenuClient menuClient;

    public DetallePedidoServiceImpl(DetallePedidoRepository detalleRepository,
                                    PedidoRepository pedidoRepository,
                                    MenuClient menuClient) {
        this.detalleRepository = detalleRepository;
        this.pedidoRepository = pedidoRepository;
        this.menuClient = menuClient;
    }

    @Override
    public DetallePedidoDTO crear(DetallePedidoDTO dto) {
        Pedido pedido = pedidoRepository.findById(dto.getIdPedido())
                .orElseThrow(() -> new PedidoNoEncontradoException(dto.getIdPedido()));

        Map<String, Object> menuResponse = menuClient.obtenerMenuPorId(dto.getIdMenu());
        if (menuResponse == null || !menuResponse.containsKey("precio")) {
            throw new RuntimeException("No se pudo obtener el precio del menú");
        }

        BigDecimal precio = new BigDecimal(menuResponse.get("precio").toString());
        BigDecimal subtotal = precio.multiply(BigDecimal.valueOf(dto.getCantidad()));

        DetallePedido detalle = new DetallePedido();
        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioUnitario(precio);
        detalle.setSubtotal(subtotal);
        detalle.setIdMenu(dto.getIdMenu());
        detalle.setPedido(pedido);

        detalle = detalleRepository.save(detalle);

        return toDTO(detalle);
    }

    @Override
    public List<DetallePedidoDTO> listarPorPedido(Long idPedido) {
        return detalleRepository.findByPedido_IdPedido(idPedido)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DetallePedidoDTO obtenerPorId(Long id) {
        DetallePedido detalle = detalleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle no encontrado"));
        return toDTO(detalle);
    }

    @Override
    public DetallePedidoDTO actualizar(Long id, DetallePedidoDTO dto) {
        DetallePedido detalle = detalleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle no encontrado"));

        Map<String, Object> menuResponse = menuClient.obtenerMenuPorId(dto.getIdMenu());
        BigDecimal precio = new BigDecimal(menuResponse.get("precio").toString());
        BigDecimal subtotal = precio.multiply(BigDecimal.valueOf(dto.getCantidad()));

        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioUnitario(precio);
        detalle.setSubtotal(subtotal);

        detalleRepository.save(detalle);
        return toDTO(detalle);
    }

    @Override
    public void eliminar(Long id) {
        detalleRepository.deleteById(id);
    }

    private DetallePedidoDTO toDTO(DetallePedido detalle) {
        return new DetallePedidoDTO(
                detalle.getIdDetalle(),
                detalle.getCantidad(),
                detalle.getPrecioUnitario(),
                detalle.getSubtotal(),
                detalle.getIdMenu(),
                detalle.getPedido().getIdPedido()
        );
    }
}
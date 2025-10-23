package com.mesago.mspedidos.service.impl;

import com.mesago.mspedidos.client.MenuClient;
import com.mesago.mspedidos.entity.DetallePedido;
import com.mesago.mspedidos.repository.DetallePedidoRepository;
import com.mesago.mspedidos.service.DetallePedidoService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class DetallePedidoServiceImpl implements DetallePedidoService {

    private final DetallePedidoRepository detallePedidoRepository;
    private final MenuClient menuClient;

    public DetallePedidoServiceImpl(DetallePedidoRepository detallePedidoRepository, MenuClient menuClient) {
        this.detallePedidoRepository = detallePedidoRepository;
        this.menuClient = menuClient;
    }

    @Override
    public List<DetallePedido> listar() {
        return detallePedidoRepository.findAll();
    }

    @Override
    public Optional<DetallePedido> obtenerPorId(Long id) {
        return detallePedidoRepository.findById(id);
    }

    @Override
    public DetallePedido guardar(DetallePedido detalle) {
        // Obtener precio desde ms-catalogo-menu usando Feign
        var menu = menuClient.obtenerMenuPorId(detalle.getIdMenu());

        // Calcular subtotal
        BigDecimal subtotal = menu.precio().multiply(BigDecimal.valueOf(detalle.getCantidad()));
        detalle.setPrecioUnitario(menu.precio());
        detalle.setSubtotal(subtotal);

        return detallePedidoRepository.save(detalle);
    }

    @Override
    public void eliminar(Long id) {
        detallePedidoRepository.deleteById(id);
    }
}

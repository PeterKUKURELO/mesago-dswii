package com.mesago.msproveedores.application.services.impl;

import com.mesago.msproveedores.api.dto.ProveedorInsumoRequest;
import com.mesago.msproveedores.api.dto.ProveedorInsumoResponse;
import com.mesago.msproveedores.application.services.ProveedorInsumoService;
import com.mesago.msproveedores.domain.entities.Proveedor;
import com.mesago.msproveedores.domain.entities.ProveedorInsumo;
import com.mesago.msproveedores.domain.repositories.ProveedorInsumoRepository;
import com.mesago.msproveedores.domain.repositories.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProveedorInsumoServiceImpl implements ProveedorInsumoService {

    private final ProveedorInsumoRepository proveedorInsumoRepository;
    private final ProveedorRepository proveedorRepository;

    @Override
    public List<ProveedorInsumoResponse> listar() {
        return proveedorInsumoRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProveedorInsumoResponse buscarPorId(Long id) {
        Optional<ProveedorInsumo> opt = proveedorInsumoRepository.findById(id);
        if (opt.isEmpty()) {
            System.out.println("⚠️ ProveedorInsumo no encontrado con id: " + id);
            return null;
        }
        return mapToResponse(opt.get());
    }

    @Override
    public ProveedorInsumoResponse registrar(ProveedorInsumoRequest request) {
        var proveedor = proveedorRepository.findById(request.getIdProveedor()).orElse(null);
        if (proveedor == null) {
            System.out.println("⚠️ No se encontró el proveedor con id: " + request.getIdProveedor());
            return null;
        }

        var nuevo = ProveedorInsumo.builder()
                .proveedor(proveedor)
                .idInsumo(request.getIdInsumo())
                .fechaEntrega(request.getFechaEntrega())
                .precioUnitario(request.getPrecioUnitario())
                .build();

        proveedorInsumoRepository.save(nuevo);

        return new ProveedorInsumoResponse(
                nuevo.getIdProveedorInsumo(),
                nuevo.getProveedor().getIdProveedor(),
                nuevo.getIdInsumo(),
                nuevo.getPrecioUnitario(),
                nuevo.getFechaEntrega()
        );
    }

    @Override
    public ProveedorInsumoResponse actualizar(Long id, ProveedorInsumoRequest request) {
        Optional<ProveedorInsumo> optExistente = proveedorInsumoRepository.findById(id);
        if (optExistente.isEmpty()) {
            System.out.println("⚠️ ProveedorInsumo no encontrado con id: " + id);
            return null;
        }

        var proveedor = proveedorRepository.findById(request.getIdProveedor()).orElse(null);
        if (proveedor == null) {
            System.out.println("⚠️ Proveedor no encontrado con id: " + request.getIdProveedor());
            return null;
        }

        ProveedorInsumo existente = optExistente.get();
        existente.setProveedor(proveedor);
        existente.setIdInsumo(request.getIdInsumo());
        existente.setPrecioUnitario(request.getPrecioUnitario());
        existente.setFechaEntrega(request.getFechaEntrega());

        proveedorInsumoRepository.save(existente);
        return mapToResponse(existente);
    }

    @Override
    public void eliminar(Long id) {
        if (!proveedorInsumoRepository.existsById(id)) {
            System.out.println("⚠️ No se encontró ProveedorInsumo con id: " + id);
            return;
        }
        proveedorInsumoRepository.deleteById(id);
        System.out.println("✅ ProveedorInsumo eliminado con id: " + id);
    }

    private ProveedorInsumoResponse mapToResponse(ProveedorInsumo entity) {
        return ProveedorInsumoResponse.builder()
                .idProveedorInsumo(entity.getIdProveedorInsumo())
                .idProveedor(entity.getProveedor().getIdProveedor())
                .idInsumo(entity.getIdInsumo())
                .precioUnitario(entity.getPrecioUnitario())
                .fechaEntrega(entity.getFechaEntrega())
                .build();
    }
}
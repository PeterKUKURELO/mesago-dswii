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
        ProveedorInsumo entity = proveedorInsumoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProveedorInsumo no encontrado con id: " + id));
        return mapToResponse(entity);
    }

    @Override
    public ProveedorInsumoResponse registrar(ProveedorInsumoRequest request) {
        Proveedor proveedor = proveedorRepository.findById(request.getIdProveedor())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con id: " + request.getIdProveedor()));

        ProveedorInsumo nuevo = ProveedorInsumo.builder()
                .proveedor(proveedor)
                .idInsumo(request.getIdInsumo())
                .precioUnitario(request.getPrecioUnitario())
                .fechaEntrega(request.getFechaEntrega())
                .build();

        ProveedorInsumo guardado = proveedorInsumoRepository.save(nuevo);
        return mapToResponse(guardado);
    }

    @Override
    public ProveedorInsumoResponse actualizar(Long id, ProveedorInsumoRequest request) {
        ProveedorInsumo existente = proveedorInsumoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProveedorInsumo no encontrado con id: " + id));

        Proveedor proveedor = proveedorRepository.findById(request.getIdProveedor())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con id: " + request.getIdProveedor()));

        existente.setProveedor(proveedor);
        existente.setIdInsumo(request.getIdInsumo());
        existente.setPrecioUnitario(request.getPrecioUnitario());
        existente.setFechaEntrega(request.getFechaEntrega());

        ProveedorInsumo actualizado = proveedorInsumoRepository.save(existente);
        return mapToResponse(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        proveedorInsumoRepository.deleteById(id);
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
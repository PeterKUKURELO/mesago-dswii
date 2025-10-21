package com.mesago.msproveedores.application.services;

import com.mesago.msproveedores.api.dto.ProveedorInsumoRequest;
import com.mesago.msproveedores.api.dto.ProveedorInsumoResponse;
import com.mesago.msproveedores.domain.entities.ProveedorInsumo;

import java.util.List;
import java.util.Optional;

public interface ProveedorInsumoService {
    ProveedorInsumoResponse registrar(ProveedorInsumoRequest request);
    List<ProveedorInsumoResponse> listar();
    ProveedorInsumoResponse buscarPorId(Long id);
    ProveedorInsumoResponse actualizar(Long id, ProveedorInsumoRequest request);
    void eliminar(Long id);
}

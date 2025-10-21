package com.mesago.msproveedores.api.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProveedorInsumoRequest {
    private Long idProveedor;
    private Long idInsumo;
    private Double precioUnitario;
    private LocalDate fechaEntrega;
}

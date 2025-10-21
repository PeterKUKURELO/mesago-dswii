package com.mesago.msproveedores.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorInsumoResponse {
    private Long idProveedorInsumo;
    private Long idProveedor;
    private Long idInsumo;
    private Double precioUnitario;
    private LocalDate fechaEntrega;
}
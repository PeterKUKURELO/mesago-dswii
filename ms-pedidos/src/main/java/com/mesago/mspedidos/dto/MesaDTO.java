package com.mesago.mspedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MesaDTO {
    private Long idMesa;
    private Integer numero;
    private Integer capacidad;
    private String estado;
}
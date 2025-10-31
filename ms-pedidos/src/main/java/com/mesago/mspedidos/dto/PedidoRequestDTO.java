package com.mesago.mspedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequestDTO {
    private String estado;
    private LocalDateTime fecha;
    private Time tiempoPreparado;
    private BigDecimal total;
    private Long idCliente;
    private Long idTrabajador;
    private Long idMesa;
    private List<DetallePedidoDTO> detalles;
}

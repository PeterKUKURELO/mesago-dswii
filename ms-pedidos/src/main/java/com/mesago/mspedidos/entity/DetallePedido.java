package com.mesago.mspedidos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "detalle_pedido")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalle;

    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    private Long idMenu; // referencia lógica (desde ms-catalogo-menu)

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;
}

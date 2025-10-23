package com.mesago.mspedidos.entity;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    private String estado;
    private LocalDateTime fecha;
    private Time tiempoPreparado;
    private BigDecimal total;

    private Long idCliente;    // referencia lógica
    private Long idTrabajador; // referencia lógica

    @ManyToOne
    @JoinColumn(name = "id_mesa")
    private Mesa mesa;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles;
}


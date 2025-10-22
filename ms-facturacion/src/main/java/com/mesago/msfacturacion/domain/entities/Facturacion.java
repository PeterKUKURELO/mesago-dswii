package com.mesago.msfacturacion.domain.entities;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "facturacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Facturacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_facturacion")
    private Long facturacionId;

    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fechaEmision;

    @Column(name = "metodo_pago", length = 50, nullable = false)
    private String metodoPago;

    @Column(name = "monto_total", nullable = false)
    private Double montoTotal;

    @Column(name = "numero_factura", length = 100, nullable = false)
    private String numeroFactura;

    @Column(name = "tipo", length = 50)
    private String tipo;

    @Column(name = "id_pedido")
    private Long idPedido;

    @Column(name = "id_reserva")
    private Long idReserva;
}

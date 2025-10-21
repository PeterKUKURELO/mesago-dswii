package com.mesago.msproveedores.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "t_mgo_proveedor_insumo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProveedorInsumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor_insumo")
    private Long idProveedorInsumo;

    @ManyToOne
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Proveedor proveedor;

    @Column(name = "id_insumo", nullable = false)
    private Long idInsumo;

    @Column(name = "precio_unitario", nullable = false)
    private Double precioUnitario;

    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;
}
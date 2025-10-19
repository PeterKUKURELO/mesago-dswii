package com.mesago.msinventario.domain.entities;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "T_MGO_INSUMO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Insumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_INSUMO")
    private Long idInsumo;

    @Column(name = "NOMBRE", length = 100, nullable = false)
    private String nombre;

    @Column(name = "UNIDAD_MEDIDA", length = 50)
    private String unidadMedida;

    @Column(name = "STOCK_MINIMO")
    private Integer stockMinimo;

    @Column(name = "STOCK_ACTUAL")
    private Integer stockActual;

    @Column(name = "ESTADO", length = 20)
    private String estado;
}
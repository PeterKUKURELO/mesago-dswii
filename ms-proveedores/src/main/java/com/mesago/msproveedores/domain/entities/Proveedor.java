package com.mesago.msproveedores.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_mgo_proveedor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor")
    private Long idProveedor;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 11)
    private String ruc;

    @Column(nullable = false, length = 15)
    private String telefono;

    @Column(nullable = false, length = 20)
    private String estado;
}
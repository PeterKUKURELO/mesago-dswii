package com.mesago.msauth.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "T_MGO_USUARIO")
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DNI", length = 20, nullable = false)
    private String dni;

    @Column(name = "EMAIL", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "NOMBRE", length = 100, nullable = false)
    private String name;

    @Column(name = "TELEFONO", length = 20)
    private String telefono;

    @Column(name = "ESTADO", length = 20)
    private String status;

    @Column(name = "FECHA_INGRESO")
    private LocalDate entryDate;

    @Column(name = "TURNO", length = 20)
    private String shift;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_ROL", nullable = false)
    private Role role;

}

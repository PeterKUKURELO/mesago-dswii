package com.mesago.mspedidos.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mesa")
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMesa;

    private Integer numero;
    private Integer capacidad;
    private String estado;

    @OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("mesa")
    private List<Pedido> pedidos = new ArrayList<>();;
}

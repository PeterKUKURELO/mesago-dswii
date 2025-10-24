package com.mesago.mscatalogomenu.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "categoria_menu")
public class CategoriaMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategoriaMenu;

    private String nombre;
    private String estado;

    @OneToMany(mappedBy = "categoriaMenu", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("categoriaMenu")
    private List<Menu> menus = new ArrayList<>();
}

package com.mesago.msauth.domain.entities;

import com.mesago.msauth.domain.enums.RoleName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "T_MGO_ROL")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ROL")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "CARGO", length = 50, nullable = false, unique = true)
    private RoleName name;

}

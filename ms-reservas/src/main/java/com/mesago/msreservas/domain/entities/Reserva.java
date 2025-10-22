package com.mesago.msreservas.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_reserva")
    private Long idReserva;
    
    @Column(name = "fecha_reserva", nullable = false)
    private LocalDate fechaReserva;
    
    @Column(name = "hora", nullable = false)
    private LocalTime hora;
    
    @Column(name = "numero_personas", nullable = false)
    private Integer numeroPersonas;
    
    @Column(name = "estado", length = 50)
    private String estado;
    
    @Column(name = "id_cliente")
    private Long idCliente;
    
    @Column(name = "id_mesa")
    private Long idMesa;
    
    @Column(name = "id_trabajador")
    private Long idTrabajador;
}
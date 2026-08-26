package com.thbrbz.checkpointNivel1.entities;

import com.thbrbz.checkpointNivel1.enums.ReservaStatus;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reservas")
public class Reserva implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "reservas")
    private List<Usuario> usuarios = new ArrayList<>();

    @OneToOne(mappedBy = "reserva")
    private Sala sala;

    @Enumerated(EnumType.STRING)
    private ReservaStatus status;
}

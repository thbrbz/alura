package com.thbrbz.checkpointNivel1.entities;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

public class Usuario implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String senha;

    @ManyToOne
    private Reserva reserva;
}

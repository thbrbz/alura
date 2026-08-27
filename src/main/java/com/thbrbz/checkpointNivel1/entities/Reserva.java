package com.thbrbz.checkpointNivel1.entities;

import com.thbrbz.checkpointNivel1.enums.ReservaStatus;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
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

    @Column(nullable = false)
    private LocalDateTime dataInicio;

    @Column(nullable = false)
    private LocalDateTime dataFim;

    @ManyToMany
    @JoinTable(
            name = "reserva_usuarios",
            joinColumns = @JoinColumn(name = "reserva_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> usuarios = new ArrayList<>();

    @ManyToOne
    @JoinColumn(nullable = false)
    private Sala sala;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservaStatus status;

    public Reserva(LocalDateTime dataInicio, LocalDateTime dataFim, Sala sala, List<Usuario> usuarios) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.sala = sala;
        this.usuarios = usuarios;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public Sala getSala() {
        return sala;
    }

    public ReservaStatus getStatus() {
        return status;
    }

    public void cancelar() {
        this.status = ReservaStatus.CANCELADA;
    }
}

package com.thbrbz.checkpointNivel1.entities;

import com.thbrbz.checkpointNivel1.dto.SalaDto;
import com.thbrbz.checkpointNivel1.dto.SalvaSalaDto;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "salas")
public class Sala implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Reserva reserva;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private Long capacidade;

    @Column(nullable = false)
    private LocalDate dataInicio;

    @Column(nullable = false)
    private LocalDate dataFim;

    @Column(nullable = false)
    private Boolean ativa;

    public Sala() {}

    public Sala(SalvaSalaDto dto) {
        nome = dto.nome();
        capacidade = dto.capacidade();
        dataInicio = dto.dataInicio();
        dataFim = dto.dataFim();
        ativa = true;
    }

    public Long getId() {
        return id;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public String getNome() {
        return nome;
    }

    public Long getCapacidade() {
        return capacidade;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void atualizarDados(SalaDto dto) {
        this.nome = dto.nome();
        this.capacidade = dto.capacidade();
        this.dataInicio = dto.dataInicio();
        this.dataFim = dto.dataFim();
        this.ativa = dto.ativa();
        this.reserva = dto.reserva();
    }
}

package com.thbrbz.checkpointNivel1.entities;

import com.thbrbz.checkpointNivel1.dto.AtualizaSalaDto;
import com.thbrbz.checkpointNivel1.dto.SalaDto;
import com.thbrbz.checkpointNivel1.dto.SalvaSalaDto;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "salas")
public class Sala implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL)
    private List<Reserva> reservas = new ArrayList<>();

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private Long capacidade;

    @Column(nullable = false)
    private Boolean ativa;

    public Sala() {}

    public Sala(SalvaSalaDto dto) {
        nome = dto.nome();
        capacidade = dto.capacidade();
        ativa = true;
    }

    public Long getId() {
        return id;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public String getNome() {
        return nome;
    }

    public Long getCapacidade() {
        return capacidade;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void atualizarDados(AtualizaSalaDto dto) {
        this.nome = dto.nome();
        this.capacidade = dto.capacidade();
    }

    public void desativarSala() {
        this.ativa = false;
    }
}

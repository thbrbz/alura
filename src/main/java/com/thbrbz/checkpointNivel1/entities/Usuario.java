package com.thbrbz.checkpointNivel1.entities;

import com.thbrbz.checkpointNivel1.dto.AtualizaUsuarioDto;
import com.thbrbz.checkpointNivel1.dto.SalvaUsuarioDto;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String nome;

    @ManyToMany
    @JoinTable(
            name = "usuario_reserva",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "reserva_id")
    )
    private List<Reserva> reservas = new ArrayList<>();

    public Usuario() {}

    public Usuario(SalvaUsuarioDto dto) {
        nome = dto.nome();
        senha = dto.senha();
        email = dto.email();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getNome() {
        return nome;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void atualizarDados(AtualizaUsuarioDto dto) {
        this.email = dto.email();
        this.senha = dto.senha();
        this.nome = dto.nome();
    }
}

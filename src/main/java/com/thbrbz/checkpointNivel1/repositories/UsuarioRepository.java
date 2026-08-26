package com.thbrbz.checkpointNivel1.repositories;

import com.thbrbz.checkpointNivel1.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}

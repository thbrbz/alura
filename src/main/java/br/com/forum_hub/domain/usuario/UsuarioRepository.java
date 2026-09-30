package br.com.forum_hub.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmailIgnoreCaseAndVerificadoTrue(String email);

    Optional<Usuario> findByRefreshToken(String refreshToken);

    Optional<Usuario> findByEmailIgnoreCaseOrUsernameIgnoreCase(String email, String username);

    Optional<Usuario> findByToken(String token);

    Optional<Usuario> findByUsernameIgnoreCaseAndVerificadoTrueAndAtivoTrue(String nomeUsuario);
}
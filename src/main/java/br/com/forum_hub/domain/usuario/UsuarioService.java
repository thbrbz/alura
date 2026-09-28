package br.com.forum_hub.domain.usuario;

import br.com.forum_hub.infra.exception.RegraDeNegocioException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("O usuário não foi encontrado!"));
    }

    public Usuario buscar(String email) {
        return usuarioRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o email informado!"));
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o id informado!"));
    }

    public Usuario buscarPorRefreshToken(@NotBlank String refreshToken) {
        return usuarioRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RegraDeNegocioException("Refresh token inválido!"));
    }
}

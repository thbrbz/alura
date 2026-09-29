package br.com.forum_hub.domain.usuario;

import br.com.forum_hub.infra.email.EmailService;
import br.com.forum_hub.infra.exception.RegraDeNegocioException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmailIgnoreCaseAndVerificadoTrue(username)
                .orElseThrow(() -> new UsernameNotFoundException("O usuário não foi encontrado!"));
    }

    public Usuario buscar(String email) {
        return usuarioRepository.findByEmailIgnoreCaseAndVerificadoTrue(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o email informado!"));
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o id informado!"));
    }

    public Usuario buscarPorRefreshToken(String refreshToken) {
        return usuarioRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RegraDeNegocioException("Refresh token inválido!"));
    }

    public Usuario buscarPorToken(String token) {
        return usuarioRepository.findByToken(token).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o token informado!"));
    }

    @Transactional
    public Usuario cadastrar(DadosCadastroUsuario dados) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmailIgnoreCaseOrUsernameIgnoreCase(dados.email(),  dados.username());

        if(optionalUsuario.isPresent())
            throw new RegraDeNegocioException("Já existe uma conta cadastrada com esse email ou nome de usuário!");

        String senhaCriptografada = passwordEncoder.encode(dados.senha());
        var usuario = new Usuario(dados, senhaCriptografada);

        emailService.enviarEmailVerificacao(usuario);

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void verificarEmail(String token) {
        var usuario = buscarPorToken(token);

        if (usuario.getExpiracaoToken().isBefore(LocalDateTime.now()))
            throw new RegraDeNegocioException("Link de verificação expirado!");

        usuario.verificar();
    }

    @Transactional
    public Usuario editarPerfil(Usuario usuario, DadosEdicaoUsuario dados) {
        return usuario.alterarDados(dados);
    }

    @Transactional
    public void alterarSenha(DadosAlteracaoSenha dados, Usuario logado) {
        if(!passwordEncoder.matches(dados.senhaAtual(), logado.getPassword())){
            throw new RegraDeNegocioException("Senha digitada não confere com senha atual!");
        }

        if(!dados.novaSenha().equals(dados.novaSenhaConfirmacao())){
            throw new RegraDeNegocioException("Senha e confirmação não conferem!");
        }

        String senhaCriptografada = passwordEncoder.encode(dados.novaSenha());
        logado.alterarSenha(senhaCriptografada);
    }

    @Transactional
    public void desativarUsuario(Usuario usuario) {
        usuario.desativar();
    }
}

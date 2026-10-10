package br.com.forum_hub.domain.usuario;

import br.com.forum_hub.domain.perfil.PerfilNome;
import br.com.forum_hub.domain.perfil.PerfilService;
import br.com.forum_hub.infra.email.EmailService;
import br.com.forum_hub.infra.exception.RegraDeNegocioException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public class RegistroUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final PerfilService perfilService;
    private final UsuarioService usuarioService;

    public RegistroUsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, EmailService emailService, PerfilService perfilService, UsuarioService usuarioService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.perfilService = perfilService;
        this.usuarioService = usuarioService;
    }

    @Transactional
    public Usuario cadastrar(DadosCadastroUsuario dados) {
        var usuario = criarUsuario(dados, false);
        emailService.enviarEmailVerificacao(usuario);

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario cadastrarVerificado(DadosCadastroUsuario dados) {
        var usuario = criarUsuario(dados, true);
        return usuarioRepository.save(usuario);
    }

    private Usuario criarUsuario(DadosCadastroUsuario dados, Boolean verificado) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmailIgnoreCaseOrUsernameIgnoreCase(dados.email(),  dados.username());

        if(optionalUsuario.isPresent())
            throw new RegraDeNegocioException("Já existe uma conta cadastrada com esse email ou nome de usuário!");

        var senhaCriptografada = passwordEncoder.encode(dados.senha());
        var perfil = perfilService.buscarPorNome(PerfilNome.ESTUDANTE);
        return new Usuario(dados, senhaCriptografada, perfil, verificado);
    }

    @Transactional
    public void verificarEmail(String token) {
        var usuario = usuarioService.buscarPorToken(token);

        if (usuario.getExpiracaoToken().isBefore(LocalDateTime.now()))
            throw new RegraDeNegocioException("Link de verificação expirado!");

        usuario.verificar();
    }
}

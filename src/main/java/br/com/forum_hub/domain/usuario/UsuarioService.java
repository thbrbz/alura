package br.com.forum_hub.domain.usuario;

import br.com.forum_hub.domain.perfil.DadosPerfil;
import br.com.forum_hub.domain.perfil.PerfilNome;
import br.com.forum_hub.domain.perfil.PerfilService;
import br.com.forum_hub.infra.email.EmailService;
import br.com.forum_hub.infra.exception.RegraDeNegocioException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
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
    private final PerfilService perfilService;
    private final HierarquiaService hierarquiaService;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, EmailService emailService, PerfilService perfilService, HierarquiaService hierarquiaService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.perfilService = perfilService;
        this.hierarquiaService = hierarquiaService;
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

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByUsernameIgnoreCaseAndVerificadoTrueAndAtivoTrue(email)
                .orElseThrow(() -> new UsernameNotFoundException("O usuário não foi encontrado com o  email informado!"));
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
    public void desativarUsuario(Long id, Usuario logado) {
        var usuario = usuarioRepository.findById(id).orElseThrow();

        if(hierarquiaService.usuarioNaoTemPermissoes(logado, usuario, "ROLE_ADMIN"))
            throw new AccessDeniedException("Não é possivel realizar essa operação!");

        usuario.desativar();
    }

    @Transactional
    public void reativarUsuario(Long id) {
        var usuario = usuarioRepository.findById(id).orElseThrow();
        usuario.reativar();
    }

    @Transactional
    public Usuario adicionarPerfil(Long id, DadosPerfil dados) {
        var usuario = buscarPorId(id);
        var perfil = perfilService.buscarPorNome(dados.perfilNome());
        usuario.adicionarPerfil(perfil);

        return usuario;
    }

    @Transactional
    public Usuario removerPerfil(Long id, DadosPerfil dados) {
        var usuario = usuarioRepository.findById(id).orElseThrow();
        var perfil = perfilService.buscarPorNome(dados.perfilNome());
        usuario.removerPerfil(perfil);

        return usuario;
    }
}

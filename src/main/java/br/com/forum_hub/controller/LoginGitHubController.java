package br.com.forum_hub.controller;

import br.com.forum_hub.domain.autenticacao.DadosTokenAcesso;
import br.com.forum_hub.domain.autenticacao.TokenService;
import br.com.forum_hub.domain.autenticacao.github.LoginGithubService;
import br.com.forum_hub.domain.usuario.RegistroUsuarioService;
import br.com.forum_hub.domain.usuario.Usuario;
import br.com.forum_hub.domain.usuario.UsuarioService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/login/github")
public class LoginGitHubController {

    private final LoginGithubService loginGithubService;
    private final UsuarioService usuarioService;
    private final TokenService tokenService;
    private final RegistroUsuarioService registroUsuarioService;

    public LoginGitHubController(LoginGithubService loginGithubService, UsuarioService usuarioService, TokenService tokenService, RegistroUsuarioService registroUsuarioService) {
        this.loginGithubService = loginGithubService;
        this.usuarioService = usuarioService;
        this.tokenService = tokenService;
        this.registroUsuarioService = registroUsuarioService;
    }

    @GetMapping
    public ResponseEntity<Void> redirecionarGithub() {
        var url = loginGithubService.gerarUrl();

        var headers = new HttpHeaders();
        headers.setLocation(URI.create(url));

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @GetMapping("/autorizado")
    public ResponseEntity<DadosTokenAcesso> autenticarUsuarioOAuth(@RequestParam String code) {
        var email = loginGithubService.obterEmail(code);
        var usuario = usuarioService.buscarPorEmail(email);

        Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        String refreshToken = tokenService.gerarRefreshToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenAcesso(token, refreshToken));
    }

    @GetMapping("/registro")
    public ResponseEntity<Void> redirecionarRegistroGithub(){
        var url = loginGithubService.gerarUrlRegistro();
        var headers = new HttpHeaders();

        headers.setLocation(URI.create(url));

        return new ResponseEntity<>(headers, HttpStatus.FOUND);

    }

    @GetMapping("/registro-autorizado")
    public ResponseEntity<DadosTokenAcesso> registrarOAuth(@RequestParam String code){
        var dadosUsuario = loginGithubService.obterDadosOAuth(code);
        var usuario = registroUsuarioService.cadastrarVerificado(dadosUsuario);

        var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String tokenAcesso = tokenService.gerarToken(usuario);
        String refreshToken = tokenService.gerarRefreshToken(usuario);

        return ResponseEntity.ok(new DadosTokenAcesso(tokenAcesso, refreshToken));
    }
}

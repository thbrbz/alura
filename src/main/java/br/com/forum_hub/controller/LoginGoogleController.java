package br.com.forum_hub.controller;

import br.com.forum_hub.domain.autenticacao.DadosTokenAcesso;
import br.com.forum_hub.domain.autenticacao.TokenService;
import br.com.forum_hub.domain.autenticacao.google.LoginGoogleService;
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
@RequestMapping("/login/google")
public class LoginGoogleController {

    private final LoginGoogleService loginGoogleService;
    private final UsuarioService usuarioService;
    private final TokenService tokenService;
    private final RegistroUsuarioService registroUsuarioService;

    public LoginGoogleController(LoginGoogleService loginGoogleService, UsuarioService usuarioService, TokenService tokenService, RegistroUsuarioService registroUsuarioService) {
        this.loginGoogleService = loginGoogleService;
        this.usuarioService = usuarioService;
        this.tokenService = tokenService;
        this.registroUsuarioService = registroUsuarioService;
    }

    @GetMapping
    public ResponseEntity<Void> redirecionarGoogle() {
        var url = loginGoogleService.gerarUrl();

        var headers = new HttpHeaders();
        headers.setLocation(URI.create(url));

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @GetMapping("/autorizado")
    public ResponseEntity<DadosTokenAcesso> autenticarUsuarioOAuth(@RequestParam String code) {
        var email = loginGoogleService.obterEmail(code);
        var usuario = usuarioService.buscarPorEmail(email);

        Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        String refreshToken = tokenService.gerarRefreshToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenAcesso(token, refreshToken));
    }

    @GetMapping("/registro")
    public ResponseEntity<Void> redirecionarRegistroGoogle(){
        var url = loginGoogleService.gerarUrlRegistro();
        var headers = new HttpHeaders();

        headers.setLocation(URI.create(url));

        return new ResponseEntity<>(headers, HttpStatus.FOUND);

    }

    @GetMapping("/registro-autorizado")
    public ResponseEntity<DadosTokenAcesso> registrarOAuth(@RequestParam String code){
        var dadosUsuario = loginGoogleService.obterDadosOAuth(code);
        var usuario = registroUsuarioService.cadastrarVerificado(dadosUsuario);

        var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String tokenAcesso = tokenService.gerarToken(usuario);
        String refreshToken = tokenService.gerarRefreshToken(usuario);

        return ResponseEntity.ok(new DadosTokenAcesso(tokenAcesso, refreshToken));
    }
}

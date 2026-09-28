package br.com.forum_hub.controller;

import br.com.forum_hub.domain.autenticacao.DadosAtualizarToken;
import br.com.forum_hub.domain.autenticacao.DadosLogin;
import br.com.forum_hub.domain.autenticacao.DadosTokenAcesso;
import br.com.forum_hub.domain.autenticacao.TokenService;
import br.com.forum_hub.domain.usuario.Usuario;
import br.com.forum_hub.domain.usuario.UsuarioService;
import br.com.forum_hub.infra.exception.RegraDeNegocioException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioService usuarioService;

    public AutenticacaoController(AuthenticationManager authenticationManager, TokenService tokenService, UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<DadosTokenAcesso> login(@RequestBody @Valid DadosLogin dados){
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        var authentication = authenticationManager.authenticate(authenticationToken);

        String token = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        String refreshToken = tokenService.gerarRefreshToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenAcesso(token, refreshToken));
    }

    @PostMapping("/atualizar-token")
    public ResponseEntity<DadosTokenAcesso> atualizarToken(@Valid @RequestBody DadosAtualizarToken dados){
        String refreshToken = dados.refreshToken();
        Long idUsuario = Long.valueOf(tokenService.verificarToken(refreshToken));
        var usuario = usuarioService.buscarPorId(idUsuario);

        String token = tokenService.gerarToken(usuario);
        String tokenRefresh = tokenService.gerarRefreshToken(usuario);

        return ResponseEntity.ok(new DadosTokenAcesso(token, tokenRefresh));
    }

    @PostMapping("/atualizar-token-usuario")
    public ResponseEntity<DadosTokenAcesso> atualizarTokenUsuario(@Valid @RequestBody DadosAtualizarToken dados){
        var usuario = usuarioService.buscarPorRefreshToken(dados.refreshToken());

        if (usuario.refreshTokenExpirado())
            throw new RegraDeNegocioException("Refresh token expirado!");

        String token = tokenService.gerarToken(usuario);
        String novoRefreshToken = usuario.novoRefreshToken();

        return ResponseEntity.ok(new DadosTokenAcesso(token, novoRefreshToken));
    }
}

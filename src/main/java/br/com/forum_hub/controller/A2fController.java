package br.com.forum_hub.controller;

import br.com.forum_hub.domain.autenticacao.DadosA2f;
import br.com.forum_hub.domain.autenticacao.DadosTokenAcesso;
import br.com.forum_hub.domain.autenticacao.TokenService;
import br.com.forum_hub.domain.usuario.A2fService;
import br.com.forum_hub.domain.usuario.Usuario;
import br.com.forum_hub.domain.usuario.UsuarioService;
import br.com.forum_hub.infra.seguranca.totp.TotpService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class A2fController {

    private final A2fService a2fService;
    private final UsuarioService usuarioService;
    private final TotpService totpService;
    private final TokenService tokenService;

    public A2fController(A2fService a2fService, UsuarioService usuarioService, TotpService totpService, TokenService tokenService) {
        this.a2fService = a2fService;
        this.usuarioService = usuarioService;
        this.totpService = totpService;
        this.tokenService = tokenService;
    }

    @PatchMapping("configurar-a2f")
    public ResponseEntity<String> gerarQrCode(@AuthenticationPrincipal Usuario logado){
        var url = a2fService.gerarQrCode(logado);
        return ResponseEntity.ok(url);

    }

    @PatchMapping("ativar-a2f")
    public ResponseEntity<Void> ativarA2f(@RequestParam String codigo, @AuthenticationPrincipal Usuario logado){
        a2fService.ativarA2f(codigo, logado);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("verificar-a2f")
    public ResponseEntity<DadosTokenAcesso> verificarA2f(@Valid @RequestBody DadosA2f dados) {
        var usuario = usuarioService.buscarPorEmail(dados.email());
        var codigoValido = totpService.verificarCodigo(dados.codigo(), usuario);

        if (!codigoValido)
            throw new BadCredentialsException("Código inválido!");

        String token = tokenService.gerarToken(usuario);
        String refreshToken = tokenService.gerarRefreshToken(usuario);

        return ResponseEntity.ok(new DadosTokenAcesso(token, refreshToken, usuario.isA2fAtiva()));
    }
}

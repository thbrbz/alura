package dev.thbrbz.med.voll.api.controller;

import dev.thbrbz.med.voll.api.domain.usuario.DadosAutenticacao;
import dev.thbrbz.med.voll.api.domain.usuario.Usuario;
import dev.thbrbz.med.voll.api.infra.security.DadosTokenJwt;
import dev.thbrbz.med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AutenticacaoController.ENDPOINT)
public class AutenticacaoController {

    public static final String ENDPOINT = "/login";

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> autenticar(@RequestBody DadosAutenticacao dados) {
        var authenicationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var autenticacao = authenticationManager.authenticate(authenicationToken);
        var tokenJwt = tokenService.gerarToken((Usuario) autenticacao.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJwt(tokenJwt));
    }
}

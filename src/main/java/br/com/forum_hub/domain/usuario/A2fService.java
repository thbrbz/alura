package br.com.forum_hub.domain.usuario;

import br.com.forum_hub.infra.exception.RegraDeNegocioException;
import br.com.forum_hub.infra.seguranca.totp.TotpService;
import org.springframework.transaction.annotation.Transactional;

public class A2fService {

    private final TotpService totpService;

    public A2fService(TotpService totpService) {
        this.totpService = totpService;
    }

    @Transactional
    public String gerarQrCode(Usuario logado) {
        var secret = totpService.gerarSecret();
        logado.gerarSecret(secret);

        return totpService.gerarQrCode(logado);
    }

    @Transactional
    public void ativarA2f(String codigo, Usuario logado) {
        if (logado.isA2fAtiva())
            throw new RegraDeNegocioException("Sua Autenticação de dois fatores já está ativa!");

        var codigoValido = totpService.verificarCodigo(codigo, logado);

        if  (!codigoValido)
            throw new RegraDeNegocioException("Código inválido!");

        logado.ativarA2f();
    }
}

package br.com.forum_hub.domain.usuario;

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

    public void ativarA2f(String codigo, Usuario logado) {

    }
}

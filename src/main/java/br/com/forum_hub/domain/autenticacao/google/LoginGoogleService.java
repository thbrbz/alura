package br.com.forum_hub.domain.autenticacao.google;

import br.com.forum_hub.domain.autenticacao.github.DadosEmail;
import br.com.forum_hub.domain.usuario.DadosCadastroUsuario;
import com.auth0.jwt.JWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.UUID;

@Service
public class LoginGoogleService {

    @Value("${google.oauth.client.id}")
    private String CLIENT_ID;

    @Value("${google.oauth.client.secret}")
    private String CLIENT_SECRET;

    private final String URL_API = "https://www.googleapis.com/auth";
    private final String URL_API_OAUTH = "https://oauth2.google.com";
    private final String URL = "https://accounts.google.com/o/oauth2/v2/auth";
    private final String REDIRECT_URI = "http://localhost:8080/login/google/autorizado";
    private final String REDIRECT_URI_REGISTRO = "http://localhost:8080/login/google/registro-autorizado";

    private final RestClient restClient;

    public LoginGoogleService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public String gerarUrl() {
        return URL +
                "?client_id=" + CLIENT_ID +
                "&redirect_uri=" + REDIRECT_URI +
                "&scope=" + URL_API + "/userinfo.email" +
                "&response_type=code";
    }

    public String obterEmail(String code) {
        var token = obterToken(code, REDIRECT_URI);
        var decodedJWT = JWT.decode(token);

        return decodedJWT.getClaim("email").asString();
    }

    public String gerarUrlRegistro() {
        return URL +
                "?client_id=" + CLIENT_ID +
                "&redirect_uri=" + REDIRECT_URI +
                "&scope=" + URL_API + "/userinfo.email" +
                "%20"+ URL_API +"/userinfo.profile" +
                "&response_type=code";

    }

    public DadosCadastroUsuario obterDadosOAuth(String codigo){
        var token = obterToken(codigo, REDIRECT_URI_REGISTRO);
        var decodedJWT = JWT.decode(token);

        var email = decodedJWT.getClaim("email").asString();
        var senha = UUID.randomUUID().toString();
        var nomeCompleto = decodedJWT.getClaim("name").asString();
        var nomeUsuario = email.split("@")[0];

        return new DadosCadastroUsuario(email, senha, nomeCompleto, nomeUsuario, null, null);
    }

    private String enviarRequisicaoEmail(HttpHeaders headers) {
        var resposta = restClient.get()
                .uri(URL_API + "/user/emails")
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(DadosEmail[].class);

        if (resposta == null)
            throw new RuntimeException("Falha ao enviar requisicao email");

        for(DadosEmail d: resposta)
            if(d.primary() && d.verified())
                return d.email();

        return null;
    }

    private String obterToken(String code, String uri) {
        var resposta = restClient.post()
                .uri(URL_API_OAUTH + "/o/oauth2/v2/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "code", code,
                        "client_id", CLIENT_ID,
                        "client_secret", CLIENT_SECRET,
                        "redirect_uri", uri,
                        "grant_type", "authorization_code"
                ))
                .retrieve()
                .body(Map.class);

        if (resposta == null)
            throw new RuntimeException("Falha ao obter token");

        return resposta.get("id_token").toString();
    }

    public String renovarAccessToken(String refreshToken) {
        var resposta = restClient.post()
                .uri(URL_API_OAUTH + "/token")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "client_id", CLIENT_ID,
                        "client_secret", CLIENT_SECRET,
                        "refresh_token", refreshToken,
                        "grant_type", "refresh_token"
                ))
                .retrieve()
                .body(Map.class);

        if (resposta == null)
            throw new RuntimeException("Falha ao tentar renovar token");

        return resposta.get("access_token").toString();

    }
}

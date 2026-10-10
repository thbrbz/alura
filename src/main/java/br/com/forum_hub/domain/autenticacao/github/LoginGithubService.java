package br.com.forum_hub.domain.autenticacao.github;

import br.com.forum_hub.domain.usuario.DadosCadastroUsuario;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Service
public class LoginGithubService {

    @Value("${github.oauth.client.id}")
    private String CLIENT_ID;

    @Value("${github.oauth.client.secret}")
    private String CLIENT_SECRET;

    private final String URL_API = "https://api.github.com";
    private final String URL = "https://github.com/login/oauth";
    private final String REDIRECT_URI = "http://localhost:8080/login/github/autorizado";
    private final RestClient restClient;

    public LoginGithubService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public String gerarUrl() {
        return URL + "/authorize" +
                "?client_id=" + CLIENT_ID +
                "&redirect_uri=" + REDIRECT_URI +
                "&scope=user:email";
    }

    public String obterEmail(String code) {
        var token = obterToken(code);

        var headers = new HttpHeaders();
        headers.setBearerAuth(token);

        var resposta = restClient.get()
                .uri(URL_API + "/user/emails")
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(DadosEmail[].class);

        for(DadosEmail d: resposta)
            if(d.primary() && d.verified())
                return d.email();

        return null;
    }

    public String gerarUrlRegistro() {
        return URL + "/authorize" +
                "?client_id=" + CLIENT_ID +
                "&redirect_uri=" + REDIRECT_URI +
                "&scope=read:user,user:email";

    }

    public DadosCadastroUsuario obterDadosOAuth(String codigo){
        var accessToken = obterToken(codigo);
        var headers = new HttpHeaders();

        headers.setBearerAuth(accessToken);

        var email = enviarRequisicaoEmail(headers);

        var resposta = restClient.get()
                .uri(URL_API + "/user")
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(Map.class);

        var nomeCompleto = resposta.get("name").toString();
        var nomeUsuario = resposta.get("login").toString();
        var senha = UUID.randomUUID().toString();

        return new DadosCadastroUsuario(email, senha, nomeCompleto, nomeUsuario, null, null);
    }

    private String enviarRequisicaoEmail(HttpHeaders headers) {
        var resposta = restClient.get()
                .uri(URL_API + "/user/emails")
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(DadosEmail[].class);

        for(DadosEmail d: resposta)
            if(d.primary() && d.verified())
                return d.email();

        return null;
    }

    private String obterToken(String code) {
        var resposta = restClient.post()
                .uri(URL +"/access_token")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Map.of("code", code, "client_id", CLIENT_ID,
                        "client_secret", CLIENT_SECRET, "redirect_uri", REDIRECT_URI))
                .retrieve()
                .body(Map.class);

        return resposta.get("access_token").toString();
    }
}

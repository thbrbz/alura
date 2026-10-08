package br.com.forum_hub.domain.autenticacao.github;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class LoginGithubService {

    private final Dotenv dotenv = Dotenv.load();

    private final String URL = "https://github.com/login/oauth";
    private final String CLIENT_ID = dotenv.get("GITHUB_CLIENT_ID");
    private final String CLIENT_SECRET = dotenv.get("GITHUB_CLIENT_SECRET");
    private final String REDIRECT_URI = "http://localhost:8080/login/github/autorizado";
    private final RestClient restClient;

    public LoginGithubService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public String gerarUrl() {
        return URL + "/authorize" +
                "?client_id=" + CLIENT_ID +
                "&redirect_uri=" + REDIRECT_URI +
                "&scope=read:user,user:email";
    }

    public String obterToken(String code) {
        var resposta = restClient.post()
                .uri(URL +"/access_token")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Map.of("code", code, "client_id", CLIENT_ID,
                        "client_secret", CLIENT_SECRET, "redirect_uri", REDIRECT_URI))
                .retrieve()
                .body(String.class);

        return resposta;
    }
}

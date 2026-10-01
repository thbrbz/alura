package dev.thbrbz.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class TradutorDeepLService {

    private static final String API_URL = "https://api-free.deepl.com/v2/translate";
    private static final HttpClient client = HttpClient.newHttpClient();

    private final String apiKey = "979a1b87-aa1e-466e-8540-1b5f11d80402:fx";

    public TradutorDeepLService() {
        if (apiKey.isBlank()) {
            throw new RuntimeException("DEEPL_API_KEY não configurada");
        }
    }

    public String traduzir(String texto) {
        try {
            String body =
                    "text=" + URLEncoder.encode(texto, StandardCharsets.UTF_8) +
                            "&source_lang=EN" +
                            "&target_lang=PT-BR";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Authorization", "DeepL-Auth-Key " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException(
                        "Erro DeepL HTTP " + response.statusCode() + " - " + response.body()
                );
            }

            JsonObject json =
                    JsonParser.parseString(response.body()).getAsJsonObject();

            return json
                    .getAsJsonArray("translations")
                    .get(0)
                    .getAsJsonObject()
                    .get("text")
                    .getAsString();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao traduzir com DeepL", e);
        }
    }
}

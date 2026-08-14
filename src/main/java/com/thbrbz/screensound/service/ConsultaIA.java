package com.thbrbz.screensound.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultaIA {
    static Dotenv env = Dotenv.load();

    public static String obterInformacao(String texto) {
        try {
            Client client = Client.builder().apiKey(env.get("GEMINI_IA_KEY")).build();

            GenerateContentResponse response =
                    client.models.generateContent("gemini-3.5-flash", "Me fale sobre o artista: " + texto, null);

            return response.text();
        } catch (Exception ex) {
            Logger.getLogger(ConsultaIA.class.getName()).log(Level.SEVERE, "Erro ao tentar realizar consulta a IA: ", ex);
        }

        return texto;
    }
}

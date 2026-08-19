package cursos.java.consumindoAPIGravandoArquivosELidandoComErros.desafios;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.Strictness;
import cursos.java.consumindoAPIGravandoArquivosELidandoComErros.entidades.records.Pessoa;

public class ConversaoJsonParaObjetoFlexivel {

    public static void main(String[] args) {
        String jsonPessoa = "{\"nome\":\"Rodrigo\",\"cidade\":\"Brasília\"}";

        Gson gson = new GsonBuilder().setStrictness(Strictness.LENIENT).create();
        Pessoa pessoa = gson.fromJson(jsonPessoa, Pessoa.class);

        System.out.println("Objeto Pessoa: " + pessoa);
    }
}

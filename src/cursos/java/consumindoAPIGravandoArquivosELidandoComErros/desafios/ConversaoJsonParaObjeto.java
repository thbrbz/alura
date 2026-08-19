package cursos.java.consumindoAPIGravandoArquivosELidandoComErros.desafios;

import com.google.gson.Gson;
import cursos.java.consumindoAPIGravandoArquivosELidandoComErros.entidades.records.Pessoa;

public class ConversaoJsonParaObjeto {

    public static void main(String[] args) {
        String jsonPessoa = "{\"nome\":\"Rodrigo\",\"idade\":20,\"cidade\":\"Brasília\"}";

        Gson gson = new Gson();
        Pessoa pessoa = gson.fromJson(jsonPessoa, Pessoa.class);

        System.out.println("Objeto Pessoa: " + pessoa);
    }
}

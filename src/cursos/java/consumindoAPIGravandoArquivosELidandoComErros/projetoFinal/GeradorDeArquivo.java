package cursos.java.consumindoAPIGravandoArquivosELidandoComErros.projetoFinal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

public class GeradorDeArquivo {

    public void salvaJson(Endereco endereco) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter arquivo = new FileWriter(endereco.cep() + ".json");
        arquivo.write(gson.toJson(endereco));
        arquivo.close();
    }
}

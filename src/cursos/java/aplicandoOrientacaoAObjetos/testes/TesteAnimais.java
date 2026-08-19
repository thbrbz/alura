package cursos.java.aplicandoOrientacaoAObjetos.testes;

import cursos.java.aplicandoOrientacaoAObjetos.Cachorro;
import cursos.java.aplicandoOrientacaoAObjetos.Gato;

public class TesteAnimais {
    public static void main(String[] args) {
        Cachorro cachorro = new Cachorro();
        cachorro.emitirSom();
        cachorro.abanarRabo();

        Gato gato = new Gato();
        gato.emitirSom();
        gato.arranharMoveis();
    }
}

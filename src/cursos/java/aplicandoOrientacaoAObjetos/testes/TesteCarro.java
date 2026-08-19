package cursos.java.aplicandoOrientacaoAObjetos.testes;

import cursos.java.aplicandoOrientacaoAObjetos.ModeloCarro;

public class TesteCarro {
    public static void main(String[] args) {
        ModeloCarro meuCarro = new ModeloCarro();
        meuCarro.definirModelo("Sedan");
        meuCarro.definirPrecos(30000, 32000, 35000);
        meuCarro.exibirInfo();
    }
}

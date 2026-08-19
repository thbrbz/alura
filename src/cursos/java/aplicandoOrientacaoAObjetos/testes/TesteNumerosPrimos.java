package cursos.java.aplicandoOrientacaoAObjetos.testes;

import cursos.java.aplicandoOrientacaoAObjetos.GeradorPrimo;
import cursos.java.aplicandoOrientacaoAObjetos.NumerosPrimos;
import cursos.java.aplicandoOrientacaoAObjetos.VerificadorPrimo;

public class TesteNumerosPrimos {
    public static void main(String[] args) {
        VerificadorPrimo verificador = new VerificadorPrimo();
        verificador.verificarSeEhPrimo(17);

        GeradorPrimo gerador = new GeradorPrimo();
        int proximoPrimo = gerador.gerarProximoPrimo(17);
        System.out.println("O próximo primo após 17 é: " + proximoPrimo);

        NumerosPrimos numerosPrimos = new NumerosPrimos();
        numerosPrimos.listarPrimos(30);
    }
}

package cursos.java.consumindoAPIGravandoArquivosELidandoComErros.projetoFinal;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        ConsultaCEP consulta = new ConsultaCEP();

        System.out.println("Digite um número de CEP para ser consultado:");
        var cep = leitor.nextLine();

        try {
            Endereco endereco = consulta.buscaEndereco(cep);
            GeradorDeArquivo geradorDeArquivo = new GeradorDeArquivo();
            geradorDeArquivo.salvaJson(endereco);
            System.out.println(endereco);
        } catch (RuntimeException | IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Finalizando aplicação");
        }
    }
}

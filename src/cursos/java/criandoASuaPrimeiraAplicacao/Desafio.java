package cursos.java.criandoASuaPrimeiraAplicacao;

import java.util.Scanner;

public class Desafio {
    public static void main(String[] args) {
        String nome = "Thiago Barboza";
        String tipoConta = "Corrente";
        double saldo = 0;
        int opcao = 0;

        System.out.println("********************************");
        System.out.println("\nNome do cliente: " + nome);
        System.out.println("Tipo da conta: " + tipoConta);
        System.out.println("Saldo atual: " + saldo);
        System.out.println("\n********************************");

        String menu = """
                \n** Digite sua opção: **
                1 - Consultar saldo.
                2 - Transferir valor.
                3 - Receber valor.
                4 - Sair.
                """;

        Scanner leitor = new Scanner(System.in);

        while (opcao != 4) {
            System.out.println(menu);
            opcao = leitor.nextInt();

            if (opcao == 1) {
                System.out.println("Seu saldo é: " + saldo);
            } else if (opcao == 2) {
                System.out.println("Qual valor deseja transferir?");
                double valor = leitor.nextDouble();

                if (valor > saldo) {
                    System.out.println("Não há saldo suficiente para realizar a transferência!");
                } else {
                    saldo -= valor;
                    System.out.println("O saldo atualizado é: " + saldo);
                }
            } else if (opcao == 3) {
                System.out.println("Informe valor a receber: ");
                double valor = leitor.nextDouble();
                saldo += valor;
                System.out.println("O saldo atualizado é: " + saldo);
            } else if (opcao != 4) {
                System.out.println("Opção inválida!");
            }
        }
    }
}

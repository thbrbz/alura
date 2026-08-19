package cursos.java.trabalhandoComListasEColecoesDeDados.projetoFinal;

import cursos.java.aplicandoOrientacaoAObjetos.Produto;

import java.util.Collections;
import java.util.Scanner;

public class principal {
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        Compra compra = new Compra();

        System.out.println("Digite o limite do cartão:");
        double limite = leitor.nextDouble();

        CartaoCredito cartao = new CartaoCredito(limite);

        int sair = 1;
        while (sair != 0) {
            System.out.println("Informe o produto que será comprado:");
            String produto = leitor.next();

            System.out.println("Digite o valor do produto:");
            double valor = leitor.nextDouble();

            boolean compraEfetuada = compra.lancaCompra(cartao, produto, valor);

            System.out.println();

            if (compraEfetuada) {
                System.out.println("Compra realizada com sucesso!");
            } else {
                System.out.println("Saldo insuficiente para realizar compra!");
            }

            System.out.println("Saldo atual: " + cartao.getSaldo() + "\n");

            System.out.println("Digite 0 para sair ou 1 para continuar");
            sair = leitor.nextInt();
        }

        System.out.println("****************************");
        System.out.println("COMPRAS EFETUADAS:\n");

        Collections.sort(compra.produtos);

        for (Produto p : compra.produtos)
            System.out.println(p.toString());

        System.out.println("\n****************************");
    }
}

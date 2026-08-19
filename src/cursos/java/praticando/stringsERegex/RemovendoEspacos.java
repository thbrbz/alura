package cursos.java.praticando.stringsERegex;

import java.util.Scanner;

public class RemovendoEspacos {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();
        scanner.close();

        String nomeFormatado = nome.trim();
        System.out.println("Nome sem espaços: " + nomeFormatado);
    }
}

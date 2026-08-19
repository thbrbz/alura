package cursos.java.praticando.stringsERegex;

import java.util.Scanner;

public class ConvertendoTexto {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o texto: ");
        String texto = scanner.nextLine();
        scanner.close();

        String maiusculas = texto.toUpperCase();
        String minusculas = texto.toLowerCase();

        System.out.println("Texto em maiúsculas: " + maiusculas);
        System.out.println("Texto em minúsculas: " + minusculas);
    }
}

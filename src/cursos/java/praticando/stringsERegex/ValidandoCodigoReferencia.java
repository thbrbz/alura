package cursos.java.praticando.stringsERegex;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ValidandoCodigoReferencia {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o código de referência: ");
        String codigo = scanner.nextLine();
        scanner.close();

        Pattern pattern = Pattern.compile("^[A-Z]{3}-\\d{4}$");
        Matcher matcher = pattern.matcher(codigo);

        System.out.println(matcher.matches() ? "O código de referência está válido." : "O código de referência é inválido.");
    }
}

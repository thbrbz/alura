package cursos.java.praticando.dataEHora;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class CalculandoDataDeEntrega {
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);

        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        System.out.println("Data de início:");
        String data = leitor.nextLine();

        String dataEntrega = LocalDate.parse(data).plusDays(15).format(formatoData);

        System.out.println("\nData de entrega: " + dataEntrega);
    }
}

package cursos.java.praticando.dataEHora;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Scanner;

public class DiferencaEntreHoras {
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);

        System.out.println("Primeiro horário:");
        String h1 = leitor.nextLine();

        System.out.println("Segundo horário:");
        String h2 = leitor.nextLine();

        LocalTime l1 = LocalTime.parse(h1);
        LocalTime l2 = LocalTime.parse(h2);

        Duration duracao = Duration.between(l1, l2);

        System.out.println("\nDiferença de tempo: " + duracao.toHours() + " horas e " + duracao.toMinutesPart() + " minutos");
    }
}

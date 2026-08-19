package cursos.java.praticando.dataEHora;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LembreteDePagamento {
    public static void main(String[] args) {
        String dataFormatada = LocalDate
                .of(2025, 3, 30)
                .minusDays(5)
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        System.out.println("Data do lembrete: " + dataFormatada);
    }
}

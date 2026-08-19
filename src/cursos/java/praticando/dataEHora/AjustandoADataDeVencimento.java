package cursos.java.praticando.dataEHora;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AjustandoADataDeVencimento {
    public static void main(String[] args) {
        String dataFormatada = LocalDate
                .of(2025,3, 20)
                .plusMonths(1)
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        System.out.println(dataFormatada);
    }
}

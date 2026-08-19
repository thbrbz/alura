package cursos.java.praticando.dataEHora;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FormatandoDataEHoraParaRelatorios {
    public static void main(String[] args) {
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

        System.out.println("Data formatada: " + LocalDate.now().format(formatoData));
        System.out.println("Hora formatada: " + LocalTime.now().format(formatoHora));
    }
}

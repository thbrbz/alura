package cursos.java.praticando.dataEHora;

import java.time.LocalDate;

public class VerificandoADataDeUmEvento {
    public static void main(String[] args) {
        boolean ocorreu = LocalDate.now().isAfter(LocalDate.of(2025,3,10));

        System.out.println(ocorreu ? "O evento já ocorreu." : "O evento ainda não ocorreu.");
    }
}

package cursos.java.praticando.dataEHora;

import java.time.LocalDate;
import java.time.LocalTime;

public class DataHoraAtual {
    public static void main(String[] args) {
        String tarefa = "Enviar relatório semanal";

        System.out.println("Tarefa: \"" + tarefa + "\"");
        System.out.println("Data atual: " + LocalDate.now());
        System.out.println("Hora atual: " + LocalTime.now());
    }
}

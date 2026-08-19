package cursos.java.praticando.dataEHora;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class FusoHorario {
    public static void main(String[] args) {
        String time = LocalTime
                .now(ZoneId.of("Asia/Tokyo"))
                .format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        System.out.println("Horário atual em Tóquio: " + time);
    }
}

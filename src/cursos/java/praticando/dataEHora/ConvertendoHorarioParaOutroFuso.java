package cursos.java.praticando.dataEHora;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ConvertendoHorarioParaOutroFuso {
    public static void main(String[] args) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");

        ZonedDateTime local = ZonedDateTime.now();
        ZonedDateTime sydney = local.withZoneSameInstant(ZoneId.of("Australia/Sydney"));

        System.out.println("Horário atual no sistema: " + local.format(formato));
        System.out.println("Horário atual em Sydney: " + sydney.format(formato));
    }
}

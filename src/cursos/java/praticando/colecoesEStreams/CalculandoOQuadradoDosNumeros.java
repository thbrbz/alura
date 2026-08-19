package cursos.java.praticando.colecoesEStreams;

import java.util.List;

public class CalculandoOQuadradoDosNumeros {
    public static void main(String[] args) {
        List<Integer> numeros = List.of(2, 3, 5, 7, 11);

        List<Integer> quadrados = numeros.stream()
                .map(numero -> numero * numero)
                .toList();

        System.out.println("Quadrados dos números: " + quadrados);
    }
}

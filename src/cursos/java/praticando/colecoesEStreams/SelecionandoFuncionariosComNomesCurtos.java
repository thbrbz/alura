package cursos.java.praticando.colecoesEStreams;

import java.util.List;

public class SelecionandoFuncionariosComNomesCurtos {
    public static void main(String[] args) {
        List<String> funcionarios = List.of("Ana", "Bruno", "Carlos", "Amanda", "Alice", "Daniel", "Caroline");

        List<String> nomesCurtos = funcionarios.stream()
                .filter(nome -> nome.length() <= 5)
                .toList();

        System.out.println(nomesCurtos);
    }
}

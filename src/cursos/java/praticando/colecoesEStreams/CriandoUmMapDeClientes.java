package cursos.java.praticando.colecoesEStreams;

import java.util.HashMap;
import java.util.Map;

public class CriandoUmMapDeClientes {
    public static void main(String[] args) {
        Map<Integer, String> clientes = new HashMap<>();

        clientes.put(1, "Maria");
        clientes.put(2, "Marcos");
        clientes.put(3, "Ana");

        System.out.println("O nome do cliente com ID 2 é: " + clientes.get(2));
    }
}

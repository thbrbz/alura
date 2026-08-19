package cursos.java.praticando.encapsulamento;

import java.util.ArrayList;

public class Disciplina {
    private String nome;
    private ArrayList<Double> notas;

    public Disciplina(String nome) {
        this.nome = nome;
        this.notas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void adicionarNota(double nota) {
        if (nota >= 0 && nota <= 10) {
            notas.add(nota);
        } else {
            System.out.println("Nota inválida ignorada: " + nota);
        }
    }

    public int contarNotasValidas() {
        return notas.size();
    }

    public double calcularMedia() {
        if (notas.isEmpty()) return 0;

        double soma = 0;
        for (double nota : notas) {
            soma += nota;
        }
        return soma / notas.size();
    }
}

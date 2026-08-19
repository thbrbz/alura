package cursos.java.praticando.herançaPolimorfismoEInterfaces.identificandoEstudante;

public class Aluno {
    String nome;
    String tipo;

    public Aluno(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    public void identificar() {
        System.out.println("Aluno: " + nome + " - Tipo: " + tipo);
    }
}

package cursos.java.praticando.encapsulamento;

public class Contato {
    private final String nome;
    private final String telefone;

    public Contato(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void exibeInformacoes(int indice){
        System.out.printf("%d. %s - %s\n", indice, nome, telefone);
    }
}

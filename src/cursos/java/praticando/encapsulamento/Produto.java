package cursos.java.praticando.encapsulamento;

public class Produto {
    private String nome;
    private double preco;

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(double valor) {
        if (valor >= 0) {
            preco = valor;
        } else {
            System.out.println("Preço inválido.");
        }
    }

    public double getPreco() {
        return this.preco;
    }

    public String getNome() {
        return this.nome;
    }
}

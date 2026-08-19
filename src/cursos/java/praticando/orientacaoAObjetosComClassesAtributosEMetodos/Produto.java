package cursos.java.praticando.orientacaoAObjetosComClassesAtributosEMetodos;

public class Produto {
    String nome;
    double preco;
    int quantidade;

    void exibirInformacoes() {
        System.out.println("Produto: " + nome);
        System.out.printf("Preço: R$ %.2f\n", preco);
        System.out.println("Quantidade em estoque: " + quantidade);
    }

    public static void main(String[] args) {
        Produto p = new Produto();
        p.nome = "Mouse Gamer";
        p.preco = 159.9;
        p.quantidade = 25;

        p.exibirInformacoes();
    }
}

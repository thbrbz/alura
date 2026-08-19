package cursos.java.praticando.orientacaoAObjetosComClassesAtributosEMetodos;

class ItemEstoque {
    String nome;
    int quantidade;

    void vender(int qtdVendida) {
        if (qtdVendida <= quantidade) {
            quantidade -= qtdVendida;
            System.out.printf("Venda realizada. Estoque restante de %s: %d%n", nome, quantidade);
        } else {
            System.out.println("Estoque insuficiente");
        }
    }

    public static void main(String[] args) {
        ItemEstoque item = new ItemEstoque();
        item.nome = "Camiseta";
        item.quantidade = 10;

        item.vender(3);
        item.vender(8);
    }
}

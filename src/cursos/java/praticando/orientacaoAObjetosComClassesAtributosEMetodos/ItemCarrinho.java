package cursos.java.praticando.orientacaoAObjetosComClassesAtributosEMetodos;

import java.util.ArrayList;
import java.util.List;

class ItemCarrinho {
    String nome;
    double preco;
    int quantidade;

    double calcularTotal() {
        return preco * quantidade;
    }

    public static void main(String[] args) {
        ItemCarrinho i1 = new ItemCarrinho();
        i1.nome = "Teclado";
        i1.preco = 120.0;
        i1.quantidade = 1;

        ItemCarrinho i2 = new ItemCarrinho();
        i2.nome = "Mouse";
        i2.preco = 60.0;
        i2.quantidade = 2;

        List<ItemCarrinho> carrinho = new ArrayList<>();
        carrinho.add(i1);
        carrinho.add(i2);

        double totalCompra = 0;
        for (ItemCarrinho item : carrinho) {
            totalCompra += item.calcularTotal();
        }

        System.out.printf("Total da compra: R$ %.2f\n", totalCompra);
    }
}

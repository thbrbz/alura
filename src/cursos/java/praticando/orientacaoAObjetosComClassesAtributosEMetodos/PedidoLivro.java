package cursos.java.praticando.orientacaoAObjetosComClassesAtributosEMetodos;

class PedidoLivro {
    String titulo;
    int diasAtraso;

    double calcularMulta() {
        return diasAtraso * 2.50;
    }

    void exibirDetalhes() {
        double multa = calcularMulta();
        System.out.printf("Livro: %s | Multa por %d dias de atraso: R$ %.2f%n",
                titulo, diasAtraso, multa);
    }

    public static void main(String[] args) {
        PedidoLivro pedido = new PedidoLivro();
        pedido.titulo = "Dom Casmurro";
        pedido.diasAtraso = 3;

        pedido.exibirDetalhes();
    }
}

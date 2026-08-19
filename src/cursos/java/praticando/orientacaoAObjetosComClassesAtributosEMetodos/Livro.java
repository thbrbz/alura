package cursos.java.praticando.orientacaoAObjetosComClassesAtributosEMetodos;

class Livro {
    String titulo;
    String autor;
    int paginas;

    void exibirResumo() {
        System.out.printf("\"%s\" de %s com %d páginas%n", titulo, autor, paginas);
    }

    public static void main(String[] args) {
        Livro l = new Livro();
        l.titulo = "O Guia do Mochileiro das Galáxias";
        l.autor = "Douglas Adams";
        l.paginas = 208;
        l.exibirResumo();
    }
}

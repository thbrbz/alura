package cursos.java.praticando.herançaPolimorfismoEInterfaces.gerenciamentoDeBiblioteca;

public class Revista extends Midia {
    private int edicao;

    public Revista(String titulo, int anoPublicacao, int edicao) {
        super(titulo, anoPublicacao);
        this.edicao = edicao;
    }

    public int getEdicao() {
        return edicao;
    }

    public void exibirInfo() {
        System.out.println("Código: " + gerarCodigo() + " | Revista: \"" + getTitulo() + "\" - Edição: " + edicao);
    }
}

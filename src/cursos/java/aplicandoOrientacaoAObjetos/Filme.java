package cursos.java.aplicandoOrientacaoAObjetos;

public class Filme extends Titulo implements Classificavel {

    private String diretor;

    public String getDiretor() {
        return this.diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    @Override
    public int getClassificacao() {
        return (int) super.pegaMedia() / 2;
    }
}

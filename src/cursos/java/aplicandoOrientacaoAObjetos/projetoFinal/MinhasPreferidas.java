package cursos.java.aplicandoOrientacaoAObjetos.projetoFinal;

public class MinhasPreferidas {
    public void inclui(Audio audio) {
        if (audio.getClassificacao() >= 8) {
            System.out.println(audio. getTitulo() + ": É braba demais!");
        } else {
            System.out.println(audio. getTitulo() + ": Precisa melhorar!");
        }
    }
}

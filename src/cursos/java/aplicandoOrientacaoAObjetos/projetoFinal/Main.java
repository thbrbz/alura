package cursos.java.aplicandoOrientacaoAObjetos.projetoFinal;

public class Main {
    public static void main(String[] args) {
        Musica musica = new Musica();
        musica.setTitulo("5 minutinhos pra tropa de BH");
        musica.setCantor("DJ de Paris");

        Podcast podcast = new Podcast();
        podcast.setTitulo("Sei lá");
        podcast.setApresentador("Caburé");

        for (int i = 0; i < 5000; i++) {
            musica.reproduz();
            podcast.reproduz();
        }

        for (int i = 0; i < 50; i++) {
            musica.curte();
            podcast.curte();
        }

        MinhasPreferidas mp = new MinhasPreferidas();
        mp.inclui(musica);
        mp.inclui(podcast);
    }
}

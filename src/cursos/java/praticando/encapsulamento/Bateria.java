package cursos.java.praticando.encapsulamento;

public class Bateria {
    private int nivel;

    public void setNivel(int nivel) {
        if (nivel >= 0 && nivel <= 100) {
            this.nivel = nivel;
        }
    }

    public int getNivel() {
        return nivel;
    }

    public String exibirStatus() {
        if (nivel <= 20) {
            return "Bateria fraca";
        } else if (nivel <= 80) {
            return "Bateria ok";
        } else {
            return "Bateria cheia";
        }
    }
}

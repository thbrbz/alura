package cursos.java.praticando.herançaPolimorfismoEInterfaces.sistemaBancario;

public abstract class OperacaoBancaria implements AcaoBancaria {
    protected double valor;

    public OperacaoBancaria( double valor) {
        this.valor = valor;
    }
}

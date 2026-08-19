package cursos.java.trabalhandoComListasEColecoesDeDados.projetoFinal;

public class CartaoCredito {
    double limite;
    double saldo;

    public CartaoCredito(double limite) {
        this.limite = limite;
        this.saldo = this.limite;
    }

    public double getSaldo() {
        return saldo;
    }
}

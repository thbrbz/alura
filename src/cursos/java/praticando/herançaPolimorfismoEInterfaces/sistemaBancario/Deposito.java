package cursos.java.praticando.herançaPolimorfismoEInterfaces.sistemaBancario;

public class Deposito extends OperacaoBancaria {
    public Deposito(double valor) {
        super(valor);
    }

    @Override
    public void executar() {
        System.out.printf("Depósito de R$%.2f realizado%n", valor);
    }
}

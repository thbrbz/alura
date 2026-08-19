package cursos.java.praticando.herançaPolimorfismoEInterfaces.sistemaBancario;

public class Saque extends OperacaoBancaria {
    public Saque(double valor) {
        super(valor);
    }

    @Override
    public void executar() {
        System.out.printf("Saque de R$%.2f realizado%n", valor);
    }
}

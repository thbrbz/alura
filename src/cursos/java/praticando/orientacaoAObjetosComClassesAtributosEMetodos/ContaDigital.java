package cursos.java.praticando.orientacaoAObjetosComClassesAtributosEMetodos;

class ContaDigital {
    double saldo;

    void zerarSaldo() {
        saldo = 0.0;
    }

    void exibirSaldo() {
        System.out.printf("Saldo atual: R$ %.2f%n", saldo);
    }

    public static void main(String[] args) {
        ContaDigital conta = new ContaDigital();
        conta.saldo = 1579.42;

        conta.exibirSaldo();
        conta.zerarSaldo();
        conta.exibirSaldo();
    }
}

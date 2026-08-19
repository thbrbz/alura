package cursos.java.trabalhandoComListasEColecoesDeDados.projetoFinal;

import cursos.java.aplicandoOrientacaoAObjetos.Produto;

import java.util.ArrayList;
import java.util.List;

public class Compra{
    List<Produto> produtos = new ArrayList<>();

    public boolean lancaCompra(CartaoCredito cartao, String nome, double valor) {
        if (valor <= cartao.saldo ) {
            produtos.add(new Produto(nome, valor));
            cartao.saldo -= valor;
            return true;
        }

        return false;
    }
}

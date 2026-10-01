package dev.thbrbz.service;

import dev.thbrbz.model.Produto;

public class TraduzProdutoService {

    private final TradutorDeepLService tradutor = new TradutorDeepLService();

    public void traduzir(Produto produto) {
        produto.setName(tradutor.traduzir(produto.getName()));
        produto.setCategory(tradutor.traduzir(produto.getCategory()));
        produto.setDescription(tradutor.traduzir(produto.getDescription()));
    }
}

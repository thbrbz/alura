package dev.thbrbz;

import com.opencsv.bean.CsvToBeanBuilder;
import dev.thbrbz.model.Produto;
import dev.thbrbz.service.TraduzProdutoService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        List<Produto> produtos = new CsvToBeanBuilder(new FileReader("src/main/resources/products.csv"))
                .withType(Produto.class).build().parse();

        for (Produto produto: produtos)
            System.out.println(produto);

        TraduzProdutoService traducaoService = new TraduzProdutoService();
        System.out.println("=".repeat(150));

        for (Produto produto: produtos){
            traducaoService.traduzir(produto);
            System.out.println(produto);
        }
    }
}
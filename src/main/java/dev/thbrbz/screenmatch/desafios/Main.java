package dev.thbrbz.screenmatch.desafios;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        //Json
        String path = "docs/tarefa.json";

        ObjectMapper mapper = new ObjectMapper();
        Tarefa tarefa = new Tarefa("Teste", false, "Eu");

        try {
            // Escrevendo arquivo:
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(path), tarefa);
            System.out.println("Arquivo com tarefa gerado com sucesso!");

            // Lendo arquivo:
            tarefa = mapper.readValue(new File(path), Tarefa.class);
            System.out.println("Conteúdo do arquivo:");
            System.out.println(tarefa);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Lambdas
        Multiplicacao mult = (a, b) -> a * b;
        System.out.println(mult.multiplicacao(5, 3));

        Primo primo = n -> {
            if (n <= 1) return false;
            for (int i = 2; i <= Math.sqrt(n); i++)
                if (n % i == 0) return false;
            return true;
        };
        System.out.println(primo.verificarPrimo(11));
        System.out.println(primo.verificarPrimo(12));

        Transformador toUpperCase = String::toUpperCase;
        System.out.println(toUpperCase.transformar("java"));

        Palindromo palindromo = str -> str.contentEquals(new StringBuilder(str).reverse());
        System.out.println(palindromo.verificarPalindromo("radar"));
        System.out.println(palindromo.verificarPalindromo("java"));

        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5); // Com lista
        numeros.replaceAll(n -> n * 3);
        System.out.println(numeros);

        List<String> nomes = Arrays.asList("Lucas", "Maria", "João", "Ana"); // Oredenando
        nomes.sort((a, b) -> a.compareTo(b));
        System.out.println(nomes);

        Divisor divisor = (a, b) -> {
            if (b == 0) throw new ArithmeticException("Divisão por zero");
            return a / b;
        };

        try {
            System.out.println(divisor.dividir(10, 2));
            System.out.println(divisor.dividir(10, 0));
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        }

        //Streams
        System.out.println("\nNúmeros pares:");
        List<Integer> numeros2 = Arrays.asList(1, 2, 3, 4, 5, 6);
        numeros2.stream().filter(n -> n % 2 == 0).forEach(System.out::println);

        System.out.println("\nConverter para maiúsculas:");
        List<String> palavras = Arrays.asList("java", "stream", "lambda");
        palavras.stream().map(String::toUpperCase).forEach(System.out::println);

        System.out.println("\nImpares multiplicados por 2:");
        List<Integer> numeros3 = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> imparesMultiplicados = numeros3.stream()
                .filter(n -> n % 2 == 1)
                .map(n -> n * 2)
                .collect(Collectors.toList());
        System.out.println(imparesMultiplicados);

        System.out.println("\nRemover duplicadas:");
        List<String> palavras2 = Arrays.asList("apple", "banana", "apple", "orange", "banana");
        palavras2.stream().distinct().forEach(System.out::println);

        System.out.println("\nNúmeros primos em ordem crescente:");
        List<List<Integer>> listaDeNumeros = Arrays.asList(
                Arrays.asList(1, 2, 3, 4),
                Arrays.asList(5, 6, 7, 8),
                Arrays.asList(9, 10, 11, 12)
        );
        listaDeNumeros.stream()
                .flatMap(List::stream)
                .filter(primo::verificarPrimo)
                .sorted()
                .forEach(System.out::println);

        System.out.println("\nMaiores de 18 em ordem alfabética:");
        List<Pessoa> pessoas = Arrays.asList(
                new Pessoa("Charlie", 19),
                new Pessoa("Alice", 22),
                new Pessoa("Bob", 17)
        );
        pessoas.stream()
                .filter(p -> p.getIdade() > 18)
                .map(Pessoa::getNome)
                .sorted()
                .forEach(System.out::println);

        System.out.println("\nEletrônicos que valem menos que mil em ordem crescente:");
        List<Produto> produtos = Arrays.asList(
                new Produto("Smartphone", 800.0, "Eletrônicos"),
                new Produto("Notebook", 1500.0, "Eletrônicos"),
                new Produto("Teclado", 200.0, "Eletrônicos"),
                new Produto("Cadeira", 300.0, "Móveis"),
                new Produto("Monitor", 900.0, "Eletrônicos"),
                new Produto("Mesa", 700.0, "Móveis")
        );
        List<Produto> produtosFiltrados = produtos.stream()
                .filter(p -> p.getCategoria().equals("Eletrônicos") && p.getPreco() < 1000)
                .sorted(Comparator.comparing(Produto::getPreco))
                .collect(Collectors.toList());
        produtosFiltrados.forEach(System.out::println);

        System.out.println("\nTop 3 mais baratos:");
        produtosFiltrados.stream().limit(3).forEach(System.out::println);

        // Collectors
        System.out.println("\nMaior número:");
        List<Integer> numeros4 = Arrays.asList(10, 20, 30, 40, 50);
        numeros4.stream().max(Integer::compareTo).ifPresent(System.out::println);

        System.out.println("\nAgrupando palavras pelo tamanho:");
        List<String> palavras3 = Arrays.asList("java", "stream", "lambda", "code");
        Map<Integer, List<String>> agrupamento = palavras3.stream().collect(Collectors.groupingBy(String::length));
        System.out.println(agrupamento);

        System.out.println("\nNomes concatenados por vírgula:");
        List<String> nomes2 = Arrays.asList("Alice", "Bob", "Charlie");
        String concatencacao = nomes2.stream().collect(Collectors.joining(", "));
        System.out.println(concatencacao);

        System.out.println("\nSoma dos quadrados dos números pares:");
        List<Integer> numeros5 = Arrays.asList(1, 2, 3, 4, 5, 6);
        Integer soma = numeros5.stream()
                .filter(n -> n % 2 == 0)
                .map(n -> n * n)
                .reduce(0, Integer::sum);
        System.out.println(soma);

        System.out.println("\nSeparando pares de impares:");
        List<Integer> numeros6 = Arrays.asList(1, 2, 3, 4, 5, 6);
        Map<Boolean, List<Integer>> separacao = numeros6.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        System.out.println("Pares: " + separacao.get(true));
        System.out.println("Impares: " + separacao.get(false));

        System.out.println("\nAgrupamento de produtos por categoria: ");
        Map<String, List<Produto>> agrupamentoProtudos = produtos.stream()
                .collect(Collectors.groupingBy(Produto::getCategoria));
        System.out.println(agrupamentoProtudos);

        System.out.println("\nContagem de produto por categoria: ");
        Map<String, Long> contagemCategoria = produtos.stream()
                .collect(Collectors.groupingBy(Produto::getCategoria, Collectors.counting()));
        System.out.println(contagemCategoria);

        System.out.println("\nProduto mais caro por categoria:");
        Map<String, Optional<Produto>> produtosMaisCaros = produtos.stream()
                .collect(Collectors.groupingBy(Produto::getCategoria, Collectors.maxBy(Comparator.comparingDouble(Produto::getPreco))));
        System.out.println(produtosMaisCaros);

        System.out.println("\nSoma dos valores por categoria:");
        Map<String, Double> somaDosValores = produtos.stream()
                .collect(Collectors.groupingBy(Produto::getCategoria, Collectors.summingDouble(Produto::getPreco)));
        System.out.println(somaDosValores);
    }
}

package cursos.java.trabalhandoComListasEColecoesDeDados;

import java.util.ArrayList;
import java.util.Collections;

public class Principal {

    public static void main(String[] args) {
        Filme favorito = new Filme("The Matrix", 1999);
        favorito.setDuracaoEmMinutos(135);
        favorito.setIncluidoNoPlano(true);

        Filme outro = new Filme("Amor de aluguel", 2003);
        outro.setDuracaoEmMinutos(110);
        outro.setIncluidoNoPlano(true);

        ArrayList<Filme> listaDeFilmes = new ArrayList<>();
        listaDeFilmes.add(favorito);
        listaDeFilmes.add(outro);

        System.out.println("Tamanho da lista: " + listaDeFilmes.size());
        System.out.println("Primeiro Filme: " + listaDeFilmes.getFirst());
        System.out.println(listaDeFilmes);

        ArrayList<Pessoa> listaDePessoas = new ArrayList<>();
        Pessoa pessoa1 = new Pessoa("Akemi", 18);
        Pessoa pessoa2 = new Pessoa("Rodrigo", 30);
        Pessoa pessoa3 = new Pessoa("Caroline", 35);

        listaDePessoas.add(pessoa1);
        listaDePessoas.add(pessoa2);
        listaDePessoas.add(pessoa3);

        System.out.println("Tamanho da lista: " + listaDePessoas.size());
        System.out.println("Primeira Pessoa: " + listaDePessoas.getFirst());

        System.out.println("Lista de Pessoas:");
        for (Pessoa pessoa : listaDePessoas)
            System.out.println(pessoa);

        Serie serie = new Serie("La Casa de Papel", 2017);

        ArrayList<Titulo> assistidos = new ArrayList<>();
        assistidos.add(favorito);
        assistidos.add(outro);
        assistidos.add(serie);

        for(Titulo item : assistidos) {
            System.out.println("Nome: " +item.getNome());

            if (item instanceof Filme filme && filme.getClassificacao() > 2)
                System.out.println("Classificação: " +filme.getClassificacao());
        }

        ArrayList<String> listaStrings = new ArrayList<>();
        listaStrings.add("Java");
        listaStrings.add("C++");
        listaStrings.add("Python");

        listaStrings.forEach(System.out::println);

        ArrayList<Titulo> lista = new ArrayList<>();
        lista.add(favorito);
        lista.add(outro);
        lista.add(serie);

        Collections.sort(lista);
        System.out.println(lista);
    }

}

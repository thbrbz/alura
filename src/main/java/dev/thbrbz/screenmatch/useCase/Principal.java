package dev.thbrbz.screenmatch.useCase;

import dev.thbrbz.screenmatch.model.Episodio;
import dev.thbrbz.screenmatch.model.Serie;
import dev.thbrbz.screenmatch.model.Temporada;
import dev.thbrbz.screenmatch.dto.SerieOmbDTO;
import dev.thbrbz.screenmatch.model.enums.Categoria;
import dev.thbrbz.screenmatch.repository.SerieRepository;
import dev.thbrbz.screenmatch.service.ConsumoAPI;
import dev.thbrbz.screenmatch.service.ConverteDados;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    static Dotenv env = Dotenv.load();

    private final Scanner leitor = new Scanner(System.in);
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConverteDados conversor = new ConverteDados();

    private final String URL = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=" + env.get("OMDB_API_KEY");

    private List<Serie> series = new ArrayList<>();
    private Optional<Serie> serieBuscada;

    private final SerieRepository serieRepository;

    public Principal(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    public void exibeMenu() {
        System.out.println("\nBem-vindos ao sistema de pesquisa de séries!");

        var opcao = -1;
        while(opcao != 0) {
            var menu = """

                    Escolha uma das opções:
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar séries
                    4 - Buscar série por título
                    5 - Buscar séries por ator
                    6 - Top 5 Séries
                    7 - Buscar séries por categoria
                    8 - Filtrar séries
                    9 - Buscar episódios por trecho
                    10 - Top 5 episódios por série
                    11 - Buscar episódios a partir de uma data

                    0 - Sair
                    """;

            System.out.println(menu);

            opcao = leitor.nextInt();
            leitor.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeries();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriesPorAtor();
                    break;
                case 6:
                    buscarTop5Series();
                    break;
                case 7:
                    buscarSeriesPorCategoria();
                    break;
                case 8:
                    filtrarSeriesPorTemporadaEAvaliacao();
                    break;
                case 9:
                    buscarEpisodioPorTrecho();
                    break;
                case 10:
                    topEpisodiosPorSerie();
                    break;
                case 11:
                    buscarEpisodiosDepoisDeUmaData();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarSerieWeb() {
        SerieOmbDTO dados = getSerieDTO();
        Serie serie = new Serie(dados);
        serieRepository.save(serie);
        System.out.println(dados);
    }

    private SerieOmbDTO getSerieDTO() {
        System.out.println("\nDigite o nome da série para busca:");
        var nomeSerie = leitor.nextLine();

        var json = consumoAPI.obterDados(URL + nomeSerie.replace(" ", "+") + API_KEY);
        return conversor.obterDados(json, SerieOmbDTO.class);
    }

    private void buscarEpisodioPorSerie(){
        listarSeries();

        System.out.println("\nEscolha uma série pelo nome:");
        var nomeSerie = leitor.nextLine();

        Optional<Serie> serie = series.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nomeSerie.toLowerCase()))
                .findFirst();

        if (serie.isEmpty()) {
            System.out.println("Série não encontrada!");
            return;
        }

        var serieEncontrada = serie.get();
        List<Temporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
            var json = consumoAPI.obterDados(URL + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
            Temporada temporada = conversor.obterDados(json, Temporada.class);
            temporadas.add(temporada);
        }

        temporadas.forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(d -> d.episodioOmbDTO()
                        .stream()
                        .map(e -> new Episodio(d.numero(), e)))
                .collect(Collectors.toList());

        serieEncontrada.setEpisodios(episodios);
        serieRepository.save(serieEncontrada);
    }

    private void listarSeries(){
        series = serieRepository.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSeriePorTitulo() {
        System.out.println("\nEscolha um série pelo nome: ");
        var nomeSerie = leitor.nextLine();

        Optional<Serie> serieBuscada = serieRepository.findByTituloContainingIgnoreCase(nomeSerie);
        System.out.println(serieBuscada.isPresent() ? "\nDados da série: " + serieBuscada.get() : "\nSérie não encontrada!");
    }

    private void buscarSeriesPorAtor() {
        System.out.println("\nQual o nome para busca?");
        var nomeAtor = leitor.nextLine();

        System.out.println("Avaliações a partir de que valor? ");
        var avaliacao = leitor.nextDouble();

        List<Serie> seriesEncontradas = serieRepository.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, avaliacao);

        System.out.println("\nSéries em que " + nomeAtor + " trabalhou: ");
        seriesEncontradas.forEach(s -> System.out.println(s.getTitulo() + " - avaliação: " + s.getAvaliacao()));
    }

    private void buscarTop5Series() {
        List<Serie> serieTop = serieRepository.findTop5ByOrderByAvaliacaoDesc();
        System.out.println("\nTop 5 séries: ");
        serieTop.forEach(s -> System.out.println(s.getTitulo() + " - avaliação: " + s.getAvaliacao()));
    }

    private void buscarSeriesPorCategoria() {
        System.out.println("\nDeseja buscar séries de que categoria/gênero? ");
        var nomeGenero = leitor.nextLine();

        Categoria categoria = Categoria.fromPortugues(nomeGenero);
        List<Serie> seriesPorCategoria = serieRepository.findByGenero(categoria);

        System.out.println("\nSéries da categoria " + nomeGenero);
        seriesPorCategoria.forEach(System.out::println);
    }

    private void filtrarSeriesPorTemporadaEAvaliacao(){
        System.out.println("\nFiltrar séries até quantas temporadas? ");
        var totalTemporadas = leitor.nextInt();
        leitor.nextLine();

        System.out.println("\nCom avaliação a partir de que valor? ");
        var avaliacao = leitor.nextDouble();
        leitor.nextLine();

        List<Serie> seriesFiltradas = serieRepository.findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(totalTemporadas, avaliacao);

        System.out.println("\n*** Séries filtradas ***");
        seriesFiltradas.forEach(s -> System.out.println(s.getTitulo() + " - avaliação: " + s.getAvaliacao()));
    }

    private void buscarEpisodioPorTrecho(){
        System.out.println("Qual o nome do episódio para busca?");
        var trechoEpisodio = leitor.nextLine();

        List<Episodio> episodiosEncontrados = serieRepository.episodiosPorTrecho(trechoEpisodio);

        episodiosEncontrados.forEach(e ->
                System.out.printf("Série: %s Temporada %s - Episódio %s - %s\n",
                        e.getSerie().getTitulo(), e.getTemporada(),
                        e.getNumeroEpisodio(), e.getTitulo()));
    }

    private void topEpisodiosPorSerie(){
        buscarSeriePorTitulo();

        if(serieBuscada.isPresent()){
            Serie serie = serieBuscada.get();
            List<Episodio> topEpisodios = serieRepository.topEpisodiosPorSerie(serie);

            topEpisodios.forEach(e ->
                    System.out.printf("Série: %s Temporada %s - Episódio %s - %s Avaliação %s\n",
                            e.getSerie().getTitulo(), e.getTemporada(),
                            e.getNumeroEpisodio(), e.getTitulo(), e.getAvaliacao()));
        }
    }
    private void buscarEpisodiosDepoisDeUmaData(){
        buscarSeriePorTitulo();

        if(serieBuscada.isPresent()){
            Serie serie = serieBuscada.get();

            System.out.println("Digite o ano limite de lançamento");
            var anoLancamento = leitor.nextInt();
            leitor.nextLine();

            List<Episodio> episodiosAno = serieRepository.episodiosPorSerieEAno(serie, anoLancamento);
            episodiosAno.forEach(System.out::println);
        }
    }
}

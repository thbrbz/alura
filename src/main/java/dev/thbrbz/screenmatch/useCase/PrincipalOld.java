package dev.thbrbz.screenmatch.useCase;

import dev.thbrbz.screenmatch.model.Episodio;
import dev.thbrbz.screenmatch.model.Temporada;
import dev.thbrbz.screenmatch.dto.EpisodioOmbDTO;
import dev.thbrbz.screenmatch.dto.SerieOmbDTO;
import dev.thbrbz.screenmatch.service.ConsumoAPI;
import dev.thbrbz.screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class PrincipalOld {

    private Scanner leitor = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";

    public void exibeMenu(){
        System.out.print("Digite o nome da série para a busca: ");
        var nomeSerie = leitor.next().trim().replace(" ", "+");

        var json = consumoAPI.obterDados(ENDERECO + nomeSerie + API_KEY);

        if (json == null) {
            System.out.println("Série não encontrada!");
            return;
        }

        SerieOmbDTO serieOmbDTO = conversor.obterDados(json, SerieOmbDTO.class);

        List<Temporada> temporadas = new ArrayList<>();

        for (int i = 1; i<= serieOmbDTO.totalTemporadas(); i++) {
            json = consumoAPI.obterDados(ENDERECO + nomeSerie +"&season=" + i + API_KEY);
            Temporada temporada = conversor.obterDados(json, Temporada.class);
            temporadas.add(temporada);
        }

        System.out.println(serieOmbDTO);
        temporadas.forEach(System.out::println);
        temporadas.forEach(t -> t.episodioOmbDTO().forEach(e -> System.out.println(e.titulo())));

        List<EpisodioOmbDTO> episodiosDTO = temporadas
                .stream()
                .flatMap(t -> t.episodioOmbDTO().stream())
                .collect(Collectors.toList());

        System.out.println("\nTop 5 episódios:");

        episodiosDTO.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(EpisodioOmbDTO::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodioOmbDTO().stream()
                        .map(d -> new Episodio(t.numero(), d)))
                .collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println("A partir de que ano você deseja ver os episódios? ");
        var ano = leitor.nextInt();
        leitor.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios.stream()
                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                        "Temporada:  " + e.getTemporada() +
                                " Episódio: " + e.getTitulo() +
                                " Data lançamento: " + e.getDataLancamento().format(formatador)
                ));

        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada, Collectors.averagingDouble(Episodio::getAvaliacao)));

        System.out.println(avaliacoesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));

        System.out.println("Média: " + est.getAverage());
        System.out.println("Melhor episódio: " + est.getMax());
        System.out.println("Pior episódio: " + est.getMin());
        System.out.println("Quantidade: " + est.getCount());
    }
}

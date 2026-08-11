package dev.thbrbz.screenmatch.service;

import dev.thbrbz.screenmatch.dto.EpisodioDTO;
import dev.thbrbz.screenmatch.dto.SerieDTO;
import dev.thbrbz.screenmatch.model.Episodio;
import dev.thbrbz.screenmatch.model.Serie;
import dev.thbrbz.screenmatch.model.enums.Categoria;
import dev.thbrbz.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {

    @Autowired
    private SerieRepository repository;

    public List<SerieDTO> obtemTodas() {
        return this.converteToDTO(repository.findAll());
    }

    public List<SerieDTO> ObtemTop5() {
        return this.converteToDTO(repository.findTop5ByOrderByAvaliacaoDesc());
    }

    public List<SerieDTO> obtemUltimosLancamentos() {
        return this.converteToDTO(repository.ultimosLancamentos());
    }

    public SerieDTO obtemPorId(Long id) {
        Optional<Serie> serie = repository.findById(id);

        if (serie.isPresent()) {
            Serie s = serie.get();
            return new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getPoster(), s.getSinopse());
        }

        return null;
    }

    public List<EpisodioDTO> obtemTodasTemporadas(Long id) {
        Optional<Serie> serie = repository.findById(id);
        return serie.map(s -> converteToEpisodioDTO(s.getEpisodios())).orElse(null);
    }

    public List<EpisodioDTO> obtemTemporadasPorNumero(Long id, Long numero) {
        return converteToEpisodioDTO(repository.episodiosPorTemporada(id, numero));
    }

    public List<SerieDTO> obtemSeriesPorCategoria(String nomeGenero) {
        Categoria categoria = Categoria.fromPortugues(nomeGenero);
        return converteToDTO(repository.findByGenero(categoria));
    }

    public List<EpisodioDTO> obtemTopEpisodios(Long id) {
        var serie = repository.findById(id);

        return serie.map(value -> converteToEpisodioDTO(repository.topEpisodiosPorSerie(value))).orElse(null);
    }

    private List<SerieDTO> converteToDTO(List<Serie> series) {
        return series.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getPoster(), s.getSinopse()))
                .collect(Collectors.toList());
    }

    private List<EpisodioDTO> converteToEpisodioDTO(List<Episodio> episodios) {
        return episodios.stream()
                .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()))
                .collect(Collectors.toList());
    }
}

package dev.thbrbz.screenmatch.controller;

import dev.thbrbz.screenmatch.dto.EpisodioDTO;
import dev.thbrbz.screenmatch.dto.SerieDTO;
import dev.thbrbz.screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService service;

    @GetMapping
    public List<SerieDTO> obtemTodas() {
        return service.obtemTodas();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obtemTop5() {
        return service.ObtemTop5();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> obtemUltimosLancamentos() {
        return service.obtemUltimosLancamentos();
    }

    @GetMapping("/{id}")
    public SerieDTO obtemPorId(@PathVariable Long id) {
        return service.obtemPorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obterTodasTemporadas(@PathVariable Long id) {
        return service.obtemTodasTemporadas(id);
    }

    @GetMapping("/{id}/temporadas/{numero}")
    public List<EpisodioDTO> obterTemporadasPorNumero(@PathVariable Long id, @PathVariable Long numero) {
        return service.obtemTemporadasPorNumero(id, numero);
    }

    @GetMapping("/{id}/temporadas/top")
    public List<EpisodioDTO> obterTopEpisodios(@PathVariable Long id) {
        return service.obtemTopEpisodios(id);
    }

    @GetMapping("/categoria/{nomeGenero}")
    public List<SerieDTO> obterSeriesPorCategoria(@PathVariable String nomeGenero) {
        return service.obtemSeriesPorCategoria(nomeGenero);
    }
}

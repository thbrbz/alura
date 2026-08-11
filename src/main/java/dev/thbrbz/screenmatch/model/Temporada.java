package dev.thbrbz.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.thbrbz.screenmatch.dto.EpisodioOmbDTO;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Temporada(@JsonAlias("Season") Integer numero,
                        @JsonAlias("Episodes") List<EpisodioOmbDTO> episodioOmbDTO) {
}

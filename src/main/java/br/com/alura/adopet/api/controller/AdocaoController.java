package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.AprovaAdocaoDto;
import br.com.alura.adopet.api.dto.ReprovaAdocaoDto;
import br.com.alura.adopet.api.dto.SolicitaAdocaoDto;
import br.com.alura.adopet.api.exception.AdocaoExeption;
import br.com.alura.adopet.api.service.AdocaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adocoes")
public class AdocaoController {

    @Autowired
    private AdocaoService adocaoService;

    @PostMapping
    @Transactional
    public ResponseEntity<String> solicitar(@RequestBody @Valid SolicitaAdocaoDto dto) {
        try {
            adocaoService.solicitar(dto);
            return ResponseEntity.ok("Adoção solicitada com sucesso!");
        }  catch (AdocaoExeption e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/aprovar")
    @Transactional
    public ResponseEntity<String> aprovar(@RequestBody @Valid AprovaAdocaoDto dto) {
        try {
            adocaoService.aprovar(dto);
            return ResponseEntity.ok("Adoção aprovada com sucesso!");
        }  catch (AdocaoExeption e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/reprovar")
    @Transactional
    public ResponseEntity<String> reprovar(@RequestBody @Valid ReprovaAdocaoDto dto) {
        try {
            adocaoService.reprovar(dto);
            return ResponseEntity.ok("Adoção reprovada com sucesso!");
        }  catch (AdocaoExeption e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

package dev.thbrbz.screenmatch.controller;

import dev.thbrbz.screenmatch.dto.FraseDTO;
import dev.thbrbz.screenmatch.service.FraseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FraseController {

    @Autowired
    private FraseService service;

    @GetMapping("series/frase")
    public FraseDTO obtemFraseAleatoria() {
        return service.obtemFraseAleatoria();
    }
}

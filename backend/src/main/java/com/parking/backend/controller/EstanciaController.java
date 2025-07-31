package com.parking.backend.controller;

import com.parking.backend.model.Estancia;
import com.parking.backend.repository.EstanciaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estancias")
public class EstanciaController {
    private final EstanciaRepository estanciaRepository;

    public EstanciaController(EstanciaRepository estanciaRepository) {
        this.estanciaRepository = estanciaRepository;
    }

    @GetMapping
    public List<Estancia> getAllEstancias() {
        return estanciaRepository.findAll();
    }

    @PostMapping
    public Estancia createEstancia(@RequestBody Estancia estancia) {
        return estanciaRepository.save(estancia);
    }
}
package com.sala.infrastructure.controller;

import com.sala.application.service.SalaService;
import com.sala.domain.model.Sala;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController 
@RequestMapping("/salas")
public class SalaController {
    @Autowired
    private SalaService service;

    @GetMapping
    public List<Sala> listar() {
        return service.listar();
    }
    
    @GetMapping("/{salaId}")
    public Sala getSala(@PathVariable Long salaId) {
        return service.getSala(salaId);
    }

    @PostMapping("/salvar")
    public Sala salvar(@RequestBody Sala sala) {
        return service.salvar(sala);  
    }
}

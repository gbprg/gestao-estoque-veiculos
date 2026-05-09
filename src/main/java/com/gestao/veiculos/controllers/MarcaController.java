package com.gestao.veiculos.controllers;

import com.gestao.veiculos.models.Marca;
import com.gestao.veiculos.services.MarcaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marcas")
@RequiredArgsConstructor
public class MarcaController {
    private final MarcaService service;

    @GetMapping
    public List<Marca> listar() {
        return service.findAll();
    }

    @PostMapping
    public Marca criar(@RequestBody Marca marca) {
        return service.save(marca);
    }
}

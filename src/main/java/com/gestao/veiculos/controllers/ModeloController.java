package com.gestao.veiculos.controllers;

import com.gestao.veiculos.models.Modelo;
import com.gestao.veiculos.services.ModeloService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modelos")
@RequiredArgsConstructor
public class ModeloController {
    private final ModeloService service;

    @GetMapping
    public List<Modelo> listar(@RequestParam(required = false) Long marcaId) {
        return service.findByMarca(marcaId);
    }

    @PostMapping
    public Modelo criar(@RequestBody Modelo modelo) {
        return service.save(modelo);
    }
}

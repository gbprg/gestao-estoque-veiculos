package com.gestao.veiculos.controllers;

import com.gestao.veiculos.models.Veiculo;
import com.gestao.veiculos.dto.VeiculoFiltro;
import com.gestao.veiculos.services.VeiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
@RequiredArgsConstructor
public class VeiculoController {
    private final VeiculoService service;

    @GetMapping
    public List<Veiculo> listar(@ModelAttribute VeiculoFiltro filtro) {
        return service.findAll(filtro);
    }

    @PostMapping
    public Veiculo criar(@RequestBody Veiculo veiculo) {
        return service.save(veiculo);
    }

    @PutMapping("/{id}")
    public Veiculo atualizar(@PathVariable Long id, @RequestBody Veiculo veiculo) {
        veiculo.setId(id);
        return service.save(veiculo);
    }

    @PatchMapping("/{id}/status")
    public Veiculo atualizarStatus(@PathVariable Long id, @RequestBody String status) {
        return service.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        service.delete(id);
    }
}

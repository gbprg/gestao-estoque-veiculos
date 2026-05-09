package com.gestao.veiculos.services;

import com.gestao.veiculos.models.Modelo;
import com.gestao.veiculos.repositories.ModeloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModeloService {
    private final ModeloRepository repository;

    public List<Modelo> findByMarca(Long marcaId) {
        if (marcaId != null) {
            return repository.findByMarcaId(marcaId);
        }
        return repository.findAll();
    }

    public Modelo save(Modelo modelo) {
        return repository.save(modelo);
    }
}

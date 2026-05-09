package com.gestao.veiculos.services;

import com.gestao.veiculos.models.Marca;
import com.gestao.veiculos.repositories.MarcaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarcaService {
    private final MarcaRepository repository;

    public List<Marca> findAll() {
        return repository.findAll();
    }

    public Marca save(Marca marca) {
        return repository.save(marca);
    }
}

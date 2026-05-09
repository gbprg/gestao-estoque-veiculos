package com.gestao.veiculos.services;

import com.gestao.veiculos.dto.VeiculoFiltro;
import com.gestao.veiculos.repositories.VeiculoRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class VeiculoServiceTest {

    private final VeiculoRepository repository = mock(VeiculoRepository.class);
    private final VeiculoService service = new VeiculoService(repository);

    @Test
    void findAllBuscaTodosOsVeiculosQuandoFiltroEstaVazio() {
        VeiculoFiltro filtro = new VeiculoFiltro();
        filtro.setStatus("   ");

        when(repository.findAll()).thenReturn(List.of());

        service.findAll(filtro);

        verify(repository).findAll();
    }
}

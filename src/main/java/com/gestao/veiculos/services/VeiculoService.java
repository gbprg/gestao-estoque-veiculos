package com.gestao.veiculos.services;

import com.gestao.veiculos.models.Veiculo;
import com.gestao.veiculos.dto.VeiculoFiltro;
import com.gestao.veiculos.repositories.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class VeiculoService {
    private final VeiculoRepository repository;

    public List<Veiculo> findAll(VeiculoFiltro filtro) {
        Specification<Veiculo> specification = criarSpecification(Optional.ofNullable(filtro).orElseGet(VeiculoFiltro::new));
        return specification == null ? repository.findAll() : repository.findAll(specification);
    }

    public List<Veiculo> findAll(Long marcaId, Long modeloId, Integer ano, BigDecimal precoMaximo, String status) {
        VeiculoFiltro filtro = new VeiculoFiltro();
        filtro.setMarcaId(marcaId);
        filtro.setModeloId(modeloId);
        filtro.setAno(ano);
        filtro.setPrecoMaximo(precoMaximo);
        filtro.setStatus(status);

        return findAll(filtro);
    }

    private Specification<Veiculo> criarSpecification(VeiculoFiltro filtro) {
        return Stream.of(
                        quandoPresente(filtro.getMarcaId(), this::porMarca),
                        quandoPresente(filtro.getModeloId(), this::porModelo),
                        quandoPresente(filtro.getAno(), this::porAno),
                        quandoPresente(filtro.getPrecoMaximo(), this::comPrecoMaximo),
                        quandoPresente(normalizarStatus(filtro.getStatus()), this::porStatus)
                )
                .filter(Objects::nonNull)
                .reduce(Specification::and)
                .orElse(null);
    }

    private <T> Specification<Veiculo> quandoPresente(T valor, Function<T, Specification<Veiculo>> specification) {
        return Optional.ofNullable(valor).map(specification).orElse(null);
    }

    private String normalizarStatus(String status) {
        return Optional.ofNullable(status)
                .map(String::trim)
                .filter(valor -> !valor.isEmpty())
                .orElse(null);
    }

    private Specification<Veiculo> porMarca(Long marcaId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("modelo").get("marca").get("id"), marcaId);
    }

    private Specification<Veiculo> porModelo(Long modeloId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("modelo").get("id"), modeloId);
    }

    private Specification<Veiculo> porAno(Integer ano) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("ano"), ano);
    }

    private Specification<Veiculo> comPrecoMaximo(BigDecimal precoMaximo) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("preco"), precoMaximo);
    }

    private Specification<Veiculo> porStatus(String status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }

    public Veiculo save(Veiculo veiculo) {
        return repository.save(veiculo);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Veiculo updateStatus(Long id, String status) {
        Veiculo veiculo = repository.findById(id).orElseThrow(() -> new RuntimeException("Veículo não encontrado"));
        veiculo.setStatus(status);
        return repository.save(veiculo);
    }
}

package com.gestao.veiculos.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class VeiculoFiltro {
    private Long marcaId;
    private Long modeloId;
    private Integer ano;
    private BigDecimal precoMaximo;
    private String status;
}

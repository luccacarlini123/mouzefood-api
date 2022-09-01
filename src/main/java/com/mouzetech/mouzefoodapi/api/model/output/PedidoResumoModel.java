package com.mouzetech.mouzefoodapi.api.model.output;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

//@JsonFilter(value = "pedidoFilter")
@Getter
@Setter
public class PedidoResumoModel {
	private String codigo;
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	private RestauranteResumoModel restaurante;
	//private UsuarioModel cliente;
	private String nomeCliente;
	private String status;
	private OffsetDateTime dataCriacao;
}
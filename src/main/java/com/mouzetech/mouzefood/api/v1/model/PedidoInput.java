package com.mouzetech.mouzefood.api.v1.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class PedidoInput {

	@Valid
	@NotNull
	private RestauranteInputId restaurante;
	
	@Valid
	@NotNull
	private FormaPagamentoInputId formaPagamento;
	
	@NotNull
	@Valid
	private EnderecoInput endereco;
	
	@Valid
	@Size(min = 1)
	@NotNull
	private List<ItemPedidoInput> itens;
}

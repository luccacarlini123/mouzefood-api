package com.mouzetech.mouzefood.api.v1.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ItemPedidoInput {
	
	@ApiModelProperty(example = "1", required = true)
	@NotNull
	private Long produtoId;
	
	@ApiModelProperty(example = "2", required = true, value = "Quantidade de produtos(deve ser maior que zero)")
	@NotNull
	@PositiveOrZero
	private Long quantidade;
	
	@ApiModelProperty(value = "Observação sobre o produto. Ex: sem cebola")
	private String observacao;
}
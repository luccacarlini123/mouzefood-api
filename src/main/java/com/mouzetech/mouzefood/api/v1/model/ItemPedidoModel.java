package com.mouzetech.mouzefood.api.v1.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ItemPedidoModel extends RepresentationModel<ItemPedidoModel> {
	
	@ApiModelProperty(value = "ID do item pedido", example = "4")
	private Long id;
	
	@ApiModelProperty(value = "Quantidade do item no pedido", example = "2")
	private Integer quantidade;
	
	@ApiModelProperty(value = "Preço unitário do item do pedido", example = "10")
	private BigDecimal precoUnitario;
	
	@ApiModelProperty(value = "Preço total dos itens do pedido", example = "20")
	private BigDecimal precoTotal;
	
	@ApiModelProperty(value = "Observação sobre o item do pedido", example = "Sem cebola")
	private String observacao;
	
	@ApiModelProperty(value = "ID do produto, que é o item do pedido", example = "2")
	private Long produtoId;
	
	@ApiModelProperty(value = "Nome do produto, que é o item do pedido", example = "Picanha com fritas")
	private String produtoNome;
}

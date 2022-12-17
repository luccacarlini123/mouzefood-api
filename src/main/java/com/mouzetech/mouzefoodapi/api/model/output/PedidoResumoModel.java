package com.mouzetech.mouzefoodapi.api.model.output;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "pedidos")
//@JsonFilter(value = "pedidoFilter")
@Getter
@Setter
public class PedidoResumoModel extends RepresentationModel<PedidoResumoModel> {
	
	@ApiModelProperty(value = "Código do pedido", example = "c6ac0f9b-0869-4f8f-8444-c2363a7afb65")
	private String codigo;
	
	@ApiModelProperty(value = "Subtotal do pedido", example = "72.00")
	private BigDecimal subtotal;
	
	@ApiModelProperty(value = "Taxa frete do pedido", example = "17.00")
	private BigDecimal taxaFrete;
	
	@ApiModelProperty(value = "Subtotal do pedido", example = "82.00")
	private BigDecimal valorTotal;
	
	@ApiModelProperty(value = "Representação resumida de um restaurante")
	private RestauranteApenasNomeModel restaurante;
	
	//private UsuarioModel cliente;
	
	@ApiModelProperty(value = "Nome do cliente que realizou o pedido", example = "Lucca Carlini")
	private String nomeCliente;
	
	@ApiModelProperty(value = "Status do pedido", example = "CONFIRMADO")
	private String status;
	
	@ApiModelProperty(value = "Data de criação do pedido", example = "2022-07-07T15:01:20Z")
	private OffsetDateTime dataCriacao;
}
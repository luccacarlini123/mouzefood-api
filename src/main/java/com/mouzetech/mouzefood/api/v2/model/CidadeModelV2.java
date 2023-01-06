package com.mouzetech.mouzefood.api.v2.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cidades")
@Getter@Setter
public class CidadeModelV2 extends RepresentationModel<CidadeModelV2> {

	@ApiModelProperty(value = "ID da cidade", example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Paracambi")
	private String nome;
	
	@ApiModelProperty(value = "ID do estado", example = "1")
	private Long estadoId;
	
	@ApiModelProperty(example = "Rio de Janeiro")
	private String estadoNome;
}
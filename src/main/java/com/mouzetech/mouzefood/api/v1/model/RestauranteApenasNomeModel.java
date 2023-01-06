package com.mouzetech.mouzefood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurantes")
@Getter@Setter
public class RestauranteApenasNomeModel extends RepresentationModel<RestauranteApenasNomeModel> {
	
	@ApiModelProperty(value = "ID do restaurante", example = "1")
	private Long id;
	
	@ApiModelProperty(value = "Nome do restaurante", example = "Quintal Di Casa")
	private String nome;
}
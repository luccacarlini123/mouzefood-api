package com.mouzetech.mouzefood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonView;
import com.mouzetech.mouzefood.api.view.RestauranteModelView;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cozinhas")
@Setter
@Getter
public class CozinhaModel extends RepresentationModel<CozinhaModel> {
	
	@ApiModelProperty(value = "ID da cozinha", example = "2")
	@JsonView(RestauranteModelView.Resumo.class)
	private Long id;
	
	@ApiModelProperty(value = "Nome da cozinha", example = "Brasileira")
	@JsonView(RestauranteModelView.Resumo.class)
	private String nome;
}
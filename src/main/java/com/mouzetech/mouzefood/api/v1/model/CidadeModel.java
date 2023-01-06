package com.mouzetech.mouzefood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cidades")
@Getter@Setter
public class CidadeModel extends RepresentationModel<CidadeModel> {

	@ApiModelProperty(value = "ID da cidade", example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Paracambi")
	private String nome;
	
	private EstadoModel estado;
}
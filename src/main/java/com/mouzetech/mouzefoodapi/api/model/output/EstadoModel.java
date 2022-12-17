package com.mouzetech.mouzefoodapi.api.model.output;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "estados")
@Getter@Setter
public class EstadoModel extends RepresentationModel<EstadoModel> {
	
	@ApiModelProperty(value = "ID do estado", example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Rio de Janeiro")
	private String nome;
}
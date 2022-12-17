package com.mouzetech.mouzefoodapi.api.model.output;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "usuarios")
@Getter@Setter
public class UsuarioModel extends RepresentationModel<UsuarioModel> {

	@ApiModelProperty(value = "ID do cliente", example = "2")
	private Long id;
	
	@ApiModelProperty(value = "Nome do cliente", example = "Chris Cole")
	private String nome;
	
	@ApiModelProperty(value = "Email do cliente", example = "chris.cole@outlook.com")
	private String email;	
}
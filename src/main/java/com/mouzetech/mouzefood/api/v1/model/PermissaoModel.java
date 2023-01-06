package com.mouzetech.mouzefood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "permissoes")
@Getter
@Setter
public class PermissaoModel extends RepresentationModel<PermissaoModel> {
	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Excluir Restaurante")
	private String nome;
	
	@ApiModelProperty(example = "Essa permissão é para excluir um Restaurante, apenas admins podem fazer uso dela")
	private String descricao;
}
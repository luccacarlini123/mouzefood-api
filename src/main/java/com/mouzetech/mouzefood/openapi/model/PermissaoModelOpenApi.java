package com.mouzetech.mouzefood.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.mouzetech.mouzefood.api.v1.model.PermissaoModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("PermissoesModel")
public class PermissaoModelOpenApi {

	private PermissaoEmbeddedModel _embedded;
	private Links _links;
	
	@Data
	@ApiModel("PermissoesEmbeddedModel")
	public class PermissaoEmbeddedModel {
		private List<PermissaoModel> permissoes;
	}	
}
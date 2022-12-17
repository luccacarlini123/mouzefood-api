package com.mouzetech.mouzefoodapi.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.mouzetech.mouzefoodapi.api.model.output.PermissaoModel;

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
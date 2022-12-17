package com.mouzetech.mouzefoodapi.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.mouzetech.mouzefoodapi.api.model.output.GrupoModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("GruposModel")
@Data
public class GrupoModelOpenApi {

	private GrupoEmbeddedModel _embedded;
	private Links _links;
	
	@ApiModel("GruposEmbeddedModel")
	@Data
	public class GrupoEmbeddedModel{
		private List<GrupoModel> grupos;
	}	
}
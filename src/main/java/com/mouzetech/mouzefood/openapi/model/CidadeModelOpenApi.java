package com.mouzetech.mouzefood.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.mouzetech.mouzefood.api.v1.model.CidadeModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("CidadesModel")
@Data
public class CidadeModelOpenApi {

	private CidadeEmbbededOpenApi _embedded;
	private Links _links;
	
	@Data
	@ApiModel("CidadesEmbbededModel")
	public class CidadeEmbbededOpenApi {
		
		private List<CidadeModel> cidades;
	
	}	
}
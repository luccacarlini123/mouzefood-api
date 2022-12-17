package com.mouzetech.mouzefoodapi.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.mouzetech.mouzefoodapi.api.model.output.RestauranteResumoModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("RestaurantesModel")
@Data
public class RestauranteModelOpenApi {

	private CidadeEmbbededOpenApi _embedded;
	private Links _links;
	
	@Data
	@ApiModel("RestaurantesEmbbededModel")
	public class CidadeEmbbededOpenApi {
		
		private List<RestauranteResumoModel> restaurantes;
	
	}	
}
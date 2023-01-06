package com.mouzetech.mouzefood.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.mouzetech.mouzefood.api.v1.model.CozinhaModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CozinhasModel")
@Getter
@Setter
public class CozinhaModelOpenApi {	
	
	private CozinhasEmbbededOpenApi _embedded;
	private Links _links;
	private PageModelOpenApi page;
	
	@Data
	@ApiModel("CozinhasEmbbededModel")
	public class CozinhasEmbbededOpenApi {
		private List<CozinhaModel> cozinhas;
	}	
}
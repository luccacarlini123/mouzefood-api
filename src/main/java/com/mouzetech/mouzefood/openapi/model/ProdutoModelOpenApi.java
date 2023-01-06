package com.mouzetech.mouzefood.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.mouzetech.mouzefood.api.v1.model.ProdutoModel;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("ProdutosModel")
@Data
public class ProdutoModelOpenApi {

	private ProdutosEmbeddedModel _embedded;
	private Links _links;
	
	@ApiModel("ProdutosEmbeddedModel")
	@Data
	public class ProdutosEmbeddedModel {
		private List<ProdutoModel> produtos;
	}
	
}

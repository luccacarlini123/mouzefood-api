package com.mouzetech.mouzefood.openapi.controller;

import javax.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.mouzetech.mouzefood.api.v1.model.ProdutoInput;
import com.mouzetech.mouzefood.api.v1.model.ProdutoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Produtos")
public interface ProdutoControllerOpenApi {

	@ApiOperation(value = "Busca produtos por ID de um restaurante")
	public CollectionModel<ProdutoModel> buscarProdutosDoRestaurante(
			@ApiParam(value = "ID do restaurante", required = true) 
			Long restauranteId, 

			@ApiParam(value = "Boolean para buscar produtos inativos", type = "query")
			Boolean buscarInativos);
	
	@ApiOperation(value = "Busca um produto específico por ID do restaurante e ID do produto")
	public ResponseEntity<ProdutoModel> buscarProdutoDoRestaurante(
			@ApiParam(value = "ID do restaurante", required = true)  
			Long restauranteId, 

			@ApiParam(value = "ID do produto", required = true) 
			Long produtoId);
	
	@ApiOperation(value = "Adiciona um produto a um restaurante")
	public ProdutoModel adicionarProdutoEmRestaurante(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput);
	
	@ApiOperation(value = "Atualiza o produto de um restaurante")
	public ProdutoModel atualizarProduto(
			@ApiParam(value = "ID do restaurante", required = true) 
			Long restauranteId, 
			
			@ApiParam(value = "ID do produto", required = true)  
			Long produtoId, 

			@ApiParam(name = "corpo", value = "Representação de um novo produto a ser cadastrado")
			ProdutoInput produtoInput);
	
}

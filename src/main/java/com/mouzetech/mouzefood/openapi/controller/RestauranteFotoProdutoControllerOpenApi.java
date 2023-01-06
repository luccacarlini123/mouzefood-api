package com.mouzetech.mouzefood.openapi.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import com.mouzetech.mouzefood.api.v1.model.FotoProdutoInput;
import com.mouzetech.mouzefood.api.v1.model.FotoProdutoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Produtos")
public interface RestauranteFotoProdutoControllerOpenApi {
	
	@ApiOperation (value = "Busca a foto do produto de um restaurante", produces = "image/jpeg, image/png, application/json")
	public ResponseEntity<?> buscarFotoDoProduto(
			@ApiParam(value = "ID do restaurante", required = true) 
			Long restauranteId, 
			
			@ApiParam(value = "ID do produto", required = true) 
			Long produtoId, 
			
			@ApiParam(hidden = true, required = false)
			String acceptHeader) throws HttpMediaTypeNotAcceptableException;
	
	@ApiOperation(value = "Exclui foto de um produto")
	public void excluirFotoDoProduto(
			@ApiParam(value = "ID do restaurante", required = true) 
			Long restauranteId, 
			
			@ApiParam(value = "ID do produto", required = true) 
			Long produtoId);
	
	@ApiOperation(value = "Salva foto relacionada a um produto de um restaurante")
	public FotoProdutoModel atualizarFoto(
			@ApiParam(value = "ID do restaurante", required = true)
			Long restauranteId, 
			
			@ApiParam(value = "ID do produto", required = true) 
			Long produtoId,
			
			FotoProdutoInput fotoProduto,
			
			@ApiParam(value = "Arquivo da foto do produto (máximo 8000KB, apenas JPG e PNG)", required = true)
			MultipartFile arquivo) throws IOException;
	
}

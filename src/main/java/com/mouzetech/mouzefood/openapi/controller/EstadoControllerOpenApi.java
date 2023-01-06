package com.mouzetech.mouzefood.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.mouzetech.mouzefood.api.v1.model.EstadoInput;
import com.mouzetech.mouzefood.api.v1.model.EstadoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {
	
	@ApiOperation(value = "Lista todos os estados")
	CollectionModel<EstadoModel> listar();
	
	@ApiOperation(value = "Busca um estado por ID")
	ResponseEntity<EstadoModel> buscarPorId(
			@ApiParam(value = "ID do estado") 
			Long estadoId);
	
	@ApiOperation(value = "Salva um estado")
	ResponseEntity<EstadoModel> cadastrar(
			@ApiParam(name = "corpo") 
			EstadoInput estadoInput);
	
	@ApiOperation(value = "Atualiza um estado")
	ResponseEntity<EstadoModel> atualizar(
			@ApiParam(value = "ID do estado") 
			Long estadoId, 

			@ApiParam(name = "corpo")
			EstadoInput estadoInput);
	
	@ApiOperation(value = "Exclui um estado")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Estado excluído com sucesso")
	})
	ResponseEntity<?> excluir(
			@ApiParam(value = "ID do estado")
			Long estadoId);
}
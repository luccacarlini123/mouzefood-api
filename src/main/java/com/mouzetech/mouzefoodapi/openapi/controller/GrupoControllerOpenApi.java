package com.mouzetech.mouzefoodapi.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.mouzetech.mouzefoodapi.api.model.input.GrupoInput;
import com.mouzetech.mouzefoodapi.api.model.output.GrupoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

	@ApiOperation(value = "Busca todos os grupos")
	CollectionModel<GrupoModel> buscarTodos();
	
	@ApiOperation(value = "Busca um grupo por ID")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Grupo não encontrado"),
		@ApiResponse(code = 400, message = "ID do grupo inválido")
	})
	ResponseEntity<GrupoModel> buscarPorId(
			@ApiParam(value = "ID de um grupo")
			Long grupoId);
	
	@ApiResponses({
		@ApiResponse(code = 201, message = "Grupo criado com sucesso")
	})
	@ApiOperation(value = "Salva um grupo")
	ResponseEntity<GrupoModel> salvar(
			@ApiParam(name = "corpo", value = "Representação de um novo grupo")
			GrupoInput grupoInput);
	
	@ApiOperation(value = "Atualiza um grupo")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Grupo não encontrado"),
		@ApiResponse(code = 400, message = "ID do grupo ou corpo da requisição inválidos")
	})
	void atualizar(
			@ApiParam(value = "ID de um grupo")
			Long grupoId, 
			
			@ApiParam(name = "corpo", value = "Representação de um grupo a ser atualizado")
			GrupoInput grupoInput);
	
	
	@ApiResponses({
		@ApiResponse(code = 404, message = "Grupo não encontrado"),
		@ApiResponse(code = 400, message = "ID do grupo inválido"),
		@ApiResponse(code = 204, message = "Grupo excluído com sucesso")
	})
	@ApiOperation(value = "Exclusão de um grupo")
	void excluir(
			@ApiParam(value = "ID de um grupo")
			Long grupoId);
}
package com.mouzetech.mouzefoodapi.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.mouzetech.mouzefoodapi.api.model.input.CidadeInput;
import com.mouzetech.mouzefoodapi.api.model.output.CidadeModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

	@ApiOperation("Lista as cidades")
	CollectionModel<CidadeModel> listar();
	
	@ApiOperation("Busca uma cidade por id")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Cidade não encontrada"),
		@ApiResponse(code = 400, message = "ID da cidade inválido")
	})
	ResponseEntity<CidadeModel> buscarPorId(
			@ApiParam(value = "ID de uma cidade")
			Long cidadeId);
	
	@ApiOperation("Lista cidades pelo id do estado")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Estado não encontrado"),
		@ApiResponse(code = 400, message = "ID do estado inválido")
	})
	CollectionModel<CidadeModel> buscarPorEstado(
			@ApiParam(value = "ID de um estado")
			Long estadoId);
	
	@ApiOperation("Salva cidade")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Cidade criada com sucesso")
	})
	ResponseEntity<CidadeModel> salvar(
			@ApiParam(name = "corpo", value = "Representação de uma nova cidade")
			CidadeInput cidadeInput);
	
	@ApiOperation("Exclui cidade")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Cidade não encontrado"),
		@ApiResponse(code = 400, message = "Requisição inválida")
	})
	ResponseEntity<?> excluir(
			@ApiParam("ID de uma cidade")
			Long cidadeId);
	
	@ApiOperation("Atualiza cidade")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Cidade não encontrado"),
		@ApiResponse(code = 400, message = "Requisição inválida")
	})
	ResponseEntity<CidadeModel> atualizar(
			@ApiParam("ID de uma cidade")
			Long cidadeId, 
			
			@ApiParam(name = "corpo", value = "Representação de uma cidade com novos dados")
			CidadeInput cidadeInput);

	
}

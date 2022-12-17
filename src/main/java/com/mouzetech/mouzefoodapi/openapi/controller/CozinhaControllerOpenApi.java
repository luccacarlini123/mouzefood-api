package com.mouzetech.mouzefoodapi.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

import com.mouzetech.mouzefoodapi.api.model.input.CozinhaInput;
import com.mouzetech.mouzefoodapi.api.model.output.CozinhaModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

	@ApiOperation(value = "Busca todas as cozinhas de forma paginada")
	PagedModel<CozinhaModel> listar(Pageable pageable);
	
	@ApiOperation(value = "Busca cozinha por ID")
	ResponseEntity<CozinhaModel> buscarPorId(
			@ApiParam(value = "ID de uma cozinha", example = "1")
			Long cozinhaId);
	
	@ApiOperation(value = "Salva uma cozinha")
	CozinhaModel salvar(
			@ApiParam(name = "corpo", value = "Representação de uma nova cozinha")
			CozinhaInput cozinhaInput);
	
	@ApiOperation(value = "Atualiza uma cozinha")
	ResponseEntity<CozinhaModel> editar(
			@ApiParam(value = "ID de uma cozinha", example = "1")
			Long cozinhaId, 
			
			@ApiParam(name = "corpo", value = "Representação de uma cozinha a ser atualizada")
			CozinhaInput cozinhaInput);
	
	@ApiResponses({
		@ApiResponse(code = 204, message = "Cozinha excluída com sucesso")
	})
	@ApiOperation(value = "Exclui uma cozinha")
	void remover(
			@ApiParam(value = "ID de uma cozinha", example = "1")
			Long cozinhaId);
	
}

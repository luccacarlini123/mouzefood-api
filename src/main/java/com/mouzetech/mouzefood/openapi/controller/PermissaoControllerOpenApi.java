package com.mouzetech.mouzefood.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.mouzetech.mouzefood.api.v1.model.PermissaoInput;
import com.mouzetech.mouzefood.api.v1.model.PermissaoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Permissões")
public interface PermissaoControllerOpenApi {

	@ApiOperation(value = "Busca todas as permissões")
	public CollectionModel<PermissaoModel> buscarTodas();
	
	@ApiOperation(value = "Busca permissão por ID")
	public ResponseEntity<PermissaoModel> buscarPorId(
			@ApiParam(value = "ID da permissão", required = true) 
			Long permissaoId);
	
	@ApiOperation(value = "Salva uma permissão")
	public PermissaoModel cadastrar(
			@ApiParam(value = "Nova permissão a ser salva", required = true, name = "corpo")
			PermissaoInput permissaoInput);
	
	@ApiOperation(value = "Atualiza uma permissão")
	public void atualizar(
			@ApiParam(value = "Nova permissão a ser atualizada", required = true, name = "corpo")
			PermissaoInput permissaoInput, 
			
			@ApiParam(value = "ID da permissão", required = true)
			Long permissaoId);	
}
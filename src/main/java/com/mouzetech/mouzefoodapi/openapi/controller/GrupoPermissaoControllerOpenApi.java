package com.mouzetech.mouzefoodapi.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.mouzetech.mouzefoodapi.api.model.output.PermissaoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Grupos")
public interface GrupoPermissaoControllerOpenApi {

	@ApiOperation(value = "Associa permissão a um grupo")
	public ResponseEntity<Void> associarPermissao(
			@ApiParam(value = "ID do grupo", required = true) 
			Long grupoId, 
			
			@ApiParam(value = "ID da permissao", required = true) 
			Long permissaoId);
	
	@ApiOperation(value = "Desassocia permissão de um grupo")
	public ResponseEntity<Void> desassociarPermissao(
			@ApiParam(value = "ID do grupo", required = true) 
			Long grupoId, 
			
			@ApiParam(value = "ID da permissao", required = true) 
			Long permissaoId);
	
	@ApiOperation(value = "Busca todas as permissões de um grupo")
	public CollectionModel<PermissaoModel> buscarPermissoesDoGrupo(
			@ApiParam(value = "ID do grupo", required = true) 
			Long grupoId);
	
}

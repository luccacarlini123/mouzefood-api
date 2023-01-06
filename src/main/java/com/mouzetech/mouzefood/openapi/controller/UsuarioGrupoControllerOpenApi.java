package com.mouzetech.mouzefood.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.mouzetech.mouzefood.api.v1.model.GrupoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Usuários")
public interface UsuarioGrupoControllerOpenApi {

	@ApiOperation(value = "Busca os grupos que um usário possui")
	public CollectionModel<GrupoModel> buscarGruposDeUmUsuario(
			@ApiParam(value = "ID do usuário")
			Long usuarioId);
	
	@ApiOperation(value = "Associa um grupo a um usuário")
	public ResponseEntity<Void> associarGrupo(
			@ApiParam(value = "ID do usuário", required = true)
			Long usuarioId, 
			
			@ApiParam(value = "ID do grupo", required = true)
			Long grupoId);
	
	@ApiOperation(value = "Desassocia um grupo a um usuário")
	public ResponseEntity<Void> desassociarGrupo(
			@ApiParam(value = "ID do usuário", required = true)
			Long usuarioId, 
			
			@ApiParam(value = "ID do grupo", required = true)
			Long grupoId);
}

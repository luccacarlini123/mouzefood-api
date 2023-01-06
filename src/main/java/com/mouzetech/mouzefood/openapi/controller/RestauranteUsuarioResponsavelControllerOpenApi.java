package com.mouzetech.mouzefood.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.mouzetech.mouzefood.api.v1.model.UsuarioModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Restaurantes")
public interface RestauranteUsuarioResponsavelControllerOpenApi {

	@ApiOperation(value = "Busca usuários responsáveis por ID do restaurante")
	public CollectionModel<UsuarioModel> buscarUsuariosResponsaveisDoRestaurante(@ApiParam("ID de um restaurante") Long restauranteId);

	@ApiOperation(value = "Adiciona um usuário responsável a um restaurante")
	public ResponseEntity<Void> adicionarResponsavelAoRestaurante(
			@ApiParam("ID de um restaurante") 
			Long restauranteId, 
			
			@ApiParam("ID de um usuário") 
			Long usuarioId);
	
	@ApiOperation(value = "Remove um usuário responsável de um restaurante")
	public ResponseEntity<Void> removerResponsavelAoRestaurante(
			@ApiParam("ID de um restaurante") 
			Long restauranteId, 

			@ApiParam("ID de um usuário")
			Long usuarioId);
	
}

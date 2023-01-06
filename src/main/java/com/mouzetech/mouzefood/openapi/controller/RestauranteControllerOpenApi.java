package com.mouzetech.mouzefood.openapi.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.mouzetech.mouzefood.api.v1.model.RestauranteApenasNomeModel;
import com.mouzetech.mouzefood.api.v1.model.RestauranteInput;
import com.mouzetech.mouzefood.api.v1.model.RestauranteModel;
import com.mouzetech.mouzefood.api.v1.model.RestauranteResumoModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

	@ApiImplicitParams({ @ApiImplicitParam(allowableValues = "apenas-nome", value = "Parâmetro para projetar campos, "
			+ "passando o valor \"apenas-nome\" como parâmetro irá filtrar a resposta para "
			+ "trazer apenas o nome e o ID na projeção de restaurantes", name = "projecao", type = "string", paramType = "query") })
	@ApiOperation(value = "Lista todos os restaurantes")
	CollectionModel<RestauranteResumoModel> listar();

	@ApiIgnore
	@ApiOperation(value = "Lista todos os restaurantes", hidden = true)
	CollectionModel<RestauranteApenasNomeModel> listarApenasNome();

	@ApiOperation(value = "Retorna o primeiro restaurante encontrado")
	ResponseEntity<RestauranteModel> findFirst();

	@ApiOperation(value = "Busca restaurantes pelo ID da cozinha")
	CollectionModel<RestauranteModel> buscarRestaurantes(
			@ApiParam(value = "ID da cozinha", required = true) Long cozinhaId);

	@ApiOperation(value = "Busca restaurantes por ID")
	ResponseEntity<RestauranteModel> buscarPorId(
			@ApiParam(value = "ID do restaurante", required = true) Long restauranteId);

	@ApiOperation(value = "Salva um restaurante")
	ResponseEntity<RestauranteModel> salvar(
			@ApiParam(value = "Respresentação de um restaurante a ser salvo", name = "corpo") RestauranteInput restauranteInput);

	@ApiOperation(value = "Atualiza um restaurante")
	ResponseEntity<RestauranteModel> atualizar(@ApiParam(value = "ID do restaurante") Long restauranteId,

			@ApiParam(value = "Representação de um restaurante a ser atualizado", name = "corpo") RestauranteInput restauranteInput);

	@ApiOperation(value = "Ativa um restaurante")
	ResponseEntity<Void> ativar(@ApiParam(value = "ID do restaurante", required = true) Long restauranteId);

	@ApiOperation(value = "Desativa um restaurante")
	ResponseEntity<Void> desativar(@ApiParam(value = "ID do restaurante", required = true) Long restauranteId);

	@ApiOperation(value = "Ativa restaurantes em massa")
	void ativarRestaurantes(
			@ApiParam(value = "Lista de ID de restaurantes", required = true) List<Long> restaurantesId);

	@ApiOperation(value = "Desativa restaurantes em massa")
	void desativarRestaurantes(
			@ApiParam(value = "Lista de ID de restaurantes", required = true) List<Long> restaurantesId);

	@ApiOperation(value = "Abre um restaurantes")
	ResponseEntity<Void> abrir(@ApiParam(value = "ID do restaurante", required = true) Long restauranteId);

	@ApiOperation(value = "Fecha um restaurantes")
	ResponseEntity<Void> fechar(@ApiParam(value = "ID do restaurante", required = true) Long restauranteId);
}
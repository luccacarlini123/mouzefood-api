package com.mouzetech.mouzefoodapi.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mouzetech.mouzefoodapi.api.model.assembler.UsuarioModelAssembler;
import com.mouzetech.mouzefoodapi.api.model.output.UsuarioModel;
import com.mouzetech.mouzefoodapi.domain.service.RestauranteUsuarioResponsavelService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
@AllArgsConstructor
public class RestauranteUsuarioResponsavelController {

	private RestauranteUsuarioResponsavelService restauranteUsuarioResponsavelService;
	private UsuarioModelAssembler usuarioModelAssembler;
	
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public List<UsuarioModel> buscarUsuariosResponsaveisDoRestaurante(@PathVariable Long restauranteId){
		return usuarioModelAssembler.toCollectionModel(restauranteUsuarioResponsavelService.buscarUsuariosResponsaveisDoRestaurante(restauranteId));
	}

	@PutMapping("/{usuarioId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void adicionarResponsavelAoRestaurante(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteUsuarioResponsavelService.associarResponsavelAoRestaurante(restauranteId, usuarioId);
	}
	
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removerResponsavelAoRestaurante(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteUsuarioResponsavelService.desassociarResponsavelAoRestaurante(restauranteId, usuarioId);
	}
}
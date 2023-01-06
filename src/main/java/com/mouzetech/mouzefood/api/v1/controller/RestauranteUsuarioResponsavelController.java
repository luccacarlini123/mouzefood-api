package com.mouzetech.mouzefood.api.v1.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mouzetech.mouzefood.api.v1.ApiLinkBuilder;
import com.mouzetech.mouzefood.api.v1.assembler.UsuarioModelAssembler;
import com.mouzetech.mouzefood.api.v1.model.UsuarioModel;
import com.mouzetech.mouzefood.domain.service.RestauranteUsuarioResponsavelService;
import com.mouzetech.mouzefood.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
@AllArgsConstructor
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

	private RestauranteUsuarioResponsavelService restauranteUsuarioResponsavelService;
	private UsuarioModelAssembler usuarioModelAssembler;
	private ApiLinkBuilder apiLinkBuilder;
	
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public CollectionModel<UsuarioModel> buscarUsuariosResponsaveisDoRestaurante(@PathVariable Long restauranteId){
		CollectionModel<UsuarioModel> collectionUsuarioModel = 
				usuarioModelAssembler.toCollectionModel(restauranteUsuarioResponsavelService
						.buscarUsuariosResponsaveisDoRestaurante(restauranteId));
		
		collectionUsuarioModel
			.removeLinks()
			.add(apiLinkBuilder.linkToUsuariosRestaurante(restauranteId))
			.add(apiLinkBuilder.linkToAdicionarUsuarioResponsavelNoRestaurante(restauranteId, "adicionar"));
		
		collectionUsuarioModel.getContent().forEach(usuario -> {
			usuario.add(apiLinkBuilder.linkToRemoverUsuarioResponsavelNoRestaurante(restauranteId, usuario.getId(), "remover"));
		});
		
		return collectionUsuarioModel;
	}

	@PutMapping("/{usuarioId}")
	public ResponseEntity<Void> adicionarResponsavelAoRestaurante(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteUsuarioResponsavelService.associarResponsavelAoRestaurante(restauranteId, usuarioId);
	
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{usuarioId}")
	public ResponseEntity<Void> removerResponsavelAoRestaurante(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteUsuarioResponsavelService.desassociarResponsavelAoRestaurante(restauranteId, usuarioId);
		
		return ResponseEntity.noContent().build();
	}
}
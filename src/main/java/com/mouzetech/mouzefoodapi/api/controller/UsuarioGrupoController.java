package com.mouzetech.mouzefoodapi.api.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mouzetech.mouzefoodapi.api.ApiLinkBuilder;
import com.mouzetech.mouzefoodapi.api.model.assembler.GrupoModelAssembler;
import com.mouzetech.mouzefoodapi.api.model.output.GrupoModel;
import com.mouzetech.mouzefoodapi.domain.service.GrupoUsuarioService;
import com.mouzetech.mouzefoodapi.openapi.controller.UsuarioGrupoControllerOpenApi;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/usuarios/{usuarioId}/grupos")
@AllArgsConstructor
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {
	
	private GrupoUsuarioService grupoUsuarioService;
	private GrupoModelAssembler grupoModelAssembler;
	private ApiLinkBuilder apiLinkBuilder;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<GrupoModel> buscarGruposDeUmUsuario(@PathVariable Long usuarioId){
		CollectionModel<GrupoModel> collectionModelGrupo = grupoModelAssembler.toCollectionModel(
						grupoUsuarioService.buscarGruposDeUmUsuario(usuarioId));
		
		collectionModelGrupo.getContent().forEach(grupo -> {
			grupo.add(apiLinkBuilder.linkToDesassociarUsuarioGrupo(grupo.getId(), usuarioId, "desassociar"));
		});
		
		collectionModelGrupo.add(apiLinkBuilder.linkToAssociarUsuarioGrupo(usuarioId, "associar"));
		
		return collectionModelGrupo;
	}
	
	@PutMapping("/{grupoId}")
	public ResponseEntity<Void> associarGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		grupoUsuarioService.associarGrupo(usuarioId, grupoId);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{grupoId}")
	public ResponseEntity<Void> desassociarGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		grupoUsuarioService.desassociarGrupo(usuarioId, grupoId);
		return ResponseEntity.noContent().build();
	}
}
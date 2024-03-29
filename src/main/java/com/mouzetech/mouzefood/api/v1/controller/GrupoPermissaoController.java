package com.mouzetech.mouzefood.api.v1.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mouzetech.mouzefood.api.v1.ApiLinkBuilder;
import com.mouzetech.mouzefood.api.v1.assembler.PermissaoModelAssembler;
import com.mouzetech.mouzefood.api.v1.model.PermissaoModel;
import com.mouzetech.mouzefood.domain.service.GrupoPermissaoService;
import com.mouzetech.mouzefood.openapi.controller.GrupoPermissaoControllerOpenApi;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
@AllArgsConstructor
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

	private PermissaoModelAssembler permissaoModelAssembler;
	private GrupoPermissaoService grupoPermissaoService;
	private ApiLinkBuilder apiLinkBuilder;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<PermissaoModel> buscarPermissoesDoGrupo(@PathVariable Long grupoId){
		CollectionModel<PermissaoModel> collectionModelPermissao = 
				permissaoModelAssembler.toCollectionModel(grupoPermissaoService.buscarPermissoesDeUmGrupo(grupoId));
		
		collectionModelPermissao.getContent().forEach(permissao -> {
			permissao.add(apiLinkBuilder.linkToDesassociarPermissaoGrupo(grupoId, permissao.getId(), "desassociar"));
		});
		
		collectionModelPermissao.add(apiLinkBuilder.linkToAssociarPermissaoGrupo(grupoId, "associar"));
		
		return collectionModelPermissao;
	}
	
	@PutMapping("/{permissaoId}")
	public ResponseEntity<Void> associarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		grupoPermissaoService.associarPermissaoAoGrupo(grupoId, permissaoId);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{permissaoId}")
	public ResponseEntity<Void> desassociarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		grupoPermissaoService.desassociarPermissaoAoGrupo(grupoId, permissaoId);
		return ResponseEntity.noContent().build();
	}
}
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

import com.mouzetech.mouzefoodapi.api.model.assembler.PermissaoModelAssembler;
import com.mouzetech.mouzefoodapi.api.model.output.PermissaoModel;
import com.mouzetech.mouzefoodapi.domain.service.GrupoPermissaoService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
@AllArgsConstructor
public class GrupoPermissaoController{

	private PermissaoModelAssembler permissaoModelAssembler;
	private GrupoPermissaoService grupoPermissaoService;
	
	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		grupoPermissaoService.associarPermissaoAoGrupo(grupoId, permissaoId);
	}
	
	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		grupoPermissaoService.desassociarPermissaoAoGrupo(grupoId, permissaoId);
	}
	
	@GetMapping
	public List<PermissaoModel> buscarPermissoesDoGrupo(@PathVariable Long grupoId){
		return permissaoModelAssembler.toCollectionModel(grupoPermissaoService.buscarPermissoesDeUmGrupo(grupoId));
	}
	
}

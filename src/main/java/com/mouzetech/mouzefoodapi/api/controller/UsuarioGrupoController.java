package com.mouzetech.mouzefoodapi.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mouzetech.mouzefoodapi.api.model.assembler.GrupoModelAssembler;
import com.mouzetech.mouzefoodapi.api.model.output.GrupoModel;
import com.mouzetech.mouzefoodapi.domain.service.GrupoUsuarioService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/usuarios/{usuarioId}/grupos")
@AllArgsConstructor
public class UsuarioGrupoController {
	
	private GrupoUsuarioService grupoUsuarioService;
	private GrupoModelAssembler grupoModelAssembler;
	
	@GetMapping
	public ResponseEntity<List<GrupoModel>> buscarGruposDeUmUsuario(@PathVariable Long usuarioId){
		return ResponseEntity.ok(
				grupoModelAssembler.toCollectionModel(
						grupoUsuarioService.buscarGruposDeUmUsuario(usuarioId)));
	}
	
	@PutMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associarGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		grupoUsuarioService.associarGrupo(usuarioId, grupoId);
	}
	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociarGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		grupoUsuarioService.desassociarGrupo(usuarioId, grupoId);
	}
}
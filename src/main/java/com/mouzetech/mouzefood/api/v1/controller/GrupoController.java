package com.mouzetech.mouzefood.api.v1.controller;

import javax.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mouzetech.mouzefood.api.v1.assembler.GrupoInputDisassembler;
import com.mouzetech.mouzefood.api.v1.assembler.GrupoModelAssembler;
import com.mouzetech.mouzefood.api.v1.model.GrupoInput;
import com.mouzetech.mouzefood.api.v1.model.GrupoModel;
import com.mouzetech.mouzefood.domain.model.Grupo;
import com.mouzetech.mouzefood.domain.repository.GrupoRepository;
import com.mouzetech.mouzefood.domain.service.CadastroGrupoService;
import com.mouzetech.mouzefood.openapi.controller.GrupoControllerOpenApi;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/grupos")
@AllArgsConstructor
public class GrupoController implements GrupoControllerOpenApi {

	private CadastroGrupoService cadastroGrupoService;
	private GrupoRepository grupoRepository;
	private GrupoInputDisassembler grupoInputDisassembler;
	private GrupoModelAssembler grupoModelAssembler;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<GrupoModel> buscarTodos(){
		return grupoModelAssembler.toCollectionModel(grupoRepository.findAll());
	}
	
	@GetMapping(value = "/{grupoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GrupoModel> buscarPorId(@PathVariable Long grupoId){
		return ResponseEntity.ok(grupoModelAssembler.toModel(cadastroGrupoService.buscarPorId(grupoId)));
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GrupoModel> salvar(@RequestBody @Valid GrupoInput grupoInput){
		Grupo grupo = grupoInputDisassembler.toObject(grupoInput);
		grupo = grupoRepository.save(grupo);
		return ResponseEntity.status(HttpStatus.CREATED).body(grupoModelAssembler.toModel(grupo));
	}
	
	@PutMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable Long grupoId, @RequestBody @Valid GrupoInput grupoInput) {
		Grupo grupo = cadastroGrupoService.buscarPorId(grupoId);
		grupoInputDisassembler.copyToDomainObject(grupoInput, grupo);
		grupoRepository.save(grupo);
	}
	
	
	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long grupoId) {
		cadastroGrupoService.excluir(grupoId);
	}
}

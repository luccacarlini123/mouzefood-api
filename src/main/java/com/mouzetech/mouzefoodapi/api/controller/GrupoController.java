package com.mouzetech.mouzefoodapi.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
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

import com.mouzetech.mouzefoodapi.api.model.assembler.GrupoModelAssembler;
import com.mouzetech.mouzefoodapi.api.model.disassembler.GrupoInputDisassembler;
import com.mouzetech.mouzefoodapi.api.model.input.GrupoInput;
import com.mouzetech.mouzefoodapi.api.model.output.GrupoModel;
import com.mouzetech.mouzefoodapi.domain.model.Grupo;
import com.mouzetech.mouzefoodapi.domain.repository.GrupoRepository;
import com.mouzetech.mouzefoodapi.domain.service.CadastroGrupoService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/grupos")
@AllArgsConstructor
public class GrupoController {

	private CadastroGrupoService cadastroGrupoService;
	private GrupoRepository grupoRepository;
	private GrupoInputDisassembler grupoInputDisassembler;
	private GrupoModelAssembler grupoModelAssembler;
	
	@GetMapping
	public ResponseEntity<List<GrupoModel>> buscarTodos(){
		return ResponseEntity.ok(grupoModelAssembler.toCollectionModel(grupoRepository.findAll()));
	}
	
	@GetMapping("/{grupoId}")
	public ResponseEntity<GrupoModel> buscarPorId(@PathVariable Long grupoId){
		return ResponseEntity.ok(grupoModelAssembler.toModel(cadastroGrupoService.buscarPorId(grupoId)));
	}
	
	@PostMapping
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

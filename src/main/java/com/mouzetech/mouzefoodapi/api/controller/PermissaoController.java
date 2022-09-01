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

import com.mouzetech.mouzefoodapi.api.model.assembler.PermissaoModelAssembler;
import com.mouzetech.mouzefoodapi.api.model.disassembler.PermissaoInputDisassembler;
import com.mouzetech.mouzefoodapi.api.model.input.PermissaoInput;
import com.mouzetech.mouzefoodapi.api.model.output.PermissaoModel;
import com.mouzetech.mouzefoodapi.domain.model.Permissao;
import com.mouzetech.mouzefoodapi.domain.repository.PermissaoRepository;
import com.mouzetech.mouzefoodapi.domain.service.CadastroPermissaoService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/permissoes")
@AllArgsConstructor
public class PermissaoController {

	private CadastroPermissaoService cadastroPermissaoService;
	private PermissaoInputDisassembler permissaoInputDisassembler;
	private PermissaoModelAssembler permissaoModelAssembler;
	private PermissaoRepository permissaoRepository;
	
	@GetMapping
	public ResponseEntity<List<PermissaoModel>> buscarTodas(){
		return ResponseEntity.ok(
				permissaoModelAssembler.toCollectionModel(
						permissaoRepository.findAll()));
	}
	
	@GetMapping("/{permissaoId}")
	public ResponseEntity<PermissaoModel> buscarPorId(@PathVariable Long permissaoId){
		Permissao permissao = cadastroPermissaoService.buscarPorId(permissaoId);
		return ResponseEntity.ok(permissaoModelAssembler.toModel(permissao));
				
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public PermissaoModel cadastrar(@RequestBody @Valid PermissaoInput permissaoInput) {
		Permissao permissao = permissaoInputDisassembler.toObject(permissaoInput);
		return permissaoModelAssembler.toModel(permissaoRepository.save(permissao));
	}
	
	@PutMapping("/{permissaoId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void atualizar(@RequestBody @Valid PermissaoInput permissaoInput, @PathVariable Long permissaoId) {
		Permissao permissao = cadastroPermissaoService.buscarPorId(permissaoId);
		permissaoInputDisassembler.copyToObject(permissaoInput, permissao);
		permissaoRepository.save(permissao);
	}
	
	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long permissaoId) {
		cadastroPermissaoService.excluir(permissaoId);
	}
	
}

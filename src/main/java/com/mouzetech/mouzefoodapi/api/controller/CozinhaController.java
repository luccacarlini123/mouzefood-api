package com.mouzetech.mouzefoodapi.api.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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

import com.mouzetech.mouzefoodapi.api.ResourceUriHelper;
import com.mouzetech.mouzefoodapi.api.model.assembler.CozinhaModelAssembler;
import com.mouzetech.mouzefoodapi.api.model.disassembler.CozinhaInputDisassembler;
import com.mouzetech.mouzefoodapi.api.model.input.CozinhaInput;
import com.mouzetech.mouzefoodapi.api.model.output.CozinhaModel;
import com.mouzetech.mouzefoodapi.domain.model.Cozinha;
import com.mouzetech.mouzefoodapi.domain.repository.CozinhaRepository;
import com.mouzetech.mouzefoodapi.domain.service.CadastroCozinhaService;
import com.mouzetech.mouzefoodapi.openapi.controller.CozinhaControllerOpenApi;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/cozinhas")
public class CozinhaController implements CozinhaControllerOpenApi {

	private CozinhaRepository cozinhaRepository;
	
	private CadastroCozinhaService cadastroCozinhaService;
	
	private CozinhaModelAssembler cozinhaModelAssembler;
	
	private CozinhaInputDisassembler cozinhaInputDisassembler;
	
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssemblerCozinha;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public PagedModel<CozinhaModel> listar(@PageableDefault(size = 1) Pageable pageable){
		Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);
		
		PagedModel<CozinhaModel> pagedCozinhaModel = pagedResourcesAssemblerCozinha.toModel(cozinhasPage, cozinhaModelAssembler);
		
		return pagedCozinhaModel;
	}
	
	@GetMapping(value = "/{cozinhaId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CozinhaModel> buscarPorId(@PathVariable Long cozinhaId) {
		return ResponseEntity.ok(cozinhaModelAssembler.toModel(cadastroCozinhaService.buscarPorId(cozinhaId)));
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaModel salvar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinha = cadastroCozinhaService.salvar(cozinhaInputDisassembler.toDomainObject(cozinhaInput));
		
		ResourceUriHelper.addCreatedUriInResponseHeader(cozinha.getId());
		
		return cozinhaModelAssembler.toModel(cozinha);
	}
	
	@PutMapping(value = "/{cozinhaId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CozinhaModel> editar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinhaAtual = cadastroCozinhaService.buscarPorId(cozinhaId);
		
		cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);

		return ResponseEntity.ok(cozinhaModelAssembler.toModel(cozinhaRepository.save(cozinhaAtual)));
		
	}
	
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId){
		cadastroCozinhaService.remover(cozinhaId);
	}
	
}

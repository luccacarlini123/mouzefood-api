package com.mouzetech.mouzefood.api.v1.controller;

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

import com.mouzetech.mouzefood.api.ResourceUriHelper;
import com.mouzetech.mouzefood.api.v1.assembler.CozinhaInputDisassembler;
import com.mouzetech.mouzefood.api.v1.assembler.CozinhaModelAssembler;
import com.mouzetech.mouzefood.api.v1.model.CozinhaInput;
import com.mouzetech.mouzefood.api.v1.model.CozinhaModel;
import com.mouzetech.mouzefood.domain.model.Cozinha;
import com.mouzetech.mouzefood.domain.repository.CozinhaRepository;
import com.mouzetech.mouzefood.domain.service.CadastroCozinhaService;
import com.mouzetech.mouzefood.openapi.controller.CozinhaControllerOpenApi;

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
		
		PagedModel<CozinhaModel> pagedCozinhaModel = 
				pagedResourcesAssemblerCozinha.toModel(cozinhasPage, cozinhaModelAssembler);
		
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

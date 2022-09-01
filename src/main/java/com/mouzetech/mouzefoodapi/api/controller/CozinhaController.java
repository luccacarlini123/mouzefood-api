package com.mouzetech.mouzefoodapi.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import com.mouzetech.mouzefoodapi.api.model.CozinhaXmlWrapper;
import com.mouzetech.mouzefoodapi.api.model.assembler.CozinhaModelAssembler;
import com.mouzetech.mouzefoodapi.api.model.disassembler.CozinhaInputDisassembler;
import com.mouzetech.mouzefoodapi.api.model.input.CozinhaInput;
import com.mouzetech.mouzefoodapi.api.model.output.CozinhaModel;
import com.mouzetech.mouzefoodapi.domain.model.Cozinha;
import com.mouzetech.mouzefoodapi.domain.repository.CozinhaRepository;
import com.mouzetech.mouzefoodapi.domain.service.CadastroCozinhaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	private CozinhaRepository cozinhaRepository;
	
	private CadastroCozinhaService cadastroCozinhaService;
	
	private CozinhaModelAssembler cozinhaModelAssembler;
	
	private CozinhaInputDisassembler cozinhaInputDisassembler;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<CozinhaModel> listar(@PageableDefault(size = 1) Pageable pageable){
		Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);
		
		List<CozinhaModel> cozinhasModel = cozinhaModelAssembler.toCollectionModel(cozinhasPage.getContent());
		
		Page<CozinhaModel> pageCozinhaModel = new PageImpl<>(cozinhasModel, pageable, cozinhasPage.getTotalElements());
		
		return pageCozinhaModel;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public CozinhaXmlWrapper listarXML(){
		return new CozinhaXmlWrapper(cozinhaRepository.findAll());
	}
	
	@GetMapping("/{cozinhaId}")
	public ResponseEntity<CozinhaModel> buscarPorId(@PathVariable Long cozinhaId) {
		return ResponseEntity.ok(cozinhaModelAssembler.toModel(cadastroCozinhaService.buscarPorId(cozinhaId)));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha salvar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		return cadastroCozinhaService.salvar(cozinhaInputDisassembler.toDomainObject(cozinhaInput));
	}
	
	@PutMapping("/{cozinhaId}")
	public ResponseEntity<CozinhaModel> editar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinhaAtual = cadastroCozinhaService.buscarPorId(cozinhaId);
		
		cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);

		return ResponseEntity.ok(cozinhaModelAssembler.toModel(cozinhaRepository.save(cozinhaAtual)));
		
	}
	
	@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<?> remover(@PathVariable Long cozinhaId){
		cadastroCozinhaService.remover(cozinhaId);
		return ResponseEntity.noContent().build();
	}
	
}

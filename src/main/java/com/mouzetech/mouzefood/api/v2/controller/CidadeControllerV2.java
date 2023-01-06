package com.mouzetech.mouzefood.api.v2.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mouzetech.mouzefood.api.v1.model.CidadeInput;
import com.mouzetech.mouzefood.api.v2.assembler.CidadeInputDisassemblerV2;
import com.mouzetech.mouzefood.api.v2.assembler.CidadeModelAssemblerV2;
import com.mouzetech.mouzefood.api.v2.model.CidadeModelV2;
import com.mouzetech.mouzefood.core.web.MouzeFoodMediaTypes;
import com.mouzetech.mouzefood.domain.model.Cidade;
import com.mouzetech.mouzefood.domain.repository.CidadeRepository;
import com.mouzetech.mouzefood.domain.service.CadastroCidadeService;


@RestController
@RequestMapping(value =  "/cidades", produces = MouzeFoodMediaTypes.V2_APPLICATION_JSON_VALUE)
public class CidadeControllerV2 {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@Autowired
	private CidadeModelAssemblerV2 cidadeModelAssembler;
	
	@Autowired
	private CidadeInputDisassemblerV2 cidadeInputDisassembler;
	
	@GetMapping(produces = MouzeFoodMediaTypes.V2_APPLICATION_JSON_VALUE)
	public CollectionModel<CidadeModelV2> listar(){
		return cidadeModelAssembler.toCollectionModel(cidadeRepository.findAll());
	}
	
	@GetMapping(value = "/{cidadeId}", produces = MouzeFoodMediaTypes.V2_APPLICATION_JSON_VALUE)
	public ResponseEntity<CidadeModelV2> buscarPorId(@PathVariable Long cidadeId){
		return ResponseEntity.ok(
				cidadeModelAssembler.toModel(
				cadastroCidadeService.buscarPorId(cidadeId)));
	}
	
	@GetMapping(value = "/estado/{estadoId}", produces = MouzeFoodMediaTypes.V2_APPLICATION_JSON_VALUE)
	public CollectionModel<CidadeModelV2> buscarPorEstado(@PathVariable Long estadoId){
		return cidadeModelAssembler.toCollectionModel(cadastroCidadeService.buscarPorEstadoId(estadoId));
	}
	
	@PostMapping(produces = MouzeFoodMediaTypes.V2_APPLICATION_JSON_VALUE)
	public ResponseEntity<CidadeModelV2> salvar(@RequestBody @Valid CidadeInput cidadeInput) {
		Cidade cidade = cadastroCidadeService.salvar(cidadeInputDisassembler.toDomainObject(cidadeInput));
		return ResponseEntity.status(HttpStatus.CREATED).body(cidadeModelAssembler.toModel(cidade));
	}
	
	@DeleteMapping(path = "/{cidadeId}", produces = {})
	public ResponseEntity<Void> excluir(@PathVariable Long cidadeId){
		cadastroCidadeService.excluir(cidadeId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@PutMapping(value = "/{cidadeId}", produces = MouzeFoodMediaTypes.V2_APPLICATION_JSON_VALUE)
	public ResponseEntity<CidadeModelV2> atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput){
		return ResponseEntity.ok(cidadeModelAssembler.toModel(cadastroCidadeService.atualizar(cidadeInput, cidadeId)));
	}
}
package com.mouzetech.mouzefood.api.v1.controller;

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

import com.mouzetech.mouzefood.api.v1.assembler.CidadeInputDisassembler;
import com.mouzetech.mouzefood.api.v1.assembler.CidadeModelAssembler;
import com.mouzetech.mouzefood.api.v1.model.CidadeInput;
import com.mouzetech.mouzefood.api.v1.model.CidadeModel;
import com.mouzetech.mouzefood.core.web.MouzeFoodMediaTypes;
import com.mouzetech.mouzefood.domain.model.Cidade;
import com.mouzetech.mouzefood.domain.repository.CidadeRepository;
import com.mouzetech.mouzefood.domain.service.CadastroCidadeService;
import com.mouzetech.mouzefood.openapi.controller.CidadeControllerOpenApi;


@RestController
@RequestMapping(value =  "/cidades")
public class CidadeController implements CidadeControllerOpenApi {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;
	
	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;
	
	@GetMapping(produces = MouzeFoodMediaTypes.V1_APPLICATION_JSON_VALUE)
	public CollectionModel<CidadeModel> listar(){
		return cidadeModelAssembler.toCollectionModel(cidadeRepository.findAll());
	}
	
	@GetMapping(value = "/{cidadeId}", produces = MouzeFoodMediaTypes.V1_APPLICATION_JSON_VALUE)
	public ResponseEntity<CidadeModel> buscarPorId(@PathVariable Long cidadeId){
		return ResponseEntity.ok(
				cidadeModelAssembler.toModel(
				cadastroCidadeService.buscarPorId(cidadeId)));
	}
	
	@GetMapping(value = "/estado/{estadoId}", produces = MouzeFoodMediaTypes.V1_APPLICATION_JSON_VALUE)
	public CollectionModel<CidadeModel> buscarPorEstado(@PathVariable Long estadoId){
		return cidadeModelAssembler.toCollectionModel(cadastroCidadeService.buscarPorEstadoId(estadoId));
	}
	
	@PostMapping(produces = MouzeFoodMediaTypes.V1_APPLICATION_JSON_VALUE)
	public ResponseEntity<CidadeModel> salvar(@RequestBody @Valid CidadeInput cidadeInput) {
		Cidade cidade = cadastroCidadeService.salvar(cidadeInputDisassembler.toDomainObject(cidadeInput));
		return ResponseEntity.status(HttpStatus.CREATED).body(cidadeModelAssembler.toModel(cidade));
	}
	
	@DeleteMapping("/{cidadeId}")
	public ResponseEntity<?> excluir(@PathVariable Long cidadeId){
		cadastroCidadeService.excluir(cidadeId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@PutMapping(value = "/{cidadeId}", produces = MouzeFoodMediaTypes.V1_APPLICATION_JSON_VALUE)
	public ResponseEntity<CidadeModel> atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput){
		return ResponseEntity.ok(cidadeModelAssembler.toModel(cadastroCidadeService.atualizar(cidadeInput, cidadeId)));
	}
}
package com.mouzetech.mouzefoodapi.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.mouzetech.mouzefoodapi.api.model.assembler.CidadeModelAssembler;
import com.mouzetech.mouzefoodapi.api.model.disassembler.CidadeInputDisassembler;
import com.mouzetech.mouzefoodapi.api.model.input.CidadeInput;
import com.mouzetech.mouzefoodapi.api.model.output.CidadeModel;
import com.mouzetech.mouzefoodapi.domain.model.Cidade;
import com.mouzetech.mouzefoodapi.domain.repository.CidadeRepository;
import com.mouzetech.mouzefoodapi.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;
	
	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;
	
	@GetMapping
	public List<CidadeModel> listar(){
		return cidadeModelAssembler.toCollectionModel(cidadeRepository.findAll());
	}
	
	@GetMapping("/{cidadeId}")
	public ResponseEntity<CidadeModel> buscarPorId(@PathVariable Long cidadeId){
		return ResponseEntity.ok(cidadeModelAssembler.toModel(cadastroCidadeService.buscarPorId(cidadeId)));
	}
	
	@GetMapping("/estado/{estadoId}")
	public ResponseEntity<List<CidadeModel>> buscarPorEstado(@PathVariable Long estadoId){
		return ResponseEntity.ok(cidadeModelAssembler.toCollectionModel(cadastroCidadeService.buscarPorEstadoId(estadoId)));
	}
	
	@PostMapping
	public ResponseEntity<CidadeModel> salvar(@RequestBody @Valid CidadeInput cidadeInput) {
		Cidade cidade = cadastroCidadeService.salvar(cidadeInputDisassembler.toDomainObject(cidadeInput));
		return ResponseEntity.status(HttpStatus.CREATED).body(cidadeModelAssembler.toModel(cidade));
	}
	
	@DeleteMapping("/{cidadeId}")
	public ResponseEntity<?> excluir(@PathVariable Long cidadeId){
		cadastroCidadeService.excluir(cidadeId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@PutMapping("/{cidadeId}")
	public ResponseEntity<CidadeModel> atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput){
		return ResponseEntity.ok(cidadeModelAssembler.toModel(cadastroCidadeService.atualizar(cidadeInput, cidadeId)));
	}
}
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

import com.mouzetech.mouzefood.api.v1.assembler.EstadoInputDisassembler;
import com.mouzetech.mouzefood.api.v1.assembler.EstadoModelAssembler;
import com.mouzetech.mouzefood.api.v1.model.EstadoInput;
import com.mouzetech.mouzefood.api.v1.model.EstadoModel;
import com.mouzetech.mouzefood.domain.model.Estado;
import com.mouzetech.mouzefood.domain.repository.EstadoRepository;
import com.mouzetech.mouzefood.domain.service.CadastroEstadoService;
import com.mouzetech.mouzefood.openapi.controller.EstadoControllerOpenApi;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/estados")
public class EstadoController implements EstadoControllerOpenApi {

	private EstadoRepository estadoRepository;
	
	private CadastroEstadoService cadastroEstadoService;
	
	private EstadoModelAssembler estadoModelAssembler;
	
	private EstadoInputDisassembler estadoInputDisassembler;
	
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
	public CollectionModel<EstadoModel> listar(){
		return estadoModelAssembler.toCollectionModel(estadoRepository.findAll());
	}
	
	@GetMapping(value = "/{estadoId}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<EstadoModel> buscarPorId(@PathVariable Long estadoId) {
		return ResponseEntity.ok(estadoModelAssembler.toModel(cadastroEstadoService.buscarPorId(estadoId)));
	}
	
	@PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<EstadoModel> cadastrar(@RequestBody @Valid EstadoInput estadoInput) {
		return ResponseEntity.ok(estadoModelAssembler.toModel(cadastroEstadoService.salvar(estadoInputDisassembler.toObjectDomain(estadoInput))));
	}
	
	@PutMapping(path = "/{estadoId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<EstadoModel> atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {
			Estado estado = cadastroEstadoService.atualizar(estadoInput, estadoId);
			return ResponseEntity.ok(estadoModelAssembler.toModel(estado));
	}
	
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@DeleteMapping("/{estadoId}")
	public ResponseEntity<?> excluir(@PathVariable Long estadoId) {
		cadastroEstadoService.excluir(estadoId);
		return ResponseEntity.noContent().build();
	}
	
}

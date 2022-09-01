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

import com.mouzetech.mouzefoodapi.api.model.assembler.FormaPagamentoModelAssembler;
import com.mouzetech.mouzefoodapi.api.model.disassembler.FormaPagamentoInputDisassembler;
import com.mouzetech.mouzefoodapi.api.model.input.FormaPagamentoInput;
import com.mouzetech.mouzefoodapi.api.model.output.FormaPagamentoModel;
import com.mouzetech.mouzefoodapi.domain.model.FormaPagamento;
import com.mouzetech.mouzefoodapi.domain.repository.FormaPagamentoRepository;
import com.mouzetech.mouzefoodapi.domain.service.CadastroFormaPagamentoService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/formas-pagamento")
@AllArgsConstructor
public class FormaPagamentoController {

	private FormaPagamentoRepository formaPagamentoRepository;
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;
	private FormaPagamentoModelAssembler formaPagamentoModelAssembler;
	private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;
	
	@GetMapping
	public ResponseEntity<List<FormaPagamentoModel>> buscar(){
		return ResponseEntity.ok(formaPagamentoModelAssembler.toCollectionModel(formaPagamentoRepository.findAll()));
	}
	
	@GetMapping("/{formaPagamentoId}")
	public ResponseEntity<FormaPagamentoModel> buscarPorId(@PathVariable Long formaPagamentoId){
		return ResponseEntity.ok(formaPagamentoModelAssembler.toModel(cadastroFormaPagamentoService.buscarPorId(formaPagamentoId)));
	}
	
	@PostMapping
	public ResponseEntity<FormaPagamentoModel> salvar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput){
		FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toObjectDomain(formaPagamentoInput);
		return ResponseEntity.status(HttpStatus.CREATED).body((formaPagamentoModelAssembler.toModel(formaPagamentoRepository.save(formaPagamento))));
	}
	
	@PutMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable Long formaPagamentoId, @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamentoAtual = cadastroFormaPagamentoService.buscarPorId(formaPagamentoId);
		
		formaPagamentoInputDisassembler.copyToObjectDomain(formaPagamentoInput, formaPagamentoAtual);
		
		formaPagamentoRepository.save(formaPagamentoAtual);
	}
	
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long formaPagamentoId) {
		cadastroFormaPagamentoService.excluir(formaPagamentoId);
	}
}

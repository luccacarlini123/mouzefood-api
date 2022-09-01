package com.mouzetech.mouzefoodapi.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mouzetech.mouzefoodapi.api.model.assembler.FormaPagamentoModelAssembler;
import com.mouzetech.mouzefoodapi.api.model.output.FormaPagamentoModel;
import com.mouzetech.mouzefoodapi.domain.model.Restaurante;
import com.mouzetech.mouzefoodapi.domain.service.CadastroRestauranteService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
@AllArgsConstructor
public class RestauranteFormasPagamentoController {

	private CadastroRestauranteService cadastroRestauranteService;
	private FormaPagamentoModelAssembler formaPagamentoModelAssembler;
	
	@GetMapping
	public ResponseEntity<List<FormaPagamentoModel>> buscarFormasPagamento(@PathVariable Long restauranteId){
		Restaurante restaurante = cadastroRestauranteService.buscarPorId(restauranteId);
		
		return ResponseEntity.ok(formaPagamentoModelAssembler.toCollectionModel(restaurante.getFormasPagamento()));
	}
	
	@PutMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		cadastroRestauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);
	}
	
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		cadastroRestauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);
	}
}

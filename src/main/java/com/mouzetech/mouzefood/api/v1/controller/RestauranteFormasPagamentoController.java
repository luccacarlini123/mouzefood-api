package com.mouzetech.mouzefood.api.v1.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mouzetech.mouzefood.api.v1.ApiLinkBuilder;
import com.mouzetech.mouzefood.api.v1.assembler.FormaPagamentoModelAssembler;
import com.mouzetech.mouzefood.api.v1.model.FormaPagamentoModel;
import com.mouzetech.mouzefood.domain.model.Restaurante;
import com.mouzetech.mouzefood.domain.service.CadastroRestauranteService;
import com.mouzetech.mouzefood.openapi.controller.RestauranteFormasPagamentoOpenApi;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
@AllArgsConstructor
public class RestauranteFormasPagamentoController implements RestauranteFormasPagamentoOpenApi {

	private CadastroRestauranteService cadastroRestauranteService;
	private FormaPagamentoModelAssembler formaPagamentoModelAssembler;
	private ApiLinkBuilder apiLinkBuilder;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CollectionModel<FormaPagamentoModel>> buscarFormasPagamento(@PathVariable Long restauranteId){
		Restaurante restaurante = cadastroRestauranteService.buscarPorId(restauranteId);
		
		CollectionModel<FormaPagamentoModel> collectionModelFormaPagamento =
				formaPagamentoModelAssembler.toCollectionModel(restaurante.getFormasPagamento());
		
		collectionModelFormaPagamento.removeLinks()
			.add(apiLinkBuilder.linkToFormasPagamentoRestaurante(restauranteId, IanaLinkRelations.SELF.toString()))
			.add(apiLinkBuilder.linkToAssociarFormaPagamentoResataurante(restauranteId, "associar"));
		
		collectionModelFormaPagamento.getContent().forEach(formaPagamento -> {
			formaPagamento.add(apiLinkBuilder.linkToDesassociarFormaPagamentoResataurante(restaurante.getId(), formaPagamento.getId(), "desassociar"));
		});
		
		return ResponseEntity.ok(collectionModelFormaPagamento);
	}
	
	@PutMapping("/{formaPagamentoId}")
	public ResponseEntity<Void> associarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		cadastroRestauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{formaPagamentoId}")
	public ResponseEntity<Void> desassociarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		cadastroRestauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);
		
		return ResponseEntity.noContent().build();
	}
}

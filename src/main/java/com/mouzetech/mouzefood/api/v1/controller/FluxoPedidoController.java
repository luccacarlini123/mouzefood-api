package com.mouzetech.mouzefood.api.v1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mouzetech.mouzefood.domain.service.FluxoPedidoService;
import com.mouzetech.mouzefood.openapi.controller.FluxoPedidoControllerOpenApi;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/pedidos/{pedidoCodigo}")
@AllArgsConstructor
public class FluxoPedidoController implements FluxoPedidoControllerOpenApi {

	private FluxoPedidoService fluxoPedidoService;
	
	@PutMapping("/confirmacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> confirmarPedido(@PathVariable String pedidoCodigo) {
		fluxoPedidoService.confirmarPedido(pedidoCodigo);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/cancelamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> cancelarPedido(@PathVariable String pedidoCodigo) {
		fluxoPedidoService.cancelarPedido(pedidoCodigo);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/entrega")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> entregarPedido(@PathVariable String pedidoCodigo) {
		fluxoPedidoService.entregarPedido(pedidoCodigo);
		
		return ResponseEntity.noContent().build();
	}	
}
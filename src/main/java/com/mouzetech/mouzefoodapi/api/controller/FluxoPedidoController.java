package com.mouzetech.mouzefoodapi.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mouzetech.mouzefoodapi.domain.service.FluxoPedidoService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/pedidos/{pedidoCodigo}")
@AllArgsConstructor
public class FluxoPedidoController {

	private FluxoPedidoService fluxoPedidoService;
	
	@PutMapping("/confirmacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void confirmarPedido(@PathVariable String pedidoCodigo) {
		fluxoPedidoService.confirmarPedido(pedidoCodigo);
	}
	
	@PutMapping("/cancelamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancelarPedido(@PathVariable String pedidoCodigo) {
		fluxoPedidoService.cancelarPedido(pedidoCodigo);
	}
	
	@PutMapping("/entrega")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void entregarPedido(@PathVariable String pedidoCodigo) {
		fluxoPedidoService.entregarPedido(pedidoCodigo);
	}
	
}

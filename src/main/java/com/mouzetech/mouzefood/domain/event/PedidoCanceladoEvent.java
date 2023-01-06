package com.mouzetech.mouzefood.domain.event;

import com.mouzetech.mouzefood.domain.model.Pedido;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PedidoCanceladoEvent {

	private Pedido pedido;
	
}
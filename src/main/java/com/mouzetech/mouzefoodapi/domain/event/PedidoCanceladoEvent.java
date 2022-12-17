package com.mouzetech.mouzefoodapi.domain.event;

import com.mouzetech.mouzefoodapi.domain.model.Pedido;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PedidoCanceladoEvent {

	private Pedido pedido;
	
}
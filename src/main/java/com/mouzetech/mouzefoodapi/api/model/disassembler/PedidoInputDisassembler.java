package com.mouzetech.mouzefoodapi.api.model.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.mouzetech.mouzefoodapi.api.model.input.PedidoInput;
import com.mouzetech.mouzefoodapi.domain.model.Pedido;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PedidoInputDisassembler {

	private ModelMapper modelMapper;
	
	public Pedido toObject(PedidoInput pedidoInput) {
		return modelMapper.map(pedidoInput, Pedido.class);
	}
	
	public void copyToDomainObject(Pedido pedido, PedidoInput pedidoInput) {
		modelMapper.map(pedidoInput, pedido);
	}
}

package com.mouzetech.mouzefood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.mouzetech.mouzefood.api.v1.model.PedidoInput;
import com.mouzetech.mouzefood.domain.model.Pedido;

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

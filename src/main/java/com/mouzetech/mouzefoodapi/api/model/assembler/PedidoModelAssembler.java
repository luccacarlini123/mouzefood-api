package com.mouzetech.mouzefoodapi.api.model.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mouzetech.mouzefoodapi.api.model.output.PedidoModel;
import com.mouzetech.mouzefoodapi.api.model.output.PedidoResumoModel;
import com.mouzetech.mouzefoodapi.domain.model.Pedido;

@Component
public class PedidoModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public PedidoModel toModel(Pedido pedido) {
		return modelMapper.map(pedido, PedidoModel.class);
	}
	
	public List<PedidoModel> toCollectionModel(List<Pedido> pedidos){
		return pedidos.stream()
				.map(pedido -> toModel(pedido))
				.collect(Collectors.toList());
	}
	
	public PedidoResumoModel toResumoModel(Pedido pedido) {
		return modelMapper.map(pedido, PedidoResumoModel.class);
	}
	
	public List<PedidoResumoModel> toCollectionResumoModel(List<Pedido> pedidos){
		return pedidos.stream()
				.map(pedido -> toResumoModel(pedido))
				.collect(Collectors.toList());
	}
}
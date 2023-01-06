package com.mouzetech.mouzefood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.mouzetech.mouzefood.api.v1.ApiLinkBuilder;
import com.mouzetech.mouzefood.api.v1.controller.PedidoController;
import com.mouzetech.mouzefood.api.v1.model.PedidoModel;
import com.mouzetech.mouzefood.domain.model.Pedido;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

	public PedidoModelAssembler() {
		super(PedidoController.class, PedidoModel.class);
	}

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ApiLinkBuilder apiLinkBuilder;
	
	public PedidoModel toModel(Pedido pedido) {
		PedidoModel pedidoModel = createModelWithId(pedido.getId(), pedido);
	    modelMapper.map(pedido, pedidoModel);
	    
	    pedidoModel.add(apiLinkBuilder.linkToPedidos("pedidos"));
	    
	    if(pedido.podeSerCancelado()) {
	    	pedidoModel.add(apiLinkBuilder.linkToCancelarPedido(pedidoModel.getCodigo(), "cancelar"));
	    }
	    	
	    if(pedido.podeSerEntregue()) {
	    	pedidoModel.add(apiLinkBuilder.linkToEntregarPedido(pedidoModel.getCodigo(), "entregar"));
	    }
	    
	    if(pedido.podeSerConfirmado()) {
	    	pedidoModel.add(apiLinkBuilder.linkToConfirmarPedido(pedidoModel.getCodigo(), "confirmar"));
	    }
	    	
		pedidoModel.getRestaurante().add(apiLinkBuilder.linkToRestaurante(pedidoModel.getRestaurante().getId()));
		
		pedidoModel.getCliente().add(apiLinkBuilder.linkToUsuario(pedidoModel.getCliente().getId()));
		
		pedidoModel.getFormaPagamento().add(apiLinkBuilder.linkToFormaPagamento(pedidoModel.getFormaPagamento().getId()));
		
		pedidoModel.getEndereco().getCidade().add(apiLinkBuilder.linkToCidade(pedidoModel.getEndereco().getCidade().getId()));
		
		pedidoModel.getItens().forEach(itemPedido -> {
			Long produtoId = itemPedido.getProdutoId();
			itemPedido.add(apiLinkBuilder.linkToProdutos(pedidoModel.getRestaurante().getId(), produtoId, IanaLinkRelations.SELF.toString()));
		});
		
		return pedidoModel;
	}
}
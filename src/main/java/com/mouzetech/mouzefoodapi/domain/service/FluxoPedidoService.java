package com.mouzetech.mouzefoodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mouzetech.mouzefoodapi.domain.model.Pedido;
import com.mouzetech.mouzefoodapi.domain.repository.PedidoRepository;

@Service
public class FluxoPedidoService {

	@Autowired
	private EmissaoPedidoService emissaoPedidoService;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Transactional
	public void confirmarPedido(String pedidoCodigo) {
		Pedido pedido = emissaoPedidoService.buscarPorCodigo(pedidoCodigo);
		pedido.confirmar();
		
		pedidoRepository.save(pedido);
	}
	
	@Transactional
	public void cancelarPedido(String pedidoCodigo) {
		Pedido pedido = emissaoPedidoService.buscarPorCodigo(pedidoCodigo);
		pedido.cancelar();
		
		pedidoRepository.save(pedido);
	}
	
	@Transactional
	public void entregarPedido(String pedidoCodigo) {
		Pedido pedido = emissaoPedidoService.buscarPorCodigo(pedidoCodigo);
		pedido.entregar();
	}
}
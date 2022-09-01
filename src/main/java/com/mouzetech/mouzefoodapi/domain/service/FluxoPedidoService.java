package com.mouzetech.mouzefoodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mouzetech.mouzefoodapi.domain.model.Pedido;
import com.mouzetech.mouzefoodapi.domain.service.EnvioEmailService.Mensagem;

@Service
public class FluxoPedidoService {

	@Autowired
	private EmissaoPedidoService emissaoPedidoService;
	
	@Autowired
	private EnvioEmailService envioEmailService;
	
	@Transactional
	public void confirmarPedido(String pedidoCodigo) {
		Pedido pedido = emissaoPedidoService.buscarPorCodigo(pedidoCodigo);
		pedido.confirmar();
		
		Mensagem mensagem = Mensagem.builder()
								.assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
								.destinatario(pedido.getCliente().getEmail())
								.corpo("pedido-confirmado.html")
								.variavel("pedido", pedido)
								.build();
		
		envioEmailService.enviar(mensagem);
	}
	
	@Transactional
	public void cancelarPedido(String pedidoCodigo) {
		Pedido pedido = emissaoPedidoService.buscarPorCodigo(pedidoCodigo);
		pedido.cancelar();
	}
	
	@Transactional
	public void entregarPedido(String pedidoCodigo) {
		Pedido pedido = emissaoPedidoService.buscarPorCodigo(pedidoCodigo);
		pedido.entregar();
	}
}
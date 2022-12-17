package com.mouzetech.mouzefoodapi.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.mouzetech.mouzefoodapi.domain.event.PedidoCanceladoEvent;
import com.mouzetech.mouzefoodapi.domain.model.Pedido;
import com.mouzetech.mouzefoodapi.domain.service.EnvioEmailService;
import com.mouzetech.mouzefoodapi.domain.service.EnvioEmailService.Mensagem;

@Component
public class NotificacaoClientePedidoCanceladoListener {

	@Autowired
	private EnvioEmailService envioEmailService;
	
	@TransactionalEventListener
	public void aoCancelarPedidoEnviarEmail(PedidoCanceladoEvent event) {
		Pedido pedido = event.getPedido();
		
		Mensagem mensagem = Mensagem.builder()
				.assunto(pedido.getRestaurante().getNome() + " - Pedido cancelado")
				.destinatario(pedido.getCliente().getEmail())
				.corpo("pedido-cancelado.html")
				.variavel("pedido", pedido)
				.build();

		envioEmailService.enviar(mensagem);
	}	
}
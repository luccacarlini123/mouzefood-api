package com.mouzetech.mouzefood.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.mouzetech.mouzefood.domain.event.PedidoConfirmadoEvent;
import com.mouzetech.mouzefood.domain.model.Pedido;
import com.mouzetech.mouzefood.domain.service.EnvioEmailService;
import com.mouzetech.mouzefood.domain.service.EnvioEmailService.Mensagem;

@Component
public class NotificacaoClientePedidoConfirmadoListener {

	@Autowired
	private EnvioEmailService envioEmailService;
	
	@TransactionalEventListener
	public void aoConfirmarPedidoEnviarEmail(PedidoConfirmadoEvent event) {
		Pedido pedido = event.getPedido();
		
		Mensagem mensagem = Mensagem.builder()
				.assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
				.destinatario(pedido.getCliente().getEmail())
				.corpo("pedido-confirmado.html")
				.variavel("pedido", pedido)
				.build();

		envioEmailService.enviar(mensagem);
	}
	
}
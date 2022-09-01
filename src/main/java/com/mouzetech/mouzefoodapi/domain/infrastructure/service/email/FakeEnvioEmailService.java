package com.mouzetech.mouzefoodapi.domain.infrastructure.service.email;

import com.mouzetech.mouzefoodapi.domain.service.EnvioEmailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEnvioEmailService implements EnvioEmailService {

	@Override
	public void enviar(Mensagem mensagem) {
		try {
			log.info("--- INICIALIZANDO ENVIO DE EMAIL ---");
			log.info("...");
			log.info("Enviando email para: " + mensagem.getDestinatarios().toString());
			log.info("Assunto do email: " + mensagem.getAssunto());
			log.info("Corpo do email: ");
			log.info(mensagem.getCorpo());
			log.info("...");
			log.info("--- FINALIZANDO ENVIO DE EMAIL ---");
			log.info("...");
			log.info("--- Email enviado com sucesso! ---");
		} catch(Exception e) {
			log.info("--- Não possível fazer o envio do email. ---");
		}
	}

}

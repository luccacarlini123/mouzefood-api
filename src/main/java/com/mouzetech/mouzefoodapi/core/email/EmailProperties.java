package com.mouzetech.mouzefoodapi.core.email;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("mouzefood.email")
public class EmailProperties {

	private String remetente;
	
	private Sandbox sandbox = new Sandbox();
	
	private Implementacao impl = Implementacao.FAKE;
	
	public enum Implementacao {
		FAKE, SMTP, SANDBOX;
	}
	
	@Getter
	@Setter
	public class Sandbox {
		String destinatario;
	}
	
}
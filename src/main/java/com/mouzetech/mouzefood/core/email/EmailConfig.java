package com.mouzetech.mouzefood.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mouzetech.mouzefood.core.email.EmailProperties.Implementacao;
import com.mouzetech.mouzefood.domain.infrastructure.service.email.FakeEnvioEmailService;
import com.mouzetech.mouzefood.domain.infrastructure.service.email.SandboxEnvioEmailService;
import com.mouzetech.mouzefood.domain.infrastructure.service.email.SmtpEnvioEmailService;
import com.mouzetech.mouzefood.domain.service.EnvioEmailService;

@Configuration
public class EmailConfig {

	@Autowired
	private EmailProperties emailProperties;
	
	@Bean
	public EnvioEmailService emailService() {
		if(emailProperties.getImpl().equals(Implementacao.FAKE)) {
			return new FakeEnvioEmailService();
		} else if(emailProperties.getImpl().equals(Implementacao.SMTP)) {
			return new SmtpEnvioEmailService();
		} else {
			return new SandboxEnvioEmailService();
		}
	}
}
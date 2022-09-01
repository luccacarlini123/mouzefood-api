package com.mouzetech.mouzefoodapi.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mouzetech.mouzefoodapi.domain.infrastructure.service.email.FakeEnvioEmailService;
import com.mouzetech.mouzefoodapi.domain.infrastructure.service.email.SmtpEnvioEmailService;
import com.mouzetech.mouzefoodapi.domain.service.EnvioEmailService;

@Configuration
public class EmailConfig {

	@Autowired
	private EmailProperties emailProperties;
	
	@Bean
	public EnvioEmailService emailService() {
		if(emailProperties.getImpl().equals("fake")) {
			return new FakeEnvioEmailService();
		} else {
			return new SmtpEnvioEmailService();
		}
	}
}
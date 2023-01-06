package com.mouzetech.mouzefood.domain.infrastructure.service.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.mouzetech.mouzefood.core.email.EmailProperties;

public class SandboxEnvioEmailService extends SmtpEnvioEmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private EmailProperties emailProperties;

	@Override
	protected MimeMessage createMimeMessage(Mensagem mensagem) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		String corpo = processarTemplate(mensagem);
		
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
		mimeMessageHelper.setFrom(emailProperties.getRemetente());
		mimeMessageHelper.setTo(emailProperties.getSandbox().getDestinatario());
		mimeMessageHelper.setSubject(mensagem.getAssunto());
		mimeMessageHelper.setText(corpo, true);
		return mimeMessage;
	}

}
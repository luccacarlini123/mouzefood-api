package com.mouzetech.mouzefood.domain.infrastructure.service.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.mouzetech.mouzefood.core.email.EmailProperties;
import com.mouzetech.mouzefood.domain.exception.EmailException;
import com.mouzetech.mouzefood.domain.service.EnvioEmailService;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class SmtpEnvioEmailService implements EnvioEmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private EmailProperties emailProperties;
	
	@Autowired 
	private Configuration freemarkerConfig;
	
	@Override
	public void enviar(Mensagem mensagem) {		
		try {
			MimeMessage mimeMessage = createMimeMessage(mensagem);
			
			javaMailSender.send(mimeMessage);
		} catch (MessagingException e) {
			throw new EmailException("Não foi possível enviar o e-mail.", e);
		}
	}

	protected MimeMessage createMimeMessage(Mensagem mensagem) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		String corpo = processarTemplate(mensagem);
		
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
		mimeMessageHelper.setFrom(emailProperties.getRemetente());
		mimeMessageHelper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
		mimeMessageHelper.setSubject(mensagem.getAssunto());
		mimeMessageHelper.setText(corpo, true);
		return mimeMessage;
	}
	
	protected String processarTemplate(Mensagem mensagem) {
		try {
			Template template = freemarkerConfig.getTemplate(mensagem.getCorpo());
			
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getVariaveis());
		} catch (Exception e) {
			throw new EmailException("Não foi possível montar o template do e-mail.", e);
		}
	}

}
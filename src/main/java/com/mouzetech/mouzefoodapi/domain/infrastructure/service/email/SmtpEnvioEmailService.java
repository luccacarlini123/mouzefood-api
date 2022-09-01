package com.mouzetech.mouzefoodapi.domain.infrastructure.service.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.mouzetech.mouzefoodapi.core.email.EmailProperties;
import com.mouzetech.mouzefoodapi.domain.exception.EmailException;
import com.mouzetech.mouzefoodapi.domain.service.EnvioEmailService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			
			String corpo = processarTemplate(mensagem);
			
			log.info(corpo);
			
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
			mimeMessageHelper.setFrom(emailProperties.getRemetente());
			mimeMessageHelper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
			mimeMessageHelper.setSubject(mensagem.getAssunto());
			mimeMessageHelper.setText(corpo, true);
			
			javaMailSender.send(mimeMessage);
		} catch (MessagingException e) {
			throw new EmailException("Não foi possível enviar o e-mail.", e);
		}
	}
	
	private String processarTemplate(Mensagem mensagem) {
		try {
			Template template = freemarkerConfig.getTemplate(mensagem.getCorpo());
			
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getVariaveis());
		} catch (Exception e) {
			throw new EmailException("Não foi possível montar o template do e-mail.", e);
		}
	}

}
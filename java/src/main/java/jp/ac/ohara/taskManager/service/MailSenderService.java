package jp.ac.ohara.taskManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import jp.ac.ohara.taskManager.config.email.PreRegister;

@Service
public class MailSenderService {
	@Autowired
	private JavaMailSender mailSender;

	public void preRegister(String email, String uuid) {
		MimeMessage message = this.mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = null;

		try {
			messageHelper = new MimeMessageHelper(message, true);
			messageHelper.setTo(email);
			messageHelper.setSubject(PreRegister.SUBJECT);
			messageHelper.setText(PreRegister.GetBody(uuid), true);
		} catch (Exception e) {
			System.out.println("[MailSenderService::preRegister] " + e.toString());
		}

		mailSender.send(message);
	}
}

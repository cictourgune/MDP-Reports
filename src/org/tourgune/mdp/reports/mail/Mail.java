package org.tourgune.mdp.reports.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.tourgune.mdp.reports.utils.Configuration;
import org.tourgune.mdp.reports.utils.Constants;

public class Mail {
	
	static Configuration configClass = Configuration.getInstance();
	
	public static void sendMail(String subject, String body, String contentType) throws Exception {
		
		Properties props = System.getProperties();
		Session session = Session.getDefaultInstance(props, null);
		Message msg = new MimeMessage(session);
		InternetAddress[] toAddresses = null;
		String[] toAddressesConfig = null;
		
		props = System.getProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.starttls.enable","true");
		props.put("mail.smtp.host", configClass.getProperty(Constants.MAIL_SMTP_HOST));
		props.put("mail.smtp.port", configClass.getProperty(Constants.MAIL_SMTP_PORT));
		props.put("mail.smtp.auth", "true");
		
		session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(configClass.getProperty(Constants.MAIL_USER), configClass.getProperty(Constants.MAIL_PASS));
			}
		  });
		msg = new MimeMessage(session); 
		
		try {
			msg.setFrom(new InternetAddress(configClass.getProperty(Constants.MAIL_FROM)));
			
			toAddressesConfig = configClass.getProperty(Constants.MAIL_TO).split(",");
			
			toAddresses = new InternetAddress[toAddressesConfig.length];
			for(int i=0; i<toAddressesConfig.length; i++)
			{
				toAddresses[i] = new InternetAddress(toAddressesConfig[i],false);
			}
			msg.setRecipients(Message.RecipientType.TO,toAddresses);  
			
			msg.setSubject(subject);
			msg.setContent(body, contentType); //text/html, text/plain

			msg.setSentDate(new Date());
			Transport.send(msg);
			
		} catch (Exception e) {
			System.out.println("[MDP] Mail.sendMail - Error enviando el mail");
			throw e;
		}
		System.out.println("[MDP] Mail.sendMail - Mail enviado correctamente");
	}
	
}

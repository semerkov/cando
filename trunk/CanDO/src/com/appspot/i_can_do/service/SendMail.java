package com.appspot.i_can_do.service;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@SuppressWarnings("serial")
public class SendMail extends HttpServlet {
	String ENCODING = "UTF-8";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String subject = "Subject";
		String content = "Test";
		String smtpHost = "smtp.km.ru";//smtp.gmail.com
		String from = "semerkov@km.ru";//semerkoff@gmail.com semerkov@km.ru
		String to = "semerkoff@gmail.com";//semerkoff@gmail.com
		String login = "semerkov";//
		String password = "";//
		String smtpPort = "25";//465 25
		try {
			sendSimpleMessage(login, password, from, to, content, subject,
					smtpPort, smtpHost);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	public void sendSimpleMessage(String login, String password, String from,
			String to, String content, String subject, String smtpPort,
			String smtpHost) throws MessagingException,
			UnsupportedEncodingException {
		Authenticator auth = new MyAuthenticator(login, password);
		Properties props = System.getProperties();
		props.put("mail.smtp.port", smtpPort);
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.auth", "true");
		props.put("mail.mime.charset", ENCODING);
		
		Session session = Session.getDefaultInstance(props, auth);
		Provider s = session.getProvider("smtp");

		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(from));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		msg.setSubject(subject);
		msg.setText(content);
		//Transport.send(msg);
		Transport transport = session.getTransport("smtp");
		transport.connect(smtpHost, 25, login, password);
		//transport.connect("semerkoff@gmail.com", "26september1992");
		//transport.connect("smtp.gmail.com", 25, "semerkoff", "26september1992");
		transport.sendMessage(msg, msg.getAllRecipients());
		transport.close();
	}
}

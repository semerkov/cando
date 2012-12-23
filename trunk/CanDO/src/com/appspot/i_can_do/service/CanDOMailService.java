package com.appspot.i_can_do.service;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class CanDOMailService {
	private static final Logger log = Logger.getLogger(CanDOSecurityService.class.getName());
	private static CanDOMailService mailService;
	
	private static String adminEmail = "i-can-do@appspot.gserviceaccount.com";
		
	public static CanDOMailService instance() {
		if(mailService==null) mailService = new CanDOMailService();
		return mailService;
	}
	private CanDOMailService(){
		
	}
	
	public boolean SendEmailFromAdmin(String userEmail,String title, String message) throws UnsupportedEncodingException{
		
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(adminEmail, "i-can-do.appspot.com Admin"));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(userEmail, "Mr. User"));
            msg.setSubject(title);
            msg.setText(message);
            Transport.send(msg);
    
        } catch (AddressException e) {
            // ...
        	return false;
        } catch (MessagingException e) {
            // ...
        	return false;
        }
        return true;
	}
}

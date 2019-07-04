package com.altimetrik.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
	
	public void sendMailInteraction(){
		String to = "mc.lakshmipriya2014@vit.ac.in";  
	      String from = "mhclpriya@gmail.com";
	      Properties properties = System.getProperties(); 
	      properties.setProperty("mail.smtp.host", "smtp.gmail.com");  
	      properties.put("mail.smtp.port", "587");
	      properties.put("mail.smtp.auth", "true");
	      properties.put("mail.smtp.starttls.enable", "true");
	      Session session = Session.getDefaultInstance(properties);  
	      
	      try{  
	          MimeMessage message = new MimeMessage(session);  
	          message.setFrom(new InternetAddress(from));  
	          message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
	          message.setSubject("Ping");  
	          message.setText("Hello, this is example of sending email  ");  
	   
	          // Send message  
	          Transport.send(message);  
	          System.out.println("message sent successfully....");  
	   
	       }
	      catch (MessagingException m) 
	      {
	    	  m.printStackTrace();
	    	  }  
	    }  
	      
	}



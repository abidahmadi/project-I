package serviceUtility;


import java.util.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class MultiEmail {

		  public static void sendEmailToAll(String[] emails, String sub, String text) {    
		    //public static void sendEmailNow(String recipeint, String sub, String text){  
		      
			  String[] em = emails;
			  
			  
		      String from = "yellowpdts@gmail.com";
		      Properties properties = new Properties();
		      properties.put("mail.smtp.auth", "true");
		      properties.put("mail.smtp.starttls.enable", "true");
		      properties.put("mail.smtp.host", "smtp.gmail.com");
		      properties.put("mail.smtp.port", "587");

		      // Get the default Session object.
		      Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator(){
		    	  protected PasswordAuthentication getPasswordAuthentication(){
		    		  return new PasswordAuthentication("yellowpdts@gmail.com", "zadaniah");
		    	  }
		      });
		    for(int i =0; i<em.length;i++){
		      try {
		         // Create a default MimeMessage object.
		         MimeMessage message = new MimeMessage(session);
		         message.setFrom(new InternetAddress(from));
		        
		         message.addRecipient(Message.RecipientType.TO, new InternetAddress(em[i]));
		         message.setSubject(sub);
		         message.setText(text);
		      
		         Transport.send(message);
		         System.out.println("Sent message successfully....");
		      } catch (MessagingException mex) {
		         mex.printStackTrace();
		      }
		   }
		   }

	}



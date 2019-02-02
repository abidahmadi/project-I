package serviceUtility;

import java.util.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class SendEmail {
	
   //public static void main(String [] args) {    
    public static void sendEmailNow(String recipeint, String sub, String text){  
      
      String to = recipeint;
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

      try {
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);
         message.setFrom(new InternetAddress(from));
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
         message.setSubject(sub);
         message.setText(text);
      
         Transport.send(message);
         System.out.println("Sent message successfully....");
      } catch (MessagingException mex) {
         mex.printStackTrace();
      }
   }
}
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.activation.*;
import static java.lang.Math.random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;


public class GoToMail {

	public static void sendMail(String recipient, String newPass){
		String USER_NAME = "smtp.python.bh";
		String PASSWORD = "smtp.python.bh2014";
		String subject = "Your new password";
        String body = "Your new password: " + newPass;
        
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", USER_NAME);
        props.put("mail.smtp.password", PASSWORD);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        
        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);
        
        try {
            message.setFrom(new InternetAddress(USER_NAME));
            InternetAddress toAddress = new InternetAddress(recipient);
            
            message.addRecipient(Message.RecipientType.TO, toAddress);
            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, USER_NAME, PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
	}
	
	
	public static String randomPassword(){
		String symbs = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890*@`~!#$%^&*()_-+=}[]|:;'<>,.?/";
		char[] symb = symbs.toCharArray();
		String pass = "";
		Random random = new Random();
		for(int i=0; i < (int) (random() * 7 + 4); i++){
			pass +=symb[(int) (random() * symb.length)];
		}
		return pass;
	}
	
	public static String checkedRandPass(){
		String pass = "";
		do{
			pass = randomPassword();
		}
		while(!Checker.password(pass));
		return pass;
	}
}

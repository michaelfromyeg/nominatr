package qualtrics;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Contact {
  
  public Contact() {}

  public static void contact() {
    String recipient = "mdemar01@student.ubc.ca";
    String sender = "michaelfromyeg@gmail.com";
    String host = "localhost";
    Properties properties = System.getProperties();
    properties.setProperty("mail.smtp.host", host);
    Session session = Session.getDefaultInstance(properties);
    try {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sender));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        // message.addRecipients(Message.RecipientType.TO, new Address["mdemar01@student.ubc.ca"]); // email to multiple recipients
        message.setSubject("Hello World!");
        message.setText("And hello from the body of the message!");
        Transport.send(message);
    } catch (AddressException e) {
        e.printStackTrace();
    } catch (MessagingException e) {
        e.printStackTrace();
    }
  }
}

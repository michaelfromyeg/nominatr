package qualtrics;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.StringWriter;

import io.github.cdimascio.dotenv.Dotenv;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class Contact {

  private static Dotenv dotenv;
  private static String HOST;
  private static int PORT;
  private static String USERNAME;
  private static String PASSWORD;
  
  public Contact() {
    // Load environment variables
    dotenv = Dotenv.configure().load();

    // Initialize API keys, survey info
    HOST = dotenv.get("MAIL_HOST");
    PORT = Integer.parseInt(dotenv.get("MAIL_PORT"));
    USERNAME = dotenv.get("MAIL_USERNAME");
    PASSWORD = dotenv.get("MAIL_PASSWORD");
  }

  public void contact() throws Exception {
    Properties prop = new Properties();
    prop.put("mail.smtp.auth", true);
    prop.put("mail.smtp.starttls.enable", "true");
    prop.put("mail.smtp.host", HOST);
    prop.put("mail.smtp.port", PORT);
    prop.put("mail.smtp.ssl.trust", HOST);

    Session session = Session.getInstance(prop, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(USERNAME, PASSWORD);
      }
    });

    try {

      Map<String, String> data = new HashMap<>();
      data.put("name", "Michael DeMarco");

      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress("michaelfromyeg@gmail.com"));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("mdemar01@student.ubc.ca"));
      message.setSubject("Mail Subject");

      Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
      cfg.setDefaultEncoding("UTF-8");
      FileTemplateLoader ftl = new FileTemplateLoader(new File("./qualtrics/src/main/java/qualtrics/templates/"));
      cfg.setTemplateLoader(ftl);
      Template template = cfg.getTemplate("elections-template.ftl");
      StringWriter stringWriter = new StringWriter();
      template.process(data, stringWriter);
      String msg = stringWriter.toString();

      MimeBodyPart mimeBodyPart = new MimeBodyPart();
      mimeBodyPart.setContent(msg, "text/html");

      MimeBodyPart attachmentBodyPart = new MimeBodyPart();
      attachmentBodyPart.attachFile(new File("./qualtrics/out/survey.csv"));

      Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(mimeBodyPart);
      multipart.addBodyPart(attachmentBodyPart);

      message.setContent(multipart);

      Transport.send(message);
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception("Couldn\'t contact election participants");
    }
  }
}

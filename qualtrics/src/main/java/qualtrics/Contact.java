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

  private Dotenv dotenv;
  private String HOST;
  private int PORT;
  private String USERNAME;
  private String PASSWORD;
  private Election election;
  
  public Contact(Election election) {
    // Load environment variables
    dotenv = Dotenv.configure().load();
    // Initialize API keys, survey info
    HOST = dotenv.get("MAIL_HOST");
    PORT = Integer.parseInt(dotenv.get("MAIL_PORT"));
    USERNAME = dotenv.get("MAIL_USERNAME");
    PASSWORD = dotenv.get("MAIL_PASSWORD");
    this.election = election;
  }

  public void contactElections() throws Exception {
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
      /**
       * Build data for sending emails
       * 
       * Elections team
       * Key-value of key=table (nominees, nominators), value=list (to render in table, need key-value again)
       * 
       * Nominees
       * List of key-value that is key=email (nominee's email), value=data; data consists of key=table (summary, nominators) value=list (key-value again)
       * 
       * Nominators 
       * List of key-value that is key=email (nominator's email), value=data; data consits of key=nominee, value=position
       */
      Map<String, Map<String, String>> electionsData = buildElectionsData(election);
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress("michaelfromyeg@gmail.com"));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("mdemar01@student.ubc.ca"));
      message.setSubject("[Qualtrics] Daily Elections Update");
      Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
      cfg.setDefaultEncoding("UTF-8");
      FileTemplateLoader ftl = new FileTemplateLoader(new File("./qualtrics/src/main/java/qualtrics/templates/"));
      cfg.setTemplateLoader(ftl);
      Template template = cfg.getTemplate("elections-template.ftl");
      StringWriter stringWriter = new StringWriter();
      template.process(electionsData, stringWriter);
      String msg = stringWriter.toString();
      MimeBodyPart mimeBodyPart = new MimeBodyPart();
      mimeBodyPart.setContent(msg, "text/html");
      MimeBodyPart attachmentCSV = new MimeBodyPart();
      attachmentCSV.attachFile(new File("./qualtrics/out/survey.csv"));
      MimeBodyPart attachmentLog = new MimeBodyPart();
      attachmentLog.attachFile(new File("./qualtrics/out/ElectionsLog.log"));
      Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(mimeBodyPart);
      multipart.addBodyPart(attachmentCSV);
      multipart.addBodyPart(attachmentLog);
      message.setContent(multipart);
      Transport.send(message);
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception("Couldn\'t contact election participants");
    }
  }

  public void contactNominees() throws Exception {
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
      Map<String, Map<String, Map<String, String>>> nomineesData = buildNomineesData(election);
      for (Map.Entry<String, Map<String, Map<String, String>>> entry : nomineesData.entrySet()) {
        String key = entry.getKey();
        Map<String, Map<String, String>> value = entry.getValue();
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("michaelfromyeg@gmail.com"));
        App.logger.info("Sending an email to " + key);
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("mdemar01@student.ubc.ca"));
        message.setSubject("[Qualtrics] SUS Election Nominee Receipt");
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setDefaultEncoding("UTF-8");
        FileTemplateLoader ftl = new FileTemplateLoader(new File("./qualtrics/src/main/java/qualtrics/templates/"));
        cfg.setTemplateLoader(ftl);
        Template template = cfg.getTemplate("nominee-template.ftl");
        StringWriter stringWriter = new StringWriter();
        template.process(value, stringWriter);
        String msg = stringWriter.toString();
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);
        Transport.send(message);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception("Couldn\'t contact election participants");
    }
  }

  public void contactNominators() throws Exception {
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
      Map<String, Map<String, Map<String, String>>> nominatorsData = buildNominatorsData(election);
      for (Map.Entry<String, Map<String, Map<String, String>>> entry : nominatorsData.entrySet()) {
        String key = entry.getKey();
        Map<String, Map<String, String>> value = entry.getValue();
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("michaelfromyeg@gmail.com"));
        App.logger.info("Sending an email to the nominator: " + key);
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("mdemar01@student.ubc.ca"));
        message.setSubject("[Qualtrics] SUS Election Nomination Receipt");
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setDefaultEncoding("UTF-8");
        FileTemplateLoader ftl = new FileTemplateLoader(new File("./qualtrics/src/main/java/qualtrics/templates/"));
        cfg.setTemplateLoader(ftl);
        Template template = cfg.getTemplate("nominator-template.ftl");
        StringWriter stringWriter = new StringWriter();
        template.process(value, stringWriter);
        String msg = stringWriter.toString();
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);
        Transport.send(message);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception("Couldn\'t contact nominators");
    }
  }

  public Map<String, Map<String, String>> buildElectionsData(Election e) {
    Map<String, Map<String, String>> electionsMap = new HashMap<String, Map<String, String>>();
    Map<String, String> nomineesMap = new HashMap<String, String>();
    Map<String, String> nominatorsMap = new HashMap<String, String>();
    for (Nominee n : e.getNominees()) {
      nomineesMap.put(n.getFullName() + " for " + n.getRunningForPositionName(), n.getTally() + "");
    }
    electionsMap.put("nominees", nomineesMap);
    for (Nominator n : e.getNominators()) {
      nominatorsMap.put(n.getFullName(), n.getNominatingName());
    }
    electionsMap.put("nominators", nominatorsMap);
    return electionsMap;
  }

  public Map<String, Map<String, Map<String, String>>> buildNomineesData(Election election) {
    Map<String, Map<String, Map<String, String>>> nomineesMap = new HashMap<String, Map<String, Map<String, String>>>();
    for (Nominee n : election.getNominees()) {
      nomineesMap.put(n.getEmail(), this.getNomineeSummary(election, n));
    }
    return nomineesMap;
  }

  public Map<String, Map<String, String>> getNomineeSummary(Election election, Nominee nominee) {
    Map<String, Map<String, String>> summaryMap = new HashMap<String, Map<String, String>>();
    Map<String, String> nameMap = new HashMap<String, String>();
    Map<String, String> nomineeMap = new HashMap<String, String>();
    Map<String, String> theirNominatorsMap = new HashMap<String, String>();
    nameMap.put(nominee.getFullName(), nominee.getFullName());
    summaryMap.put("name", nameMap);
    nomineeMap.put(nominee.getFullName() + " for " + nominee.getRunningForPositionName(), nominee.getTally() + "");
    summaryMap.put("nominees", nomineeMap);
    for (Nominator n : election.getNominators()) {
      if (n.getNominatingName().equals(nominee.getFullName())) {
        theirNominatorsMap.put(n.getFullName(), n.getNominatingName());
      }
    }
    summaryMap.put("nominators", theirNominatorsMap);
    return summaryMap;
  }

  public Map<String, Map<String, Map<String, String>>> buildNominatorsData(Election election) {
    Map<String, Map<String, Map<String, String>>> nominatorsMap = new HashMap<String, Map<String, Map<String, String>>>();
    for (Nominator n : election.getNominators()) {
      nominatorsMap.put(n.getEmail(), this.getNominatorSummary(election, n));
    }
    return nominatorsMap;
  }

  public Map<String, Map<String, String>> getNominatorSummary(Election election, Nominator nominator) {
    Map<String, Map<String, String>> summaryMap = new HashMap<String, Map<String, String>>();
    Map<String, String> nameMap = new HashMap<String, String>();
    Map<String, String> nominatorMap = new HashMap<String, String>();
    nameMap.put(nominator.getFullName(), nominator.getFullName());
    summaryMap.put("name", nameMap);
    nominatorMap.put(nominator.getFullName(), nominator.getNominatingName());
    summaryMap.put("nominators", nominatorMap);
    return summaryMap;
  }
}

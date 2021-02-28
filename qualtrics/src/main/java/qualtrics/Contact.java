package qualtrics;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Contact election participants via email.
 */
public class Contact {

  /**
   * The host for sending mail over.
   */
  private String host;

  /**
   * The port to send mail over.
   */
  private int port;

  /**
   * The from-email.
   */
  private String username;

  /**
   * The email password.
   */
  private String password;

  /**
   * A copy of the current election data.
   */
  private Election election;


  /**
   * Create an instance of the Contact object.
   *
   * @param e the election object
   */
  public Contact(final Election e) {
    // Initialize API keys, survey info
    host = App.getDotenv().get("MAIL_HOST");
    port = Integer.parseInt(App.getDotenv().get("MAIL_PORT"));
    username = App.getDotenv().get("MAIL_USERNAME");
    password = App.getDotenv().get("MAIL_PASSWORD");
    this.election = e;
  }

  /**
   * Fetch the contact instance's election data.
   *
   * @return the election variable
   */
  public final Election getElection() {
    return this.election;
  }

  /**
   * Sets the contact instance's election data.
   *
   * @param e the election variable
   */
  public final void setElection(final Election e) {
    this.election = e;
  }

  /**
   * This is a comment.
   *
   * @throws Exception if there was an issue contacting election participants
   */
  public final void contactElections() throws Exception {
    Properties prop = new Properties();
    prop.put("mail.smtp.auth", true);
    prop.put("mail.smtp.starttls.enable", "true");
    prop.put("mail.smtp.host", host);
    prop.put("mail.smtp.port", port);
    prop.put("mail.smtp.ssl.trust", host);
    Session session = Session.getInstance(prop, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
      }
    });
    try {
      Map<String, Map<String, String>> electionsData =
          buildElectionsData(election);
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress("michaelfromyeg@gmail.com"));
      message.setRecipients(Message.RecipientType.TO,
          InternetAddress.parse("mdemar01@student.ubc.ca"));
      message.setSubject("[Qualtrics] Daily Elections Update");
      Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
      cfg.setDefaultEncoding("UTF-8");
      FileTemplateLoader ftl = new FileTemplateLoader(
          new File("./qualtrics/src/main/java/qualtrics/templates/"));
      cfg.setTemplateLoader(ftl);
      Template template = cfg.getTemplate("elections-template.ftl");
      StringWriter stringWriter = new StringWriter();
      template.process(electionsData, stringWriter);
      String msg = stringWriter.toString();
      MimeBodyPart mimeBodyPart = new MimeBodyPart();
      mimeBodyPart.setContent(msg, "text/html");
      MimeBodyPart attachmentCsv = new MimeBodyPart();
      attachmentCsv.attachFile(new File(App.getFilePath() + "/survey.csv"));
      MimeBodyPart attachmentLog = new MimeBodyPart();
      attachmentLog.attachFile(new File(App.getFilePath() + "/ElectionsLog.log"));
      Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(mimeBodyPart);
      multipart.addBodyPart(attachmentCsv);
      multipart.addBodyPart(attachmentLog);
      message.setContent(multipart);
      Transport.send(message);
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception("Couldn\'t contact election participants");
    }
  }

  /**
   * This is a comment.
   */
  public final void contactNominees() throws Exception {
    Properties prop = new Properties();
    prop.put("mail.smtp.auth", true);
    prop.put("mail.smtp.starttls.enable", "true");
    prop.put("mail.smtp.host", host);
    prop.put("mail.smtp.port", port);
    prop.put("mail.smtp.ssl.trust", host);
    Session session = Session.getInstance(prop, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
      }
    });
    try {
      Map<String, Map<String, Map<String, String>>> nomineesData =
          buildNomineesData(election);
      for (Map.Entry<String, Map<String, Map<String, String>>> entry
          : nomineesData.entrySet()) {
        String key = entry.getKey();
        Map<String, Map<String, String>> value = entry.getValue();
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("michaelfromyeg@gmail.com"));
        App.getLogger().info("Sending an email to " + key);
        message.setRecipients(
            Message.RecipientType.TO,
            InternetAddress.parse("mdemar01@student.ubc.ca"));
        message.setSubject("[Qualtrics] SUS Election Nominee Receipt");
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setDefaultEncoding("UTF-8");
        FileTemplateLoader ftl = new FileTemplateLoader(
            new File("./qualtrics/src/main/java/qualtrics/templates/"));
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

  /**
   * This is a comment.
   *
   * @throws Exception if there's any issue with contacting nominators, throw an exception
   */
  public final void contactNominators() throws Exception {
    Properties prop = new Properties();
    prop.put("mail.smtp.auth", true);
    prop.put("mail.smtp.starttls.enable", "true");
    prop.put("mail.smtp.host", host);
    prop.put("mail.smtp.port", port);
    prop.put("mail.smtp.ssl.trust", host);
    Session session = Session.getInstance(prop, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
      }
    });
    try {
      Map<String, Map<String, Map<String, String>>> nominatorsData =
          buildNominatorsData(election);
      for (Map.Entry<String, Map<String, Map<String, String>>> entry
          : nominatorsData.entrySet()) {
        String key = entry.getKey();
        Map<String, Map<String, String>> value = entry.getValue();
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("michaelfromyeg@gmail.com"));
        App.getLogger().info("Sending an email to the nominator: " + key);
        message.setRecipients(
            Message.RecipientType.TO,
            InternetAddress.parse("mdemar01@student.ubc.ca"));
        message.setSubject("[Qualtrics] SUS Election Nomination Receipt");
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setDefaultEncoding("UTF-8");
        FileTemplateLoader ftl = new FileTemplateLoader(
            new File("./qualtrics/src/main/java/qualtrics/templates/"));
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

  /**
   * This is a comment.
   *
   * @param e the elections object
   * @return elections data in the correct format for FTL
   */
  public final Map<String, Map<String, String>>
      buildElectionsData(final Election e) {
    Map<String, Map<String, String>> electionsMap =
        new HashMap<String, Map<String, String>>();
    Map<String, String> nomineesMap = new HashMap<String, String>();
    Map<String, String> nominatorsMap = new HashMap<String, String>();
    for (Nominee n : e.getNominees()) {
      nomineesMap.put(n.getFullName()
          + " for "
          + n.getRunningForPositionName(),
          n.getTally() + "");
    }
    electionsMap.put("nominees", nomineesMap);
    for (Nominator n : e.getNominators()) {
      nominatorsMap.put(n.getFullName(), n.getNominatingName());
    }
    electionsMap.put("nominators", nominatorsMap);
    return electionsMap;
  }

  /**
   * This is a comment.
   *
   * @param e the elections object
   * @return nominees data in the correct format for FTL
   */
  public final Map<String, Map<String, Map<String, String>>>
      buildNomineesData(final Election e) {
    Map<String, Map<String, Map<String, String>>> nomineesMap =
        new HashMap<String, Map<String, Map<String, String>>>();
    for (Nominee n : e.getNominees()) {
      nomineesMap.put(n.getEmail(), this.getNomineeSummary(e, n));
    }
    return nomineesMap;
  }

  /**
   * Return data for the nominee summary email.
   * @param e the elections object
   * @param nominee an individual nominee
   * @return formatted data for nominee summary email.
   */
  public final Map<String, Map<String, String>>
      getNomineeSummary(final Election e, final Nominee nominee) {
    Map<String, Map<String, String>> summaryMap =
        new HashMap<String, Map<String, String>>();
    Map<String, String> nameMap = new HashMap<String, String>();
    Map<String, String> nomineeMap = new HashMap<String, String>();
    Map<String, String> theirNominatorsMap = new HashMap<String, String>();
    nameMap.put(nominee.getFullName(), nominee.getFullName());
    summaryMap.put("name", nameMap);
    nomineeMap.put(nominee.getFullName() + " for "
        + nominee.getRunningForPositionName(),
        nominee.getTally() + "");
    summaryMap.put("nominees", nomineeMap);
    for (Nominator n : nominee.getNominators()) {
      theirNominatorsMap.put(n.getFullName(), n.getNominatingName());
    }
    summaryMap.put("nominators", theirNominatorsMap);
    return summaryMap;
  }

  /**
   * Return formatted nominator data for FTL email.
   * @param e the election object
   * @return formatted nominator data
   */
  public final Map<String, Map<String, Map<String, String>>>
      buildNominatorsData(final Election e) {
    Map<String, Map<String, Map<String, String>>> nominatorsMap =
        new HashMap<String, Map<String, Map<String, String>>>();
    for (Nominator n : e.getNominators()) {
      nominatorsMap.put(n.getEmail(), this.getNominatorSummary(e, n));
    }
    return nominatorsMap;
  }

  /**
   * Return data for nominator summary email.
   * @param e the election object
   * @param nominator an individual nominator
   * @return formatted nominator data
   */
  public final Map<String, Map<String, String>>
      getNominatorSummary(final Election e, final Nominator nominator) {
    Map<String, Map<String, String>> summaryMap =
        new HashMap<String, Map<String, String>>();
    Map<String, String> nameMap = new HashMap<String, String>();
    Map<String, String> nominatorMap = new HashMap<String, String>();
    nameMap.put(nominator.getFullName(), nominator.getFullName());
    summaryMap.put("name", nameMap);
    nominatorMap.put(nominator.getFullName(), nominator.getNominatingName());
    summaryMap.put("nominators", nominatorMap);
    return summaryMap;
  }
}

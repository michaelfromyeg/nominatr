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
   * Relative filepath to templates folder.
   */
  private String templates;

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
    templates = App.getDotenv().get("TEMPLATE_FILEPATH");
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
   * Contact the elections administrators.
   *
   * @throws Exception if there was an issue contacting election administrators.
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
      message.setFrom(new InternetAddress(username));
      // Email yourself
      message.setRecipients(Message.RecipientType.TO,
          InternetAddress.parse(username));
      message.setSubject("[Qualtrics] Elections Update");
      Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
      cfg.setDefaultEncoding("UTF-8");
      FileTemplateLoader ftl = new FileTemplateLoader(
          new File(templates + "/"));
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
      throw new Exception("Could not contact election administrators.");
    }
  }

  /**
   * Contact all nominees in the election.
   *
   * @throws Exception if there was an issue contacting nominees
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
        String nomineeEmail = entry.getKey();
        try {
          Map<String, Map<String, String>> value = entry.getValue();
          Message message = new MimeMessage(session);
          message.setFrom(new InternetAddress(username));
          App.getLogger().info("Sending an email to nominee: " + nomineeEmail);
          message.setRecipients(
              Message.RecipientType.TO,
              InternetAddress.parse(nomineeEmail));
          message.setSubject("[Qualtrics] SUS Election Nominee Receipt");
          Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
          cfg.setDefaultEncoding("UTF-8");
          FileTemplateLoader ftl = new FileTemplateLoader(
              new File(templates + "/"));
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
        } catch (Exception e) {
          e.printStackTrace();
          App.getLogger().warning("Could not contact " + nomineeEmail);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception("Could not contact nominees.");
    }
  }

  /**
   * Contact all nominators in the election.
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
        String nominatorEmail = entry.getKey();
        try {
          Map<String, Map<String, String>> value = entry.getValue();
          Message message = new MimeMessage(session);
          message.setFrom(new InternetAddress(username));
          App.getLogger().info("Sending an email to nominator: " + nominatorEmail);
          message.setRecipients(
              Message.RecipientType.TO,
              InternetAddress.parse(nominatorEmail));
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
        } catch (Exception e) {
          e.printStackTrace();
          App.getLogger().warning("Could not contact " + nominatorEmail);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception("Could not contact nominators.");
    }
  }

  /**
   * Build the election data for the EA email.
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
      String nomineeName = "";
      if (n.getFullName() == null) {
        nomineeName = n.getFirstName();
      } else {
        nomineeName = n.getFullName();
      }
      nomineesMap.put(nomineeName
          + " for "
          + n.getRunningForPositionName(),
          n.getTally() + "");
    }
    electionsMap.put("nominees", nomineesMap);
    for (Nominator n : e.getNominators()) {
      String nominatingName = "";
      if (n.getNominatingName() == null) {
        nominatingName = n.getNominatingNameFirstName();
      } else {
        nominatingName = n.getNominatingName();
      }
      nominatorsMap.put(n.getFullName(), nominatingName);
    }
    electionsMap.put("nominators", nominatorsMap);
    return electionsMap;
  }

  /**
   * Build data object for nominee data.
   *
   * @param e the elections object
   * @return nominees data in the correct format for FTL
   */
  public final Map<String, Map<String, Map<String, String>>>
      buildNomineesData(final Election e) {
    Map<String, Map<String, Map<String, String>>> nomineesMap =
        new HashMap<String, Map<String, Map<String, String>>>();
    for (Nominee n : e.getNominees()) {
      if (n.getShouldEmail()) {
        nomineesMap.put(n.getEmail(), this.getNomineeSummary(e, n));
      }
      if (n.getTally() >= 15) {
        n.setShouldEmail(false);
      }
    }
    return nomineesMap;
  }

  /**
   * Return data for the nominee summary email.
   * TODO: refactor to not use maps for everything...
   *
   * @param e the elections object
   * @param nominee an individual nominee
   * @return formatted data for nominee summary email.
   */
  public final Map<String, Map<String, String>>
      getNomineeSummary(final Election e, final Nominee nominee) {
    Map<String, Map<String, String>> summaryMap =
        new HashMap<String, Map<String, String>>();
    Map<String, String> nameMap = new HashMap<String, String>();
    Map<String, String> countMap = new HashMap<String, String>();
    Map<String, String> nomineeMap = new HashMap<String, String>();
    Map<String, String> theirNominatorsMap = new HashMap<String, String>();
    String nomineeName = nominee.getFullName() == null
        ? nominee.getFirstName() : nominee.getFullName();
    nameMap.put(nomineeName, nomineeName);
    // Total votes, votes needed
    countMap.put(nominee.getTally() + "",
        Integer.max(App.getNominationsRequired() - nominee.getTally(), 0) + "");
    summaryMap.put("name", nameMap);
    summaryMap.put("count", countMap);
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
   *
   * @param e the election object
   * @return formatted nominator data
   */
  public final Map<String, Map<String, Map<String, String>>>
      buildNominatorsData(final Election e) {
    Map<String, Map<String, Map<String, String>>> nominatorsMap =
        new HashMap<String, Map<String, Map<String, String>>>();
    for (Nominator n : e.getNominators()) {
      if (n.getShouldEmail()) {
        nominatorsMap.put(n.getEmail(), this.getNominatorSummary(e, n));
      }
      n.setShouldEmail(false);
    }
    return nominatorsMap;
  }

  /**
   * Return data for nominator summary email.
   *
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

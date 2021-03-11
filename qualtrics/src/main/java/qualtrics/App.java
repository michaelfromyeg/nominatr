package qualtrics;

import io.github.cdimascio.dotenv.Dotenv;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
* App acts as a driver class for downloading,
* parsing, and processing current Qualtrics responses.
*/
public final class App {

  /**
  * Global logger object.
  */
  private static Logger logger;

  /**
   * Global dotenv object.
   */
  private static Dotenv dotenv;

  /**
   * Global filepath.
   */
  private static String filePath;

  /**
  * Whether or not in a run, they app should download new data.
  */
  private static boolean shouldDownload = true;

  /**
  * A list of responses from the Qualtrics survey.
  */
  private static List<Response> responses;

  /**
  * Whether or not the zip file should be unzipped.
  */
  private static boolean shouldExtract = true;

  /**
  * Whether or not emails should be sent.
  */
  private static boolean shouldContact = false;

  /**
   * Whether or not the download folder should be cleaned up.
   */
  private static boolean shouldCleanup = true;

  /**
   * Total nominations required to run.
   */
  private static final int nominationsRequired = 15;

  private App() {
    // Empty constructor for final class
  }

  /**
  * Process elections data and send needed emails.
  *
  * @param args Java args
  */
  public static void main(final String[] args) {

    // Setup dotenv
    createDotenv();

    // Setup filepath
    createFilePath();

    // Setup the logs
    createLogs();

    // Gather latest Qualtrics data
    if (shouldDownload) {
      try {
        logger.info("Gathering Qualtrics survey data...");
        Qualtrics q = new Qualtrics();
        q.connect();
        q.download();
        q.normalizeFileName();
      } catch (Exception e) {
        logger.severe(e.getMessage());
        logger.severe("Couldn't download data... exiting!");
        System.exit(1);
      }
    }

    // Unzip and process survey data into a format useful to Java
    if (shouldExtract) {
      try {
        logger.info("Reading in Qualtrics survey data...");
        Survey s = new Survey();
        if (shouldDownload) {
          s.unzip(filePath + "/survey.zip");
        }
        s.normalizeFileName();
        responses = s.fetch(filePath + "/survey.csv");
      } catch (Exception e) {
        logger.severe(e.getMessage());
        logger.severe("Couldn't extract or read survey... exiting!");
        System.exit(1);
      }
    }

    // Create or reload the election object
    Election election = new Election();
    try {
      logger.info("Tabulating current election results...");
      // Add possible positions
      String[] positions = {"NULL",
        "President",
        "Vice President, Academic",
        "Vice President, Administration",
        "Vice President, Communications",
        "Vice President, External",
        "Vice President, Finance",
        "Vice President, Internal",
        "Vice President, Student Life",
        "SUS AMS Representative"
      };
      election.buildPositions(positions);
      // Process form responses and count the votes
      election.processNominations(responses);
      election.totalNominations();
      election.exportNominees(true);
      election.printResults(false, true);
    } catch (Exception e) {
      e.printStackTrace();
      logger.severe(e.getMessage());
      logger.severe("Couldn't process election results... exiting!");
      System.exit(1);
    }

    // Contact recipients with relevant findings
    if (shouldContact) {
      try {
        logger.info("Contacting election participants...");
        Contact c = new Contact(election);
        c.contactElections();
        c.contactNominees();
        c.contactNominators();
      } catch (Exception e) {
        logger.severe(e.getMessage());
        logger.severe("Couldn't contact participants... exiting!");
        System.exit(1);
      }
    }

    if (shouldCleanup) {
      try {
        logger.info("Saving elections data for later use...");
        election.saveNominations();
        logger.info("Cleaning up output folder...");
        cleanUp();
      } catch (Exception e) {
        logger.severe(e.getMessage());
        logger.severe("Couldn't clean up the output folder... exiting!");
        System.exit(1);
      }
    }

    logger.info("Finished!");
    System.exit(0);
  }

  /**
  * Create the logs file for the application.
  */
  public static void createLogs() {
    logger = Logger.getLogger("ElectionsLog");
    FileHandler fh;
    try {
      fh = new FileHandler(filePath + "/ElectionsLog.log");
      logger.addHandler(fh);
      SimpleFormatter simpleFormatter = new SimpleFormatter();
      fh.setFormatter(simpleFormatter);
    } catch (SecurityException e) {
      e.printStackTrace();
      System.exit(1);
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }

    logger.info("Logger setup!");
  }

  /**
   * Getter for the logger.
   *
   * @return the app's logger instance
   */
  public static Logger getLogger() {
    return logger;
  }

  /**
   * Initialize dotenv object.
   */
  public static void createDotenv() {
    String environment = System.getProperty("ENVIRONMENT");
    // getLogger().info("The working environment is " + environment);
    dotenv = Dotenv.configure()
        .filename(".env." + environment)
        .load();
  }

  /**
   * Create a user's filepath.
   */
  public static void createFilePath() {
    filePath = dotenv.get("OUT_FILEPATH");
  }

  /**
   * Getter for the filepath.
   *
   * @return a filepath.
   */
  public static String getFilePath() {
    return filePath;
  }

  /**
   * Getter for the dotenv.
   *
   * @return the app's dotenv instance
   */
  public static Dotenv getDotenv() {
    return dotenv;
  }

  public static int getNominationsRequired() {
    return nominationsRequired;
  }

  /**
  * Clean-up output directory; delete all zip or csv files.
  */
  public static void cleanUp() {
    for (File file : new File(filePath).listFiles()) {
      String extension = "";
      int i = file.getName().lastIndexOf('.');
      if (i > 0) {
        extension = file.getName().substring(i + 1);
      }
      if (!file.isDirectory() && !file.getName().equals("positions.csv") && extension.length() > 0
          && (extension.equals("zip") || extension.equals("csv"))) { // extension.equals("ser")
        file.delete();
      }
    }
  }
}

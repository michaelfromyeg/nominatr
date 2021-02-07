package qualtrics;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * App acts as a driver class for downloading, parsing, and processing current Qualtrics responses
 */
public class App {

    // Logging
    public static Logger logger;

    // Qualtrics
    private static boolean shouldDownload = true;

    // Process zip file
    private static List<Response> responses; 
    private static boolean shouldExtract = true;

    // Send emails
    private static boolean shouldContact = true;

    public static void main(String[] args) {

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
                logger.info("Unzipping and reading in Qualtrics survey data...");
                Survey s = new Survey();
                if (shouldDownload)
                    s.unzip("./qualtrics/out/survey.zip");
                s.normalizeFileName();
                responses = s.fetch("./qualtrics/out/survey.csv");
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
            String[] positions = {"NULL", "AMS Representative", "Statistics Representative", "Vantage College Representative"};
            election.buildPositions(positions);
            // Process form responses and count the votes
            election.processNominations(responses);
            election.totalNominations();
        } catch (Exception e) {
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
                logger.severe("Couldn't contact election participants... exiting!");
                System.exit(1);
            }
        }

        try {
            logger.info("Cleaning up output folder...");
            cleanUp();
        } catch (Exception e) {
            logger.severe(e.getMessage());
            logger.severe("Couldn't clean up the output folder... exiting!");
            System.exit(1);
        }

        logger.info("Finished!");
        System.exit(0);
    }

    public static void createLogs() {
        logger = Logger.getLogger("ElectionsLog");
        FileHandler fh;
        try {
            fh = new FileHandler("./qualtrics/out/ElectionsLog.log");
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

    public static void cleanUp() {
        for(File file: new File("./qualtrics/out").listFiles()) {
            String extension = "";
            int i = file.getName().lastIndexOf('.');
            if (i > 0) {
                extension = file.getName().substring(i+1);
            }
            if (!file.isDirectory() && extension.length() > 0 && (extension.equals("zip") || extension.equals("csv"))) {
                file.delete();
            }
        }
    }
}
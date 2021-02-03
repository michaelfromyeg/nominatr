package qualtrics;

import java.io.File;
import java.util.List;

/**
 * App acts as a driver class for downloading, parsing, and processing current Qualtrics responses
 */
public class App {

    // Qualtrics
    private static boolean shouldDownload = true;

    // Process zip file
    private static List<Response> responses; 
    private static boolean shouldExtract = true;

    public static void main(String[] args) {

        // Gather latest Qualtrics data
        if (shouldDownload) {
            try {
                System.out.println("Gathering Qualtrics survey data...");
                Qualtrics q = new Qualtrics();
                q.connect();
                q.download();
                q.normalizeFileName();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Couldn't download data... exiting!");
                System.exit(1);
            }
        }

        // Unzip and process survey data into a format useful to Java
        if (shouldExtract) {
            try {
                System.out.println("Unzipping and reading in Qualtrics survey data...");
                Survey s = new Survey();
                if (shouldDownload)
                    s.unzip("./qualtrics/out/survey.zip");
                s.normalizeFileName();
                responses = s.fetch("./qualtrics/out/survey.csv");
            } catch (Exception e) {
                System.out.println("Couldn't extract or read survey... exiting!");
                System.exit(1);
            }
        }

        // Create or reload the election object
        try {
            System.out.println("Tabulating current election results...");
            Election e = new Election();
            // Add possible positions
            String[] positions = {"NULL", "AMS Representative", "Statistics Representative", "Vantage College Representative"};
            e.buildPositions(positions);
            // Process form responses and count the votes
            e.processNominations(responses);
            e.totalNominations();
        } catch (Exception e) {
            System.out.println("Couldn't process election results... exiting!");
            System.exit(1);
        }

        // Contact recipients with relevant findings
        try {
            System.out.println("Contacting election participants...");
            Contact c = new Contact();
            c.contact();
        } catch (Exception e) {
            System.out.println("Couldn't contact election participants... exiting!");
            System.exit(1);
        }

        try {
            System.out.println("Cleaning up output folder...");
            cleanUp();
        } catch (Exception e) {
            System.out.println("Couldn't clean up the output folder... exiting!");
            System.exit(1);
        }

        System.out.println("Finished!");
        System.exit(0);
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
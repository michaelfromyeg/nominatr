package qualtrics;


import java.util.List;

/**
 * App acts as a driver class for downloading, parsing, and processing current Qualtrics responses
 */
public class App {

    // Qualtrics
    private static String fileName;
    private static boolean shouldDownload = false;

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
                fileName = q.download();
            } catch (Exception e) {
                System.out.println("Couldn't download data... exiting!");
                System.exit(1);
            }
        }

        // Unzip and process survey data into a format useful to Java
        if (shouldExtract) {
            try {
                System.out.println("Unzipping and reading in Qualtrics survey data...");
                Survey s = new Survey();
                // s.unzip(fileName == null ? "Test Survey.csv" : fileName);
                responses = s.fetch(fileName == null ? "./qualtrics/out/Test Survey.csv" : fileName);
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
            System.out.println("Contacting election participants... (not implemented yet)");
        } catch (Exception e) {
            System.out.println("Couldn't contact election participants... exiting!");
            System.exit(1);
        }

        System.out.println("Finished!");
        System.exit(0);
    }
}
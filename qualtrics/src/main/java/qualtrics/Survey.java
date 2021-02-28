package qualtrics;

import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
* Unzip and process survey results.
*/
public class Survey {

  public Survey() {}

  /**
   * Unzip the dowloaded results.
   *
   * @param fileName filename
   * @throws Exception if unzipping fails
   */
  public void unzip(String fileName) throws Exception {
    String source = fileName;
    String destination = Paths.get("qualtrics", "out").toAbsolutePath().toString();
    try {
      ZipFile zipFile = new ZipFile(source);
      zipFile.extractAll(destination);
    } catch (ZipException e) {
      e.printStackTrace();
      throw new Exception("Error unzipping file");
    }
  }

  /**
   * Normalize file path.
   */
  public void normalizeFileName() {
    for (File file : new File(App.getFilePath()).listFiles()) {
      String extension = "";
      int i = file.getName().lastIndexOf('.');
      if (i > 0) {
        extension = file.getName().substring(i + 1);
      }
      if (!file.isDirectory() && extension.length() > 0 && extension.equals("csv")) {
        file.renameTo(new File(App.getFilePath() + "/survey.csv"));
      }
    }
  }

  /**
   * Read in survey data.
   *
   * @param fileName filename
   * @return a list of responses
   * @throws Exception if the CSV processing fails
   */
  public List<Response> fetch(String fileName) throws Exception {
    try {
      List<Response> responses = new ArrayList<>();
      CSVReader reader = new CSVReader(new FileReader(App.getFilePath() + "/survey.csv"));
      List<String[]> r = reader.readAll();

      // Build a response map that links the question wording
      // to a machine name key, that links to a response field
      Map<String, String> responseMap = buildResponseMap();
      Map<Integer, String> indicesMap = buildResponseIndicesMap(responseMap, r.get(1));

      // Collect responses, beginning at the third row (skipping Qualtrics preamble)
      for (int i = 3; i < r.size(); i++) {
        String[] row = r.get(i);
        Response newResponse = new Response();
        int rowNumber = 0;
        for (String cell : row) {
          String fieldName = indicesMap.get(rowNumber);
          if (fileName == null) {
            continue;
            // throw new Exception("A rowNumber returned null that should not have. Exiting.");
          }
          newResponse.setByFieldNameString(fieldName, cell);
          rowNumber++;
        }
        responses.add(newResponse);
      }
      return responses;
    } catch (Exception e) {
      e.printStackTrace();
      throw new Error("Error reading CSV and creating response list");
    }
  }

  /**
   * Return an object to map survey responses in the CSV to field names in our response object.
   *
   * @return a hashmap in the form survey question key-field name value
   */
  private static Map<String, String> buildResponseMap() {
    Map<String, String> responseMap = new HashMap<String, String>();
    responseMap.put("Start Date", "startDate");
    responseMap.put("End Date", "endDate");
    responseMap.put("Response Type", "responseType");
    responseMap.put("IP Address", "ipAddress");
    responseMap.put("Progress", "progress");
    responseMap.put("Duration (in seconds)", "duration");
    responseMap.put("Finished", "finished");
    responseMap.put("Recorded Date", "recordedDate");
    responseMap.put("Response ID", "responseId");
    responseMap.put("Recipient Last Name", "lastName");
    responseMap.put("Recipient First Name", "firstName");
    responseMap.put("Recipient Email", "recipientEmail");
    responseMap.put("External Data Reference", "externalReference");
    responseMap.put("Location Latitude", "locationLatitude");
    responseMap.put("Location Longitude", "locationLongitude");
    responseMap.put("Distribution Channel", "distributionChannel");
    responseMap.put("User Language", "userLang");
    responseMap.put("nominee or a nominator", "nomineeOrNominator");
    responseMap.put("Nominee - Full name", "nomineeFullName");
    responseMap.put("Nominee - Student number", "nomineeStudentNumber");
    responseMap.put("Nominee - Email", "nomineeEmail");
    responseMap.put("Nominee - Phone number", "nomineePhoneNumber");
    responseMap.put("Nominee - Year", "nomineeYear");
    responseMap.put("Nominee - Major", "nomineeMajor");
    responseMap.put("I am aware of my nomination and am willing to run for the position of",
        "nomineeRunningFor");
    responseMap.put("Nominator - Full name", "nominatorFullName");
    responseMap.put("Nominator - Student number", "nominatorStudentNumber");
    responseMap.put("Nominator - Email", "nominatorEmail");
    responseMap.put("Nominator - Major", "nominatorMajor");
    responseMap.put("Name of person that you are nominating", "nominatorNominatingName");
    responseMap.put("Position you are nominating them for:", "nominatorNominatingPosition");
    return responseMap;
  }

  /**
   * Return a key-value pairing of row index-field string identifier.
   */
  private Map<Integer, String> buildResponseIndicesMap(Map<String, String> responseMap,
      String[] questionRow) {
    Map<Integer, String> imap = new HashMap<Integer, String>();
    int row = 0;
    for (String question : questionRow) {
      for (Entry<String, String> e : responseMap.entrySet()) {
        // System.out.println("Attempting " + question + " : " + e.getKey() + ", " + e.getValue());
        if (question.contains(e.getKey())) {
          // This Qualtrics question contains our target key string,
          // so now we know to which field to set it
          String fieldName = e.getValue();
          // System.out.println("buildResponseIndicesMap " + row + " : " + fieldName);
          imap.put(row, fieldName);
        }
      }
      row++;
    }
    return imap;
  }
}

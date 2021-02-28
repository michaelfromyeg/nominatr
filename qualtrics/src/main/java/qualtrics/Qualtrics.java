package qualtrics;

import com.qualtrics.api.sdk.ApiClient.Extensions;
import com.qualtrics.api.sdk.authentication.Authentication;
import com.qualtrics.api.sdk.authentication.AuthenticationFactory;
import com.qualtrics.api.sdk.corelibs.DataCenters;
import com.qualtrics.api.sdk.generated.users.UsersApiClient;
import com.qualtrics.api.sdk.generated.users.models.WhoAmIResponse;
import com.qualtrics.api.sdk.httpclient.QualtricsHTTPClient;
import java.io.File;
import java.nio.file.Paths;

/**
 * Controller class for Qualtrics: connects to data center and downloads survey data.
 */
public class Qualtrics {

  private static QualtricsHTTPClient qualtricsConnection = null;
  private static String API_TOKEN;
  private static String SURVEY_ID;
  private static String URL_LINK;

  /**
   * Build a basic Qualtrics connection object.
   */
  public Qualtrics() {
    // Initialize API keys, survey info
    API_TOKEN = App.getDotenv().get("QUALTRICS_API_KEY");
    SURVEY_ID = App.getDotenv().get("QUALTRICS_SURVEY_ID");
    URL_LINK = App.getDotenv().get("QUALTRICS_SURVEY_URL");
  }

  /**
   * connect to Qualtrics data center.
   */
  public void connect() {

    System.out.println("Using API token: " + API_TOKEN);
    System.out.println("Using survey ID: " + SURVEY_ID);
    System.out.println("For survey link: " + URL_LINK);

    // Create auth token
    AuthenticationFactory authFactory =
        new com.qualtrics.api.sdk.authentication.AuthenticationFactory(
            DataCenters.CA1);
    Authentication apiToken = authFactory.createApiKeyAuth(API_TOKEN);
    QualtricsHTTPClient qualtricsHttp =
        new QualtricsHTTPClient();
    qualtricsHttp.setAuthentication(apiToken);
    UsersApiClient apiClient = new UsersApiClient(qualtricsHttp);

    // Check we've connected correctly
    WhoAmIResponse response = apiClient.whoAmI();
    System.out.println(response.getResult().toString());

    // Set field
    qualtricsConnection = qualtricsHttp;
  }

  /**
   * download zip file of election results to qualtrics/out.
   *
   * @throws Exception if the download fails
   */
  public void download() throws Exception {
    qualtricsConnection.setTempFolderPath(
        Paths.get("qualtrics", "out").toAbsolutePath().toString());
    Extensions extensions = new Extensions(qualtricsConnection);
    try {
      File responses = extensions.exportResponses(SURVEY_ID, "csv");
      // System.out.println(responses.toString());
    } catch (InterruptedException e) {
      e.printStackTrace();
      throw new Exception("Unable to download file to qualtrics/out folder.");
    }
  }

  /**
   * Normalize filenames in the qualtrics/out folder.
   */
  public void normalizeFileName() {
    for (File file : new File(App.getFilePath()).listFiles()) {
      String extension = "";
      int i = file.getName().lastIndexOf('.');
      if (i > 0) {
        extension = file.getName().substring(i + 1);
      }
      if (!file.isDirectory() && extension.length() > 0 && extension.equals("zip")) {
        file.renameTo(new File(App.getFilePath() + "/survey.zip"));
      }
    }
  }
}

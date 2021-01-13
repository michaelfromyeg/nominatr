package qualtrics;

import com.qualtrics.api.sdk.ApiClient.Extensions;
import com.qualtrics.api.sdk.authentication.Authentication;
import com.qualtrics.api.sdk.authentication.AuthenticationFactory;
import com.qualtrics.api.sdk.corelibs.DataCenters;
import com.qualtrics.api.sdk.generated.users.UsersApiClient;
import com.qualtrics.api.sdk.generated.users.models.*;
import com.qualtrics.api.sdk.httpclient.QualtricsHTTPClient;

import java.io.File;
import java.nio.file.Paths;

/*
 * Controller class for Qualtrics: connects to data center and downloads survey data
 */
public class Qualtrics {
  
  private final static String API_TOKEN = "123";
  private final static String SURVEY_ID = "SV_abc";
  // private final String URL_LINK = "https://ubc.ca1.qualtrics.com/jfe/form/SV_dnBBFWNZqeLHhsh";
  private static QualtricsHTTPClient qualtricsConnection = null;

  public Qualtrics() {}

  // EFFECTS: connect to Qualtrics data center
  public void connect() {

    System.out.println("Using API token: " + API_TOKEN);
    System.out.println("Using survey ID: " + SURVEY_ID);

    // Create auth token
    AuthenticationFactory authFactory = new com.qualtrics.api.sdk.authentication.AuthenticationFactory(
            DataCenters.CA1);
    Authentication apiToken = authFactory.createApiKeyAuth(API_TOKEN);
    QualtricsHTTPClient qHTTP = new QualtricsHTTPClient();
    qHTTP.setAuthentication(apiToken);
    UsersApiClient apiClient = new UsersApiClient(qHTTP);

    // Check we've connected correctly
    WhoAmIResponse response = apiClient.whoAmI();
    System.out.println(response.getResult().toString());

    // Set field
    qualtricsConnection = qHTTP;
  }

  // EFFECTS: download zip file of election results to qualtrics/out
  public String download() throws Exception {
    qualtricsConnection.setTempFolderPath(Paths.get("qualtrics", "out").toAbsolutePath().toString());
    Extensions extensions = new Extensions(qualtricsConnection);
    try {
        File responses = extensions.exportResponses(SURVEY_ID, "csv");
        return responses.toString();
    } catch (InterruptedException e) {
        e.printStackTrace();
        throw new Exception("Unable to download file to qualtrics/out folder.");
    }
  }
         
}

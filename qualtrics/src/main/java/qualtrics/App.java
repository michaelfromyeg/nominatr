package qualtrics;

import java.io.File;
import java.nio.file.Paths;

import com.qualtrics.api.sdk.ApiClient.Extensions;
import com.qualtrics.api.sdk.authentication.Authentication;
import com.qualtrics.api.sdk.authentication.AuthenticationFactory;
import com.qualtrics.api.sdk.corelibs.DataCenters;
import com.qualtrics.api.sdk.generated.users.UsersApiClient;
import com.qualtrics.api.sdk.generated.users.models.*;
import com.qualtrics.api.sdk.httpclient.QualtricsHTTPClient;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

// import net.lingala.zip4j.ZipFile;
// import net.lingala.zip4j.exception.ZipException;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        String API_TOKEN = "";
        // Replace with your data center
        AuthenticationFactory authFactory = new com.qualtrics.api.sdk.authentication.AuthenticationFactory(
                DataCenters.CA1);
        Authentication apiToken = authFactory.createApiKeyAuth(API_TOKEN);
        QualtricsHTTPClient qHTTP = new QualtricsHTTPClient();
        qHTTP.setAuthentication(apiToken);
        UsersApiClient apiClient = new UsersApiClient(qHTTP);
        WhoAmIResponse response = apiClient.whoAmI();
        System.out.println(response.getMeta().getHttpStatus());
        System.out.println(response.getResult().toString());

        qHTTP.setTempFolderPath(Paths.get("qualtrics", "out").toAbsolutePath().toString()); // Where to download
                                                                                            // the responses to
        Extensions extensions = new Extensions(qHTTP);
        String source = null;
        try {
            File responses = extensions.exportResponses("", "csv");
            System.out.println("Responses (39): " + responses.toString());
            source = responses.toString();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        String destination = "../../../../out";   

        try {
            ZipFile zipFile = new ZipFile(source);
            zipFile.extractAll(destination);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }
}

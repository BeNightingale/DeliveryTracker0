package track.service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
@Slf4j
public class HttpCaller {

    // zwraca jsona z info o przesyłce o numerze requestParam
    public static String callHttpGetMethod(String endpointUrl, String requestParam) {
        //     log.info("Init: calling endpoint: {}, request: {}.", endpointUrl, requestParam);
        //660166696359300112430272
        try {
            Client client = Client.create();
            URI uri = new URI("https://api-shipx-pl.easypack24.net/v1/tracking/" + requestParam);
            //  URI uri = new URI(endpointUrl);// TODO dodać requested param
            WebResource webResource = client.resource(uri);
            ClientResponse clientResponse = webResource
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .accept(MediaType.APPLICATION_JSON_TYPE)
                    .get(ClientResponse.class);
            final int statusCode = clientResponse.getStatus();
            final String jsonResponse = clientResponse.getEntity(String.class);
            return jsonResponse;
        } catch (Exception ex) {
            System.out.println("problem");
            return null;
        }
    }

            //////////////////
//            final URL url = new URL(endpointUrl); // TODO dodać requested param
//            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            connection.connect();
//            int responseCode = connection.getResponseCode();
//            log.debug("Response code: {}.", responseCode);
//            final InputStream inputStream = connection.getInputStream();
//            final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//            final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            StringBuilder stringBuilder = new StringBuilder();
//            String line = null;
//            while ((line = bufferedReader.readLine()) != null) {
//                stringBuilder.append(line);
//            }
//            return stringBuilder.toString();
//        } catch (Exception ex) {
//            log.error("Error during connecting with endpoint {}.", endpointUrl);
//        }
//        return null;
  //  }
}

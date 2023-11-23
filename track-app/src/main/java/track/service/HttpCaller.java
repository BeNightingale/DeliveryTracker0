package track.service;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.LoggingFilter;
import lombok.extern.slf4j.Slf4j;
import track.web.PolishPostRequest;

import javax.ws.rs.core.MediaType;
import java.net.URI;

@Slf4j
public class HttpCaller {

    private HttpCaller() {
        // do nothing
    }

    public static final String API_KEY = "BiGwVG2XHvXY+kPwJVPA8gnKchOFsyy39Thkyb1wAiWcKLQ1ICyLiCrxj1+vVGC+kQk3k0b74qkmt5/qVIzo7lTfXhfgJ72Iyzz05wH2XZI6AgXVDciX7G2jLCdoOEM6XegPsMJChiouWS2RZuf3eOXpK5RPl8Sy4pWj+b07MLg=.Mjg0Q0NFNzM0RTBERTIwOTNFOUYxNkYxMUY1NDZGMTA0NDMwQUIyRjg4REUxMjk5NDAyMkQ0N0VCNDgwNTc1NA==.b24415d1b30a456cb8ba187b34cb6a86";
    static Gson gson = new Gson();

    // zwraca jsona z info o przesyłce o numerze requestParam
    public static String callHttpGetMethod(String endpointUrl, String requestParam) {
        log.info("Init: calling endpoint: {}, request: {}.", endpointUrl, requestParam);
        try {
            Client client = Client.create();
            URI uri = new URI(endpointUrl + requestParam);
            WebResource webResource = client.resource(uri);
            ClientResponse clientResponse = webResource
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .accept(MediaType.APPLICATION_JSON_TYPE)
                    .get(ClientResponse.class);
            final int statusCode = clientResponse.getStatus();
            log.info("Status odpowiedzi od InPosta: {}.", statusCode);
            return clientResponse.getEntity(String.class);
        } catch (Exception ex) {
            log.error("Błąd integracji z InPostem.", ex);
            return null;
        }
    }

    public static String callHttpPostMethod(String endpointUrl, PolishPostRequest request) {
        log.info("Init: calling endpoint: {}, request: {}.", endpointUrl, request.toString());
        try {
            Client client = Client.create();
            client.addFilter(new LoggingFilter(System.out));
            URI uri = new URI(endpointUrl);
            WebResource webResource = client.resource(uri);
            ClientResponse clientResponse = webResource
                    .header("API_KEY", API_KEY)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .accept(MediaType.APPLICATION_JSON_TYPE)
                    .post(ClientResponse.class, gson.toJson(request));
            log.info("location {}", clientResponse.getLocation());
            log.info("Status odpowiedzi od Poczty Polskiej: {}.", clientResponse.getStatus());
            return clientResponse.getEntity(String.class);
        } catch (Exception ex) {
            log.error("Błąd integracji z Pocztą Polską.", ex);
            return null;
        }
    }
}

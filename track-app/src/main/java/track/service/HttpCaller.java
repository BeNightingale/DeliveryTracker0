package track.service;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.LoggingFilter;
import lombok.extern.slf4j.Slf4j;
import track.model.Deliverer;
import track.web.PolishPostRequest;

import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.util.Map;
import java.util.function.BiFunction;

import static track.model.Deliverer.INPOST;
import static track.model.Deliverer.POCZTA_POLSKA;

@Slf4j
public class HttpCaller {
    public static final String INPOST_ENDPOINT_URL = "https://api-shipx-pl.easypack24.net/v1/tracking/";
    public static final String POLISH_POST_ENDPOINT_URL = "https://uss.poczta-polska.pl/uss/v1.1/tracking/checkmailex";

    public static final Map<Deliverer, String> endpointUrlsMap = Map.of(
            INPOST, INPOST_ENDPOINT_URL,
            POCZTA_POLSKA, POLISH_POST_ENDPOINT_URL
    );

    private static final BiFunction<String, String, String> inPostCaller = HttpCaller::callHttpGetMethod;
    private static final BiFunction<String, String, String> polishPostCaller = HttpCaller::callHttpPostMethod;
    public static final Map<Deliverer, BiFunction<String, String, String>> callersMap = Map.of(
            INPOST, inPostCaller,
            POCZTA_POLSKA, polishPostCaller
    );
    public static final String API_KEY = "BiGwVG2XHvXY+kPwJVPA8gnKchOFsyy39Thkyb1wAiWcKLQ1ICyLiCrxj1+vVGC+kQk3k0b74qkmt5/qVIzo7lTfXhfgJ72Iyzz05wH2XZI6AgXVDciX7G2jLCdoOEM6XegPsMJChiouWS2RZuf3eOXpK5RPl8Sy4pWj+b07MLg=.Mjg0Q0NFNzM0RTBERTIwOTNFOUYxNkYxMUY1NDZGMTA0NDMwQUIyRjg4REUxMjk5NDAyMkQ0N0VCNDgwNTc1NA==.b24415d1b30a456cb8ba187b34cb6a86";
    static Gson gson = new Gson();

    private HttpCaller() {
        // do nothing
    }

    // zwraca jsona z info o przesyłce o numerze requestParam=deliveryNumber
    public static String callHttpGetMethod(String endpointUrl, String deliveryNumber) {
        log.info("Init: calling endpoint: {}, request - deliveryNumber: {}.", endpointUrl, deliveryNumber);
        try {
            Client client = Client.create();
            final URI uri = new URI(endpointUrl + deliveryNumber);
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

    public static String callHttpPostMethod(String endpointUrl, String deliveryNumber) {
        log.info("Init: calling endpoint: {}, request - deliveryNumber: {}.", endpointUrl, deliveryNumber);
        final PolishPostRequest polishPostRequest = new PolishPostRequest("PL", deliveryNumber, true);
        try {
            Client client = Client.create();
            client.addFilter(new LoggingFilter(System.out));
            final URI uri = new URI(endpointUrl);
            WebResource webResource = client.resource(uri);
            ClientResponse clientResponse = webResource
                    .header("API_KEY", API_KEY)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .accept(MediaType.APPLICATION_JSON_TYPE)
                    .post(ClientResponse.class, gson.toJson(polishPostRequest));
            log.info("location {}", clientResponse.getLocation());
            log.info("Status odpowiedzi od Poczty Polskiej: {}.", clientResponse.getStatus());
            return clientResponse.getEntity(String.class);
        } catch (Exception ex) {
            log.error("Błąd integracji z Pocztą Polską.", ex);
            return null;
        }
    }
}

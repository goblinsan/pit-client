package client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

class GameConnectionService {

    private final String baseHost;
    private final String basePort;
    private final String username;
    private final String password;
    RestTemplate restTemplate = new RestTemplate();
    private final HttpHeaders headers = new HttpHeaders();

    GameConnectionService(String baseHost, String basePort, String username, String password) {
        this.baseHost = baseHost;
        this.basePort = basePort;
        this.username = username;
        this.password = password;

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
        messageConverters.add(jsonMessageConverter);
        restTemplate.setMessageConverters(messageConverters);

        String plainCreds = username+":"+password;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.getEncoder().encode(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        headers.add("Authorization", "Basic " + base64Creds);
    }

    boolean schedule(String state) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host(baseHost).port(basePort)
                .path("/admin/schedule/{state}").buildAndExpand(state);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, request, String.class);
        return response.getStatusCode().is2xxSuccessful() && response.getBody().equalsIgnoreCase("SCHEDULED");
    }

    boolean connect() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host(baseHost).port(basePort)
                .path("/connect/{name}").buildAndExpand(username);
        ResponseEntity<String> response = restTemplate.getForEntity(uriComponents.toUriString(),String.class);
        return response.getStatusCode().is2xxSuccessful() && response.getBody().equalsIgnoreCase("CONNECTED");
    }

    Map<String, Integer> getHand() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host(baseHost).port(basePort)
                .path("/player/hand/{name}").buildAndExpand(username);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, request, Map.class);
        return response.getBody();
    }

    List<Offer> getOffers() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host(baseHost).port(basePort)
                .path("/player/hand/{name}").buildAndExpand(username);

        return null;
    }

    List<Bid> getBids() {
        return null;
    }

    void submitOffer(Offer offer) {

    }

    Bid acceptBid(Bid acceptedBid) {
        return null;
    }

    List<Trade> getTrades() {
        return null;
    }

    Bid submitBid(Bid submittedBid) {
        return null;
    }

    String getMarketState() {
        return "OPEN";
    }
}

package client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GameConnectionService {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final String baseHost;
    private final String basePort;
    private final String username;
    private final HttpHeaders headers = new HttpHeaders();
    private RestTemplate restTemplate = new RestTemplate();


    public GameConnectionService(String baseHost, String basePort, String username, String password) {
        this.baseHost = baseHost;
        this.basePort = basePort;
        this.username = username;

        String plainCreds = username + ":" + password;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.getEncoder().encode(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        headers.add("Authorization", "Basic " + base64Creds);
    }

    public boolean schedule(String state) {
        System.out.println("    SETTING GAME STATUS TO " + state);
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host(baseHost).port(basePort)
                .path("/admin/schedule/{state}").buildAndExpand(state);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, request, String.class);
        return response.getStatusCode().is2xxSuccessful() && Objects.requireNonNull(response.getBody()).equalsIgnoreCase("SCHEDULED");
    }

    boolean connect() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host(baseHost).port(basePort)
                .path("/connect/{name}").buildAndExpand(username);
        ResponseEntity<String> response = restTemplate.getForEntity(uriComponents.toUriString(), String.class);
        boolean isconnected = response.getStatusCode().is2xxSuccessful() && Objects.requireNonNull(response.getBody()).equalsIgnoreCase("CONNECTED");
        System.out.println(username + ": sc:" + response.getStatusCode() + " body:" + response.getBody() + "  connected?" + isconnected);
        return isconnected;
    }

    Map getHand() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host(baseHost).port(basePort)
                .path("/player/hand/{name}").buildAndExpand(username);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, request, Map.class);
        return response.getBody();
    }

    List<Offer> getOffers() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host(baseHost).port(basePort)
                .path("/offers").build();
        ResponseEntity<List> response = restTemplate.getForEntity(uriComponents.toUriString(), List.class);

        List<Offer> offers = new ArrayList<>();
        for (Object serverOffer : Objects.requireNonNull(response.getBody())) {
            LinkedHashMap offerInfo = (LinkedHashMap) serverOffer;
            String offerPlayerName = getPlayerName(offerInfo, "player");
            if (offerPlayerName.equalsIgnoreCase(username)) {
                continue;
            }
            int offerAmount = (int) offerInfo.get("amount");
            offers.add(new Offer(offerPlayerName, offerAmount));
        }
        System.out.println(username + ": Offers: " + offers);
        return offers;
    }

    void submitOffer(TradeAction offer) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host(baseHost).port(basePort)
                .path("/player/offer/{name}/{amount}").buildAndExpand(username, offer.getAmount());
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, request, String.class);
        System.out.println(username + ": Submitted offer name=" + username + " amount=" + offer.getAmount() + "  response: " + response.getBody());
    }

    void removeOffer(Offer offer) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host(baseHost).port(basePort)
                .path("/player/remove-offer/{name}/{amount}").buildAndExpand(offer.getName(), offer.getAmount());
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, request, String.class);
        System.out.println(username + ": Removed offer name=" + offer.getName() + " amount=" + offer.getAmount() + "  response: " + response.getBody());
    }

    List<Bid> getBids() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host(baseHost).port(basePort)
                .path("/bids").build();
        ResponseEntity<List> response = restTemplate.getForEntity(uriComponents.toUriString(), List.class);

        List<Bid> bids = new ArrayList<>();
        for (Object serverBid : Objects.requireNonNull(response.getBody())) {
            LinkedHashMap bidInfo = (LinkedHashMap) serverBid;
            String bidRequesterName = getPlayerName(bidInfo, "requester");
            String bidOwnerName = getPlayerName(bidInfo, "owner");
            int bidAmount = (int) bidInfo.get("amount");
            Bid bid = new Bid(bidRequesterName, bidOwnerName, bidAmount, null);
            if (!bidOwnerName.equalsIgnoreCase(username)) {
//                System.out.println(username + ": discarding bid: " + bid);
                continue;
            }
            bids.add(bid);
        }
        System.out.println(username + ": got bids from server: " + bids);

        return bids;
    }

    void submitBid(TradeAction bid) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host(baseHost).port(basePort)
                .path("/player/bid/{name}/{ownerName}/{amount}/{commodity}")
                .buildAndExpand(username, bid.getOwner(), bid.getAmount(), bid.getCommodityToTrade());
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, request, String.class);
        System.out.println(username + ": Submitted bid: " + bid + "   response was: " + response.getBody());
    }

    void acceptBid(TradeAction bid) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host(baseHost).port(basePort)
                .path("/player/accept-bid/{name}/{ownerName}/{amount}/{commodity}")
                .buildAndExpand(bid.getRequester(), username, bid.getAmount(), bid.getCommodityToTrade());
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, request, String.class);
        System.out.println(username + ": Accepted bid: " + bid + "   response was: " + response.getBody());
    }

    private String getPlayerName(LinkedHashMap serverObjInfo, String field) {
        LinkedHashMap<String, String> playerInfo = (LinkedHashMap<String, String>) serverObjInfo.get(field);
        return playerInfo.get("name");
    }

    String getMarketState() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host(baseHost).port(basePort)
                .path("scheduleStrings").build();
        ResponseEntity<Map> responseMap = restTemplate.getForEntity(uriComponents.toUriString(), Map.class);
        Map<String, String> marketSchedule = responseMap.getBody();

        uriComponents = UriComponentsBuilder.newInstance().scheme("http").host(baseHost).port(basePort)
                .path("time").build();
        ResponseEntity<String> response = restTemplate.getForEntity(uriComponents.toUriString(), String.class);
        LocalTime currentMarketTime = LocalTime.parse(Objects.requireNonNull(response.getBody()), formatter);
        LocalTime marketEnd = LocalTime.parse(Objects.requireNonNull(marketSchedule).get("marketEnd"));
        LocalTime marketStart = LocalTime.parse(Objects.requireNonNull(marketSchedule).get("marketStart"));
        String status = "OPEN";
        if (currentMarketTime.isBefore(marketStart) || currentMarketTime.isAfter(marketEnd)) {
            status = "CLOSED";
        }
        return status;
    }

    boolean cornerMarket(Map<String, Integer> hand) {
        String cornerableCommodity = null;
        for (Map.Entry<String, Integer> me : hand.entrySet()) {
            if (me.getValue() == 9) {
                cornerableCommodity = me.getKey();
            }
        }
        if (null == cornerableCommodity) {
            return false;
        }

        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host(baseHost).port(basePort)
                .path("player/corner-market/{name}/{commodity}")
                .buildAndExpand(username, cornerableCommodity);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, request, String.class);
        System.out.println(username + ": Attempted to corner market on: " + cornerableCommodity + "   response was: " + response.getBody());

        return Objects.requireNonNull(response.getBody()).equalsIgnoreCase("ACCEPTED");
    }

    List<Trade> getTrades() {
        return null;
    }

}

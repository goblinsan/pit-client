package client;

import java.util.List;
import java.util.Map;

class GameConnectionService {
    boolean joinGame() {
        return true;
    }

    Map<String, Integer> getHand() {
        return null;
    }

    List<Offer> getOffers() {
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

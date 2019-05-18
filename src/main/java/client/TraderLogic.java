package client;

import java.util.List;
import java.util.Map;

public class TraderLogic {

    private final String name;
    private int wins = 0;


    public TraderLogic(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    boolean canCornerMarket(Map<String, Integer> hand) {
        // TODO: Logic for Exercise One
        for (Integer integer : hand.values()) {
            if (integer == 9) {
                return true;
            }
        }
        return false;
    }

    TargetTrade getTargetTrade(Map<String, Integer> hand) {
        TargetTrade returnValue = new TargetTrade("initial", 10);

        for (Map.Entry<String, Integer> entry : hand.entrySet()) {
            // TODO: Logic for Exercise Two
            if (entry.getValue() > 0 && entry.getValue() < returnValue.getAmount()) {
                returnValue = new TargetTrade(entry.getKey(), entry.getValue());
            }
        }

        return returnValue;
    }

    Offer prepareOffer(TargetTrade targetTrade) {
        // TODO: Logic for Exercise Three
        return new Offer(name, targetTrade.getAmount());
    }

    Bid prepareBid(Offer offer, TargetTrade targetTrade) {
        return new Bid(name, offer.getName(), offer.getAmount(), targetTrade.getType());
    }

    Bid choosePreferredBid(List<Bid> bidList, TargetTrade targetTrade) {
        for (Bid bid : bidList) {
            // TODO: Logic for Exercise Four
            if ((bid.getAmount() <= targetTrade.getAmount()) && (bid.getOwner().equalsIgnoreCase(name))) {
                Bid selectedBid = new Bid(bid.getRequester(), name, bid.getAmount(), targetTrade.getType());
                System.out.println(name + ": selected bid: " + selectedBid);
                return selectedBid;
            }
        }
        return null;
    }

    Offer getBetterOffer(Bid preferredBid, List<Offer> offers, TargetTrade targetTrade) {
        // TODO: Logic for Exercise Four and Five
        if (preferredBid != null && preferredBid.getAmount() == targetTrade.getAmount()) {
            return null;
        }

        return selectBetterOffer(offers, targetTrade, preferredBid);
    }

    private Offer selectBetterOffer(List<Offer> offers, TargetTrade targetTrade, Bid preferredBid){
        int minOfferToBeatBid = 1;
        if (preferredBid != null){
            minOfferToBeatBid = preferredBid.getAmount() + 1;
        }
        Offer preferredOffer = null;
        if (offers != null) {
            for (Offer offer : offers) {
                if (offer.getAmount() <= targetTrade.getAmount() && offer.getAmount() >= minOfferToBeatBid){
                    preferredOffer = offer;
                    minOfferToBeatBid = offer.getAmount() + 1;
                }
            }
        }
        return preferredOffer;
    }

    public void incrementWins() {
        System.out.println("\n\n       " + name + " wins!!");
        wins++;
    }

    public int getWins() {
        return wins;
    }
}

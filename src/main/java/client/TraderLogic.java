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
            if (entry.getValue() > 0 && entry.getValue() < returnValue.getAmount()) {
                returnValue = new TargetTrade(entry.getKey(), entry.getValue());
            }
        }

        return returnValue;
    }

    Offer prepareOffer(TargetTrade targetTrade) {
        return new Offer(name, targetTrade.getAmount());
    }

    Bid prepareBid(List<Offer> offers, TargetTrade targetTrade, Bid preferredBid) {

        int minOfferToBeatBid = 1;
        if (preferredBid != null) {
            minOfferToBeatBid = preferredBid.getAmount() + 1;
        }
        for (Offer offer : offers) {
            if (offer.getAmount() <= targetTrade.getAmount() && offer.getAmount() >= minOfferToBeatBid) {
                return new Bid(name, offer.getName(), offer.getAmount(), targetTrade.getType());
            }
        }
        return null;
    }

    Bid choosePreferredBid(List<Bid> bidList, TargetTrade targetTrade) {
        for (Bid bid : bidList) {
            if ((bid.getAmount() <= targetTrade.getAmount()) && (bid.getOwner().equalsIgnoreCase(name))) {
                Bid selectedBid = new Bid(bid.getRequester(), name, bid.getAmount(), targetTrade.getType());
                System.out.println(name + ": selected bid: " + selectedBid);
                return selectedBid;
            }
        }
        return null;
    }

    Bid isThereBetterOffer(Bid preferredBid, List<Offer> offers, TargetTrade targetTrade) {
        if (preferredBid != null && preferredBid.getAmount() == targetTrade.getAmount()) {
            return null;
        }

        return prepareBid(offers, targetTrade, preferredBid);
    }

    public void incrementWins() {
        System.out.println("\n\n       " + name + " wins!!");
        wins++;
    }

    public int getWins() {
        return wins;
    }
}

package client;

import java.util.List;
import java.util.Map;

public abstract class TraderLogic {
    private final String name;
    private int wins = 0;

    TraderLogic(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean canCornerMarket(Map<String, Integer> hand) {
        // TODO: Logic for Exercise One
        for (Integer integer : hand.values()) {
            if (integer == 9) {
                return true;
            }
        }
        return false;
    }

    abstract TargetTrade getTargetTrade(Map<String, Integer> hand);
    abstract Offer prepareOffer(TargetTrade targetTrade);
    abstract Bid prepareBid(Offer offer, TargetTrade targetTrade);
    abstract Bid choosePreferredBid(List<Bid> bidList, TargetTrade targetTrade);
    abstract TraderAction getTraderAction(Map<String, Integer> hand, List<Offer> offers, List<Bid> bids);

    void incrementWins() {
        System.out.println("\n\n       " + name + " wins!!");
        wins++;
    }

    public int getWins() {
        return wins;
    }

}

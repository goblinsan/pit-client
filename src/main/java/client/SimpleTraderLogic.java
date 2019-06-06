package client;

import java.util.List;
import java.util.Map;

public class SimpleTraderLogic extends TraderLogic {

    public SimpleTraderLogic(String name) {
        super(name);
    }

    public TargetTrade getTargetTrade(Map<String, Integer> hand) {
        TargetTrade returnValue = new TargetTrade("initial", 10);

        for (Map.Entry<String, Integer> entry : hand.entrySet()) {
            // TODO: Logic for Exercise Two
            if (entry.getValue() > 0 && entry.getValue() < returnValue.getAmount()) {
                returnValue = new TargetTrade(entry.getKey(), entry.getValue());
            }
        }

        return returnValue;
    }

    public Offer prepareOffer(TargetTrade targetTrade) {
        // TODO: Logic for Exercise Three
        return new Offer(getName(), targetTrade.getAmount());
    }

    public Bid prepareBid(Offer offer, TargetTrade targetTrade) {
        return new Bid(getName(), offer.getName(), offer.getAmount(), targetTrade.getType());
    }

    public Bid choosePreferredBid(List<Bid> bidList, TargetTrade targetTrade) {
        for (Bid bid : bidList) {
            // TODO: Logic for Exercise Four
            if ((bid.getAmount() <= targetTrade.getAmount()) && (bid.getOwner().equalsIgnoreCase(getName()))) {
                Bid selectedBid = new Bid(bid.getRequester(), getName(), bid.getAmount(), targetTrade.getType());
                System.out.println(getName() + ": selected bid: " + selectedBid);
                return selectedBid;
            }
        }
        return null;
    }

    @Override
    public TraderAction getTraderAction(Map<String, Integer> hand, List<Offer> offers, List<Bid> bids) {
        // Decide what you want to offer to the market
        TargetTrade targetTrade = getTargetTrade(hand);
        System.out.println(getName() + ":targetTrade: " + targetTrade.getType() + "/" + targetTrade.getAmount());

        // Select the best bid for any of our outstanding offers
        Bid preferredBid = choosePreferredBid(bids, targetTrade);

        // Check for better offers in the market
        Offer betterOffer = getBetterOffer(preferredBid, offers, targetTrade);
        if (betterOffer != null) {
            return new TraderAction(ActionType.SUBMIT_BID, prepareBid(betterOffer, targetTrade));
        } else if (preferredBid != null) {
            return new TraderAction(ActionType.ACCEPT_BID, preferredBid);
        } else {
            return new TraderAction(ActionType.SUBMIT_OFFER, prepareOffer(targetTrade));
        }

    }

    public Offer getBetterOffer(Bid preferredBid, List<Offer> offers, TargetTrade targetTrade) {
        // TODO: Logic for Exercise Four and Five
        if (preferredBid != null && preferredBid.getAmount() == targetTrade.getAmount()) {
            return null;
        }

        return selectBetterOffer(offers, targetTrade, preferredBid);
    }

    Offer selectBetterOffer(List<Offer> offers, TargetTrade targetTrade, Bid preferredBid){
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

}

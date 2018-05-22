package client;

import java.util.List;
import java.util.Map;

class GameClient {

    private final String name;
    private GameConnectionService gameConnectionService;

    GameClient(String name, GameConnectionService gameConnectionService) {
        this.name = name;
        this.gameConnectionService = gameConnectionService;
    }

    String joinGame() {
        return "ok";
    }

    boolean cornerMarket(Map<String, Integer> hand) {
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
            if (entry.getValue() < returnValue.getAmount()) {
                returnValue = new TargetTrade(entry.getKey(), entry.getValue());
            }
        }

        return returnValue;
    }

    Offer submitOffer(Map<String, Integer> hand) {
        return new Offer(name, getTargetTrade(hand).getAmount());
    }

    Bid submitBid(List<Offer> offers, Map<String, Integer> hand) {
        TargetTrade targetTrade = getTargetTrade(hand);

        for (Offer offer : offers) {
            if (offer.getAmount() == targetTrade.getAmount()) {
                return new Bid(name, offer.getName(), targetTrade.getAmount(), targetTrade.getType());
            }
        }
        return null;
    }

    Bid acceptBid(List<Bid> bidList, Map<String, Integer> hand) {
        TargetTrade targetTrade = getTargetTrade(hand);

        for (Bid aBidList : bidList) {
            if ((aBidList.getAmount() == targetTrade.getAmount()) && (aBidList.getOwner().equals(name))) {
                return new Bid(aBidList.getRequester(), name, targetTrade.getAmount(), targetTrade.getType());
            }
        }
        return null;
    }

    GameConnectionService getGameConnectionService() {
        return gameConnectionService;
    }
}
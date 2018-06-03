package client;

import java.util.List;
import java.util.Map;

public class TraderRunner implements Runnable {
    private TraderLogic traderLogic;
    private GameConnectionService gameConnectionService;
    private TraderState state = TraderState.START;

    public TraderRunner(TraderLogic traderLogic, GameConnectionService gameConnectionService) {
        this.traderLogic = traderLogic;
        this.gameConnectionService = gameConnectionService;
    }

    public TraderLogic getTraderLogic() {
        return traderLogic;
    }

    public GameConnectionService getGameConnectionService() {
        return gameConnectionService;
    }

    TraderState getState() {
        return state;
    }
    private String name() { return traderLogic.getName(); }

    @Override
    public void run() {
        while (!gameConnectionService.connect()) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Offer previousMadeOffer = null;
        String marketState = gameConnectionService.getMarketState();
        while (!marketState.equals("OPEN")) {
            marketState = gameConnectionService.getMarketState();
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (state != TraderState.WON && marketState.equals("OPEN")) {
            Map<String, Integer> hand = gameConnectionService.getHand();
            System.out.println(name() + ":Hand: " + hand);


            if (traderLogic.canCornerMarket(hand)) {
                if (gameConnectionService.cornerMarket(hand)) {
                    traderLogic.incrementWins();
                    state = TraderState.WON;
                }
            } else {
                state = TraderState.TRADING;

                TargetTrade targetTrade = traderLogic.getTargetTrade(hand);
                System.out.println(name() + ":targetTrade: " + targetTrade.getType() + "/" + targetTrade.getAmount());

                List<Bid> bids = gameConnectionService.getBids();
                Bid preferredBid = traderLogic.choosePreferredBid(bids, targetTrade);

                List<Offer> offers = gameConnectionService.getOffers();
                Bid bidToSubmit = traderLogic.isThereBetterOffer(preferredBid, offers, targetTrade);
                if (bidToSubmit != null) {
                    gameConnectionService.submitBid(bidToSubmit);
                } else if (preferredBid != null) {
                    gameConnectionService.acceptBid(preferredBid);
                } else {
//                    if (previousMadeOffer != null) {
//                        gameConnectionService.removeOffer(previousMadeOffer);
//                    }
                    Offer offer = traderLogic.prepareOffer(targetTrade);
                    previousMadeOffer = offer;
                    gameConnectionService.submitOffer(offer);
                }
            }
            marketState = gameConnectionService.getMarketState();
            if (Thread.interrupted()) {
                break;
            }
        }
    }
}

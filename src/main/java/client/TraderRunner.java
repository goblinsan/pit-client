package client;

import java.util.List;
import java.util.Map;

public class TraderRunner implements Runnable {
    private TraderLogic traderLogic;
    private GameConnectionService gameConnectionService;
    private TraderState state = TraderState.START;

    TraderRunner(TraderLogic traderLogic, GameConnectionService gameConnectionService) {
        this.traderLogic = traderLogic;
        this.gameConnectionService = gameConnectionService;
    }

    TraderLogic getTraderLogic() {
        return traderLogic;
    }

    GameConnectionService getGameConnectionService() {
        return gameConnectionService;
    }

    TraderState getState() {
        return state;
    }

    @Override
    public void run() {
        while (!gameConnectionService.joinGame()) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String marketState = gameConnectionService.getMarketState();
        while (state != TraderState.WON && marketState.equals("OPEN")) {
            Map<String, Integer> hand = gameConnectionService.getHand();
            if (traderLogic.cornerMarket(hand)) {
                state = TraderState.WON;
            } else {
                state = TraderState.TRADING;
                traderLogic.getTargetTrade(hand);
                gameConnectionService.submitOffer(traderLogic.submitOffer(hand));
                List<Bid> bids = gameConnectionService.getBids();
                Bid acceptedBid = traderLogic.acceptBid(bids, hand);
                List<Offer> offers = gameConnectionService.getOffers();
                if (traderLogic.isThereBetterOffer(acceptedBid, offers)) {
                    Bid submittedBid = traderLogic.submitBid(offers, hand);
                    gameConnectionService.submitBid(submittedBid);
                } else {
                    gameConnectionService.acceptBid(acceptedBid);
                }

                // TODO: check trades against timestamp
                gameConnectionService.getTrades();
            }
            marketState = gameConnectionService.getMarketState();
        }
    }
}

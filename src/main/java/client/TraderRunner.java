package client;

import java.time.LocalDateTime;
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

    GameConnectionService getGameConnectionService() {
        return gameConnectionService;
    }

    TraderState getState() {
        return state;
    }

    @Override
    public void run() {
        while (!gameConnectionService.connect()) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String marketState = gameConnectionService.getMarketState();
        while (!marketState.equals("OPEN")) {
            marketState = gameConnectionService.getMarketState();
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (marketState.equals("OPEN")) {
            try {
                Map<String, Integer> hand = gameConnectionService.getHand();
                System.out.println(traderLogic.getName() + ":Hand: " + hand);

                if (traderLogic.canCornerMarket(hand)) {
                    if (gameConnectionService.cornerMarket(hand)) {
                        traderLogic.incrementWins();
                        state = TraderState.WON;
                    }
                } else {
                    state = TraderState.TRADING;

                    // Get any open bids from the market
                    List<Bid> bids = gameConnectionService.getBids();

                    // Get any open offers from the market
                    List<Offer> offers = gameConnectionService.getOffers();

                    // Choose which action to take based on available bids and offers
                    TraderAction traderAction = traderLogic.getTraderAction(hand, offers, bids);

                    // Connect and execute action
                    traderAction.submitActionRequest(traderLogic.getName(), gameConnectionService);

                }
                marketState = gameConnectionService.getMarketState();
            } catch (Exception e) {
                System.err.println(traderLogic.getName() + " got exception at " + LocalDateTime.now());
                e.printStackTrace();
            }
        }
    }
}

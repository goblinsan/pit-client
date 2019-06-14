package client;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class TraderAction {
    ActionType actionType;
    TradeAction tradeAction;

    void submitActionRequest(String player, GameConnectionService gameConnectionService) {
        if (getActionType() == ActionType.SUBMIT_BID) {
            System.out.println(player + " Submitting Bid for the " + getTradeAction().getAmount() + " cards offered by " + getTradeAction().getOwner());
            gameConnectionService.submitBid(getTradeAction());
        } else if (getActionType() == ActionType.ACCEPT_BID) {
            System.out.println(player + " Accepting Bid from " + getTradeAction().getRequester() + " for " + getTradeAction().getAmount() + " cards" );
            gameConnectionService.acceptBid(getTradeAction());
        } else if (getActionType() == ActionType.SUBMIT_OFFER) {
            System.out.println(player + " Submitting Offer for " + getTradeAction().getAmount() + " cards");
            gameConnectionService.submitOffer(getTradeAction());
        } else {
            System.out.println(player + " couldn't decide what to do!");
        }
    }
}

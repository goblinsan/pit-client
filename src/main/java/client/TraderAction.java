package client;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class TraderAction {
    ActionType actionType;
    TradeAction tradeAction;

    public void submitActionRequest(String player, GameConnectionService gameConnectionService) {
        if (getActionType() == ActionType.SUBMIT_BID) {
            gameConnectionService.submitBid(getTradeAction());
        } else if (getActionType() == ActionType.ACCEPT_BID) {
            gameConnectionService.acceptBid(getTradeAction());
        } else if (getActionType() == ActionType.SUBMIT_OFFER) {
            gameConnectionService.submitOffer(getTradeAction());
        } else {
            System.out.println(player + " couldn't decide what to do!");
        }
    }
}

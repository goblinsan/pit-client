package features;

import client.ActionType;
import client.TradeAction;
import client.TraderAction;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
class TraderActionInput {
    ActionType actionType;
    String requester;
    String owner;
    int amount;
    String commodityToTrade;

    TraderAction toTraderAction(){
        return new TraderAction(actionType, new TradeAction() {
            @Override
            public int getAmount() {
                return amount;
            }

            @Override
            public String getRequester() {
                return requester.equalsIgnoreCase("null") ? null : requester;
            }

            @Override
            public String getOwner() {
                return owner.equalsIgnoreCase("null") ? null : owner;
            }

            @Override
            public String getCommodityToTrade() {
                return commodityToTrade.equalsIgnoreCase("null") ? null : commodityToTrade;
            }
        });
    }
}

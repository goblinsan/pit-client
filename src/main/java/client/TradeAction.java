package client;

public interface TradeAction {
    int getAmount();

    default String getRequester(){
        return null;
    }

    default String getOwner(){
        return null;
    }

    default String getCommodityToTrade(){
        return null;
    }
}

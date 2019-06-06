package client;

public class Bid implements TradeAction {
    private final String requester;
    private final String owner;
    private final int amount;
    private final String commodityToTrade;

    Bid(String requester, String owner, int amount, String commodityToTrade) {
        this.requester = requester;
        this.owner = owner;
        this.amount = amount;
        this.commodityToTrade = commodityToTrade;
    }

    public String getRequester() {
        return requester;
    }

    public String getOwner() {
        return owner;
    }

    public int getAmount() {
        return amount;
    }

    public String getCommodityToTrade() {
        return commodityToTrade;
    }

    @Override
    public String toString() {
        return requester + ':' +
                owner + ':' +
                amount + ':' +
                commodityToTrade;
    }
}

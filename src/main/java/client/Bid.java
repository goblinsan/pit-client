package client;

class Bid {
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

    String getRequester() {
        return requester;
    }

    String getOwner() {
        return owner;
    }

    int getAmount() {
        return amount;
    }

    String getCommodityToTrade() {
        return commodityToTrade;
    }
}

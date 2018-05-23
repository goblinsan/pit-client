package client;

public class Trade {
    private String requester;
    private String owner;
    private int amount;

    public Trade(String requester, String owner, int amount) {
        this.requester = requester;
        this.owner = owner;
        this.amount = amount;
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
}

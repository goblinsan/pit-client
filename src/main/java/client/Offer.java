package client;

class Offer implements TradeAction {
    private final String name;
    private final int amount;

    Offer(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return name + ":" + amount;
    }
}

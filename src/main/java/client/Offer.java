package client;

class Offer {
    private final String name;
    private final int amount;

    Offer(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    String getName() {
        return name;
    }

    int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return name + ":" + amount;
    }
}

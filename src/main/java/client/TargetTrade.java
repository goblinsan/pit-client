package client;

class TargetTrade {
    private final String type;
    private final int amount;

    TargetTrade(String type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    String getType() {
        return type;
    }

    int getAmount() {
        return amount;
    }
}

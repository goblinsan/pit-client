package client;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class TargetTrade {
    private final String type;
    private final int amount;

    public TargetTrade(String type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }
}

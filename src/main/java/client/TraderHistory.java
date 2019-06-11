package client;

import lombok.Data;

import java.util.Map;

@Data
public
class TraderHistory {
    private int rounds = 1;
    private int consecutiveTradeAmountAttempts;
    private int lastSwitch;
    private Map<String, Integer> lastHand;
    private TargetTrade lastTradeAttempt;

    void incrementRounds() {
        rounds++;
    }

    void incremetTradeAttempts() {
        consecutiveTradeAmountAttempts++;
    }

    private void incremetLastSwitch() {
        lastSwitch++;
    }
}

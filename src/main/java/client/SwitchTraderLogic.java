package client;

import java.util.Map;

public class SwitchTraderLogic extends SimpleTraderLogic {

    static final int MAX_SWITCH = 15;
    static final int MAX_CONSECUTIVE = 6;
    private final TraderHistory traderHistory;
    private final Randomize randomTrade;

    public SwitchTraderLogic(String name, TraderHistory traderHistory, Randomize randomTrade) {
        super(name);
        this.traderHistory = traderHistory;
        this.randomTrade = randomTrade;
    }

    public SwitchTraderLogic(String name) {
        this(name, new TraderHistory(), RandomTrade.INSTANCE);
    }

    public TargetTrade getTargetTrade(Map<String, Integer> hand) {
        TargetTrade targetTrade;
        if (isDeadlocked()) {
            targetTrade = randomTrade.getRandomTargetTrade(hand);
        } else if (trySwitchingCommodity(hand)) {
            System.out.println(getName() + " - Switch Round ##################################################################");
            targetTrade = new TargetTrade("initial", 0);
            for (Map.Entry<String, Integer> entry : hand.entrySet()) {
                if (entry.getValue() < 10 && entry.getValue() > targetTrade.getAmount()) {
                    targetTrade = new TargetTrade(entry.getKey(), entry.getValue());
                }
            }
            targetTrade = new TargetTrade(targetTrade.getType(), targetTrade.getAmount() / 2);
        } else {
            targetTrade = new TargetTrade("initial", 10);
            for (Map.Entry<String, Integer> entry : hand.entrySet()) {
                if (entry.getValue() > 0 && entry.getValue() < targetTrade.getAmount()) {
                    targetTrade = new TargetTrade(entry.getKey(), entry.getValue());
                }
            }
        }

        traderHistory.incrementRounds();
        traderHistory.setLastHand(hand);
        compareTrade(targetTrade);
        return targetTrade;
    }

    private void compareTrade(TargetTrade targetTrade) {
        if (traderHistory.getLastTradeAttempt() != null) {
            if (traderHistory.getLastTradeAttempt().getAmount() == targetTrade.getAmount()) {
                traderHistory.incremetTradeAttempts();
            } else {
                traderHistory.setConsecutiveTradeAmountAttempts(0);
            }
        }
        traderHistory.setLastTradeAttempt(targetTrade);
    }

    private boolean trySwitchingCommodity(Map<String, Integer> hand) {
        boolean foolishConsistency = traderHistory.getRounds() >= traderHistory.getLastSwitch() + MAX_SWITCH;
        boolean sameHand = traderHistory.getLastHand() != null && traderHistory.getLastHand().equals(hand);
        if (foolishConsistency && sameHand) {
            return true;
        } else if (foolishConsistency) {
            traderHistory.setLastSwitch(traderHistory.getRounds());
        }
        return false;
    }

    private boolean isDeadlocked() {
        return traderHistory.getConsecutiveTradeAmountAttempts() > MAX_CONSECUTIVE;
    }

}

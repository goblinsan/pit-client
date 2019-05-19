package client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class SwitchTraderLogic extends SimpleTraderLogic {

    private int lastSwitch;
    private int rounds = 1;
    private Map<String, Integer> lastHand = null;
    private TargetTrade lastTradeAttempt = null;
    private int consecutiveTradeAmountAttempts = 0;

    public SwitchTraderLogic(String name) {
        super(name);
    }

    public TargetTrade getTargetTrade(Map<String, Integer> hand) {
        TargetTrade targetTrade;
        if (isDeadlocked()){
            int randomEntry = getRandomIntInRange(0, hand.size());
            List<Map.Entry<String, Integer>> entryList = new ArrayList<>(hand.entrySet());
            Map.Entry<String, Integer> chosenEntry = entryList.get(randomEntry);
            int totalHeld = chosenEntry.getValue();
            int targetValue = totalHeld < 2 ? totalHeld : getRandomIntInRange(1, totalHeld + 1);

            targetTrade = new TargetTrade(chosenEntry.getKey(), targetValue);
        }else if (trySwitchingCommodity(hand)) {
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

        rounds++;
        lastHand = hand;
        compareTrade(targetTrade);
        return targetTrade;
    }

    private int getRandomIntInRange(int lower, int upper) {
        return ThreadLocalRandom.current().nextInt(lower, upper);
    }

    private void compareTrade(TargetTrade targetTrade) {
        if (lastTradeAttempt != null){
            if (lastTradeAttempt.getAmount() == targetTrade.getAmount()){
                consecutiveTradeAmountAttempts++;
            } else {
                consecutiveTradeAmountAttempts = 0;
            }
        }
        lastTradeAttempt = targetTrade;
    }

    private boolean trySwitchingCommodity(Map<String, Integer> hand) {
        boolean foolishConsistency = rounds >= lastSwitch + 15;
        boolean sameHand = lastHand != null && lastHand.equals(hand);
        if (foolishConsistency && sameHand) {
            return true;
        } else if (foolishConsistency) {
            lastSwitch = rounds;
        }
        return false;
    }

    private boolean isDeadlocked() {
        if(consecutiveTradeAmountAttempts > 6) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Deadlock !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            return true;
        }
        return false;
    }

}

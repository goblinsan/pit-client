package client;

import java.util.Map;

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
            targetTrade = LogicUtil.getRandomTargetTrade(hand);
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

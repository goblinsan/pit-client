package client;

import java.util.Map;

public class RandomTraderLogic extends SimpleTraderLogic {

    private final RandomTrade randomTrade;

    public RandomTraderLogic(String name, RandomTrade randomTrade) {
        super(name);
        this.randomTrade = randomTrade;
    }

    public TargetTrade getTargetTrade(Map<String, Integer> hand) {
        return randomTrade.getRandomTargetTrade(hand);
    }

}

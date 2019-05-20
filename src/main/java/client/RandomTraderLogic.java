package client;

import java.util.Map;

public class RandomTraderLogic extends SimpleTraderLogic {

    public RandomTraderLogic(String name) {
        super(name);
    }

    public TargetTrade getTargetTrade(Map<String, Integer> hand) {
        return LogicUtil.getRandomTargetTrade(hand);
    }

}

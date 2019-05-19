package client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class RandomTraderLogic extends SimpleTraderLogic {

    public RandomTraderLogic(String name) {
        super(name);
    }

    public TargetTrade getTargetTrade(Map<String, Integer> hand) {
        int randomEntry = getRandomIntInRange(0, hand.size());
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(hand.entrySet());
        Map.Entry<String, Integer> chosenEntry = entryList.get(randomEntry);
        int totalHeld = chosenEntry.getValue();
        int targetValue = totalHeld < 2 ? totalHeld : getRandomIntInRange(1, totalHeld + 1);

        return new TargetTrade(chosenEntry.getKey(), targetValue);
    }

    private int getRandomIntInRange(int lower, int upper) {
        return ThreadLocalRandom.current().nextInt(lower, upper);
    }

}

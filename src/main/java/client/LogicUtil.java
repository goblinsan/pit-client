package client;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class LogicUtil {

    public static TargetTrade getRandomTargetTrade(Map<String, Integer> hand){
        List<Map.Entry<String, Integer>>  nonEmptyEntries = hand.entrySet().stream()
                .filter(stringIntegerEntry -> stringIntegerEntry.getValue() > 0)
                .collect(Collectors.toList());
        Map.Entry<String, Integer> chosenEntry = nonEmptyEntries.get(getRandomIntInRange(0, nonEmptyEntries.size()));
        int totalHeld = chosenEntry.getValue();
        int targetValue = totalHeld == 1 ? totalHeld : getRandomIntInRange(1, totalHeld + 1);

        return new TargetTrade(chosenEntry.getKey(), targetValue);
    }

    private static int getRandomIntInRange(int lower, int upper) {
        return ThreadLocalRandom.current().nextInt(lower, upper);
    }
}

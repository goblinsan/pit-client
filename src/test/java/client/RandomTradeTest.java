package client;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static client.Commodity.*;
import static org.junit.Assert.assertTrue;

public class RandomTradeTest {

    @Test
    public void getRandomTargetTrade() {
        Map<String, Integer> testHand = new HashMap<>();
        testHand.put(GOLD.name(), 3);
        testHand.put(OIL.name(), 3);
        testHand.put(RICE.name(), 3);

        Map<TargetTrade, Integer> targetTradeProfile = new HashMap<>();
        targetTradeProfile.put(new TargetTrade(GOLD.name(), 1), 0);
        targetTradeProfile.put(new TargetTrade(GOLD.name(), 2), 0);
        targetTradeProfile.put(new TargetTrade(GOLD.name(), 3), 0);
        targetTradeProfile.put(new TargetTrade(OIL.name(), 1), 0);
        targetTradeProfile.put(new TargetTrade(OIL.name(), 2), 0);
        targetTradeProfile.put(new TargetTrade(OIL.name(), 3), 0);
        targetTradeProfile.put(new TargetTrade(RICE.name(), 1), 0);
        targetTradeProfile.put(new TargetTrade(RICE.name(), 2), 0);
        targetTradeProfile.put(new TargetTrade(RICE.name(), 3), 0);

        int testRuns = 10000;
        for (int i = 0; i < testRuns; i++) {
            TargetTrade targetTrade = RandomTrade.INSTANCE.getRandomTargetTrade(testHand);
            int currentCount = targetTradeProfile.get(targetTrade);
            targetTradeProfile.put(targetTrade, currentCount + 1);
        }

        double expectedDistribution = testRuns / targetTradeProfile.size();
        double deviation = expectedDistribution * .10;
        double lowerBound = expectedDistribution - deviation;
        double upperBound = expectedDistribution + deviation;

        targetTradeProfile.forEach((targetTrade, integer) ->
                assertTrue(String.format("targetTrade for %s amount %d was %d",
                        targetTrade.getType(), targetTrade.getAmount(), integer),
                        integer > lowerBound && integer < upperBound));
    }

}
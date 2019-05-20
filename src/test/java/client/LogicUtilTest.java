package client;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.Map;

public class LogicUtilTest {

    @Test
    public void getRandomTargetTrade() {
        Map<String, Integer> testHand = new HashMap<>();
        testHand.put("GOLD", 3);
        testHand.put("OIL", 3);
        testHand.put("CORN", 3);

        Map<TargetTrade, Integer> targetTradeProfile = new HashMap<>();
        targetTradeProfile.put(new TargetTrade("GOLD", 1), 0);
        targetTradeProfile.put(new TargetTrade("GOLD", 2), 0);
        targetTradeProfile.put(new TargetTrade("GOLD", 3), 0);
        targetTradeProfile.put(new TargetTrade("OIL", 1), 0);
        targetTradeProfile.put(new TargetTrade("OIL", 2), 0);
        targetTradeProfile.put(new TargetTrade("OIL", 3), 0);
        targetTradeProfile.put(new TargetTrade("CORN", 1), 0);
        targetTradeProfile.put(new TargetTrade("CORN", 2), 0);
        targetTradeProfile.put(new TargetTrade("CORN", 3), 0);

        int testRuns = 10000;
        for (int i = 0; i < testRuns; i++) {
            TargetTrade targetTrade = LogicUtil.getRandomTargetTrade(testHand);
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
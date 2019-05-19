package client;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import java.util.HashMap;
import java.util.Map;

public class ExerciseTwoTest {
    // Test Target Trade Logic

    private SimpleTraderLogic testObject;

    @Before
    public void setUp() {
        testObject = new SimpleTraderLogic("Will");
    }

    @Test
    public void targetShouldBe2() {
        Map<String, Integer> testHand = new HashMap<>();
        testHand.put("GOLD", 4);
        testHand.put("OIL", 3);
        testHand.put("CORN", 2);

        TargetTrade expectedTargetTrade = new TargetTrade("CORN", 2);
        assertEquals(expectedTargetTrade.getType(), testObject.getTargetTrade(testHand).getType());
        assertEquals(expectedTargetTrade.getAmount(), testObject.getTargetTrade(testHand).getAmount());
    }

    @Test
    public void targetShouldBe4() {
        Map<String, Integer> testHand = new HashMap<>();
        testHand.put("GOLD", 5);
        testHand.put("OIL", 4);

        TargetTrade expectedTargetTrade = new TargetTrade("OIL", 4);

        assertEquals(expectedTargetTrade.getType(), testObject.getTargetTrade(testHand).getType());
        assertEquals(expectedTargetTrade.getAmount(), testObject.getTargetTrade(testHand).getAmount());
    }
}

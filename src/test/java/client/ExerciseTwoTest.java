package client;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static client.Commodity.*;
import static org.junit.Assert.assertEquals;

public class ExerciseTwoTest {
    public static final String TRADER_NAME = "Will";
    // Test Target Trade Logic

    private SimpleTraderLogic testObject;

    @Before
    public void setUp() {
        testObject = new SimpleTraderLogic(TRADER_NAME);
    }

    @Test
    public void targetShouldBe2() {
        Map<String, Integer> testHand = new HashMap<>();
        testHand.put(GOLD.name(), 4);
        testHand.put(OIL.name(), 3);
        testHand.put(RICE.name(), 2);

        TargetTrade expectedTargetTrade = new TargetTrade(RICE.name(), 2);
        assertEquals(expectedTargetTrade.getType(), testObject.getTargetTrade(testHand).getType());
        assertEquals(expectedTargetTrade.getAmount(), testObject.getTargetTrade(testHand).getAmount());
    }

    @Test
    public void targetShouldChooseTheFirstIfEqual() {
        Map<String, Integer> testHand = new LinkedHashMap<>();
        testHand.put(GOLD.name(), 4);
        testHand.put(OIL.name(), 3);
        testHand.put(RICE.name(), 1);
        testHand.put(CATTLE.name(), 1);

        TargetTrade expectedTargetTrade = new TargetTrade(RICE.name(), 1);
        assertEquals(expectedTargetTrade.getType(), testObject.getTargetTrade(testHand).getType());
        assertEquals(expectedTargetTrade.getAmount(), testObject.getTargetTrade(testHand).getAmount());
    }

    @Test
    public void targetShouldBe4() {
        Map<String, Integer> testHand = new HashMap<>();
        testHand.put(GOLD.name(), 5);
        testHand.put(OIL.name(), 4);

        TargetTrade expectedTargetTrade = new TargetTrade(OIL.name(), 4);

        assertEquals(expectedTargetTrade.getType(), testObject.getTargetTrade(testHand).getType());
        assertEquals(expectedTargetTrade.getAmount(), testObject.getTargetTrade(testHand).getAmount());
    }

    @Test
    public void targetShouldBeGreaterThanZero() {
        Map<String, Integer> testHand = new HashMap<>();
        testHand.put(GOLD.name(), 5);
        testHand.put(OIL.name(), 4);
        testHand.put(PLATINUM.name(), 0);
        testHand.put(CATTLE.name(), -4);

        TargetTrade expectedTargetTrade = new TargetTrade(OIL.name(), 4);

        assertEquals(expectedTargetTrade.getType(), testObject.getTargetTrade(testHand).getType());
        assertEquals(expectedTargetTrade.getAmount(), testObject.getTargetTrade(testHand).getAmount());
    }
}

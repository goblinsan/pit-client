package client;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static client.Commodity.GOLD;
import static client.Commodity.OIL;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExerciseOneTest {
    // Test Win Conditions

    private SimpleTraderLogic testObject;

    @Before
    public void setUp() {
        testObject = new SimpleTraderLogic("Will");
    }

    @Test
    public void shouldWin() {
        Map<String, Integer> testHand = new HashMap<>();
        testHand.put(GOLD.name(), 9);

        assertTrue(testObject.canCornerMarket(testHand));
    }

    @Test
    public void shouldNotWin() {
        Map<String, Integer> testHand = new HashMap<>();
        testHand.put(GOLD.name(), 5);
        testHand.put(OIL.name(), 4);

        assertFalse(testObject.canCornerMarket(testHand));
    }
}

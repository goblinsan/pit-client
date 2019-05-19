package client;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.Map;

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
        testHand.put("GOLD", 9);

        assertTrue(testObject.canCornerMarket(testHand));
    }

    @Test
    public void shouldNotWin() {
        Map<String, Integer> testHand = new HashMap<>();
        testHand.put("GOLD", 5);
        testHand.put("OIL", 4);

        assertFalse(testObject.canCornerMarket(testHand));
    }
}

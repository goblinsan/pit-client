package client;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExerciseOne {
    // Test Win Conditions

    private TraderLogic testObject;

    @Before
    public void setUp() {
        testObject = new TraderLogic("Will");
    }

    @Test
    public void shouldWin() {
        Map<String, Integer> testHand = new HashMap<>();
        testHand.put("GOLD", 9);

        assertTrue(testObject.cornerMarket(testHand));
    }

    @Test
    public void shouldNotWin() {
        Map<String, Integer> testHand = new HashMap<>();
        testHand.put("GOLD", 5);
        testHand.put("OIL", 4);

        assertFalse(testObject.cornerMarket(testHand));
    }
}

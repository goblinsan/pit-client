package client;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExerciseOne {
    // Test Win Conditions

    private GameConnectionService gameConnectionService;
    private GameClient testObject;

    @Before
    public void setUp() {
        gameConnectionService = Mockito.mock(GameConnectionService.class);
        testObject = new GameClient("Will", gameConnectionService);
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

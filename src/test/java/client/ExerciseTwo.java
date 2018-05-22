package client;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ExerciseTwo {
    // Test Target Trade Logic

    private GameConnectionService gameConnectionService;
    private GameClient testObject;

    @Before
    public void setUp() {
        gameConnectionService = Mockito.mock(GameConnectionService.class);
        testObject = new GameClient("Will", gameConnectionService);
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

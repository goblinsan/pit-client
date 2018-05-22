package client;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ExerciseThree {
    // Test offer logic

    private GameConnectionService gameConnectionService;
    private GameClient testObject;

    @Before
    public void setUp() {
        gameConnectionService = Mockito.mock(GameConnectionService.class);
        testObject = new GameClient("Will", gameConnectionService);
    }

    @Test
    public void shouldOffer2() {
        Map<String, Integer> testHand = new HashMap<>();
        testHand.put("GOLD", 4);
        testHand.put("OIL", 3);
        testHand.put("CORN", 2);

        Offer expectedOffer = new Offer("Will", 2);
        Offer actualOffer = testObject.submitOffer(testHand);

        assertEquals(expectedOffer.getAmount(), actualOffer.getAmount());
        assertEquals(expectedOffer.getName(), actualOffer.getName());
    }

    @Test
    public void shouldOffer4() {
        Map<String, Integer> testHand = new HashMap<>();
        testHand.put("GOLD", 5);
        testHand.put("OIL", 4);

        Offer expectedOffer = new Offer("Will", 4);
        Offer actualOffer = testObject.submitOffer(testHand);

        assertEquals(expectedOffer.getAmount(), actualOffer.getAmount());
        assertEquals(expectedOffer.getName(), actualOffer.getName());
    }
}

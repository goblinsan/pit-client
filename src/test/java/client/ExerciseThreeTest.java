package client;

import org.junit.Before;
import org.junit.Test;

import static client.Commodity.OIL;
import static org.junit.Assert.assertEquals;

public class ExerciseThreeTest {
    // Test offer logic

    private SimpleTraderLogic testObject;

    @Before
    public void setUp() {
        testObject = new SimpleTraderLogic("Will");
    }

    @Test
    public void shouldOffer2() {
        TargetTrade targetTrade = new TargetTrade(OIL.name(), 2);

        Offer expectedOffer = new Offer("Will", 2);
        Offer actualOffer = testObject.prepareOffer(targetTrade);

        assertEquals(expectedOffer.getAmount(), actualOffer.getAmount());
        assertEquals(expectedOffer.getName(), actualOffer.getName());
    }

    @Test
    public void shouldOffer4() {
        TargetTrade targetTrade = new TargetTrade(OIL.name(), 4);

        Offer expectedOffer = new Offer("Will", 4);
        Offer actualOffer = testObject.prepareOffer(targetTrade);

        assertEquals(expectedOffer.getAmount(), actualOffer.getAmount());
        assertEquals(expectedOffer.getName(), actualOffer.getName());
    }
}

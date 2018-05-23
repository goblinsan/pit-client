package client;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExerciseFive {
    // Test choose between accepting bid or making a bid

    private TraderLogic testObject;

    @Before
    public void setUp() {
        testObject = new TraderLogic("Will");
    }

    @Test
    public void shouldAcceptAnyBid() {
        Bid bidToAccept = new Bid("anyRequester", "anyOwner", 3, "OIL");
        List<Offer> offers = new ArrayList<>();
        assertFalse(testObject.isThereBetterOffer(bidToAccept, offers));
    }

    @Test
    public void ifNoBidReturnTrueForBetterOffer() {
        assertTrue(testObject.isThereBetterOffer(null, null));
    }
}

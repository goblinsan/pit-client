package client;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;

public class ExerciseFourTest {
    // Test bid logic

    private SimpleTraderLogic testObject;

    @Before
    public void setUp() {
        testObject = new SimpleTraderLogic("Will");
    }

    @Test
    public void shouldBid4ForLuke() {
        // the TraderLogic should look at the List of Offers
        // and select a player offering the same number as your Target Amount

        // This is your hand, you want to give Luke your 4 Oil
        TargetTrade testTrade = new TargetTrade("OIL", 4);

        // This is the current list of Offers in the market.
        List<Offer> testOfferList = new ArrayList<>();
        testOfferList.add(new Offer("James", 3));
        testOfferList.add(new Offer("Luke", 4));
        testOfferList.add(new Offer("Mason", 2));

        // Bid format : Requester[you], Owner[other player], amount, Commodity[type you will trade]
        // The method prepareBid should return an Bid equal to this expectedBid object
        Bid expectedBid = new Bid("Will", "Luke", 4, "OIL");

        Offer offer = testObject.getBetterOffer(null, testOfferList, testTrade);
        Bid actualBid = testObject.prepareBid(offer, testTrade);

        // comparisons for test
        assertEquals(expectedBid.getOwner(), actualBid.getOwner());
        assertEquals(expectedBid.getAmount(), actualBid.getAmount());
        assertEquals(expectedBid.getCommodityToTrade(), actualBid.getCommodityToTrade());
        assertEquals(expectedBid.getRequester(), actualBid.getRequester());
    }

    @Test
    public void choosePreferredBidFromMultipleBids() {
        TargetTrade testTrade = new TargetTrade("OIL", 4);

        List<Bid> testBidList = new ArrayList<>();
        testBidList.add(new Bid("Luke", "Will", 4, null));
        testBidList.add(new Bid("James", "Will", 2, null));
        testBidList.add(new Bid("Mason", "Will", 4, null));

        Bid expectedBid = new Bid("Luke", "Will", 4, null);

        Bid actualBid = testObject.choosePreferredBid(testBidList, testTrade);

        // comparisons for test
        assertEquals(expectedBid.getOwner(), actualBid.getOwner());
        assertEquals(expectedBid.getAmount(), actualBid.getAmount());
        assertEquals(expectedBid.getRequester(), actualBid.getRequester());
    }
}

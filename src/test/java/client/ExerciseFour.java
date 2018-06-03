package client;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ExerciseFour {
    // Test bid logic

    private TraderLogic testObject;

    @Before
    public void setUp() {
        testObject = new TraderLogic("Will");
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

        // compare this actualBid to the expectedBid
        Bid actualBid = testObject.prepareBid(testOfferList, testTrade, null);

        // comparisons for test
        assertEquals(expectedBid.getOwner(), actualBid.getOwner());
        assertEquals(expectedBid.getAmount(), actualBid.getAmount());
        assertEquals(expectedBid.getCommodityToTrade(), actualBid.getCommodityToTrade());
        assertEquals(expectedBid.getRequester(), actualBid.getRequester());
    }

    @Test
    public void acceptBidTest() {
        TargetTrade testTrade = new TargetTrade("OIL", 4);

        List<Bid> testBidList = new ArrayList<>();
        testBidList.add(new Bid("Luke", "Will", 4, "OIL"));
        testBidList.add(new Bid("James", "Will", 2, "OIL"));
        testBidList.add(new Bid("Mason", "Luke", 4, "GOLD"));

        Bid expectedBid = new Bid("Luke", "Will", 4, "OIL");

        Bid actualBid = testObject.choosePreferredBid(testBidList, testTrade);

        // comparisons for test
        assertEquals(expectedBid.getOwner(), actualBid.getOwner());
        assertEquals(expectedBid.getAmount(), actualBid.getAmount());
        assertEquals(expectedBid.getCommodityToTrade(), actualBid.getCommodityToTrade());
        assertEquals(expectedBid.getRequester(), actualBid.getRequester());
    }
}

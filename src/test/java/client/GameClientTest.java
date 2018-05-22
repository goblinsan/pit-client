package client;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class GameClientTest {

    private final GameClient testObject = new GameClient("Will");

    @Test
    public void clientCanJoinGame() {
        assertEquals("ok", testObject.joinGame());
    }

    @Test
    public void shouldWin() {
        Map<String,Integer> testHand = new HashMap<>();
        testHand.put("GOLD",9);
        assertTrue(testObject.cornerMarket(testHand));
    }

    @Test
    public void shouldNotWin() {
        Map<String,Integer> testHand = new HashMap<>();

        assertFalse(testObject.cornerMarket(testHand));
    }

    @Test
    public void targetShouldBe2() {
        Map<String,Integer> testHand = new HashMap<>();
        testHand.put("GOLD",4);
        testHand.put("OIL",3);
        testHand.put("CORN",2);

        TargetTrade expectedTargetTrade = new TargetTrade("CORN",2);
        assertEquals(expectedTargetTrade.getType(), testObject.getTargetTrade(testHand).getType());
        assertEquals(expectedTargetTrade.getAmount(), testObject.getTargetTrade(testHand).getAmount());
    }

    @Test
    public void targetShouldBe4() {
        Map<String,Integer> testHand = new HashMap<>();
        testHand.put("GOLD",5);
        testHand.put("OIL",4);

        TargetTrade expectedTargetTrade = new TargetTrade("OIL",4);

        assertEquals(expectedTargetTrade.getType(), testObject.getTargetTrade(testHand).getType());
        assertEquals(expectedTargetTrade.getAmount(), testObject.getTargetTrade(testHand).getAmount());
    }

    @Test
    public void shouldOffer2() {
        Map<String,Integer> testHand = new HashMap<>();
        testHand.put("GOLD",4);
        testHand.put("OIL",3);
        testHand.put("CORN",2);

        Offer expectedOffer = new Offer("Will", 2);
        Offer actualOffer = testObject.submitOffer(testHand);

        assertEquals(expectedOffer.getAmount(), actualOffer.getAmount());
        assertEquals(expectedOffer.getName(), actualOffer.getName());
    }

    @Test
    public void shouldOffer4() {
        Map<String,Integer> testHand = new HashMap<>();
        testHand.put("GOLD",5);
        testHand.put("OIL",4);

        Offer expectedOffer = new Offer("Will", 4);
        Offer actualOffer = testObject.submitOffer(testHand);

        assertEquals(expectedOffer.getAmount(), actualOffer.getAmount());
        assertEquals(expectedOffer.getName(), actualOffer.getName());
    }

    @Test
    public void shouldBid4ForLuke() {
        // the GameClient should look at the List of Offers
        // and select a player offering the same number as your Target Amount

        // This is your hand, you want to give Luke your 4 Oil
        Map<String,Integer> testHand = new HashMap<>();
        testHand.put("GOLD",5);
        testHand.put("OIL",4);

        // This is the current list of Offers in the market.
        List<Offer> testOfferList = new ArrayList<>();
        testOfferList.add(new Offer("James", 3));
        testOfferList.add(new Offer("Luke", 4));
        testOfferList.add(new Offer("Mason", 2));


        // Bid format : Requester[you], Owner[other player], amount, Commodity[type you will trade]
        // The method submitBid should return an Bid equal to this expectedBid object
        Bid expectedBid = new Bid("Will", "Luke", 4 , "OIL");

        // compare this actualBid to the expectedBid
        Bid actualBid = testObject.submitBid(testOfferList, testHand);

        // comparisons for test
        assertEquals(expectedBid.getOwner(), actualBid.getOwner());
        assertEquals(expectedBid.getAmount(), actualBid.getAmount());
        assertEquals(expectedBid.getCommodityToTrade(), actualBid.getCommodityToTrade());
        assertEquals(expectedBid.getRequester(), actualBid.getRequester());
    }

    @Test
    public void acceptBidTest() {
        Map<String,Integer> testHand = new HashMap<>();
        testHand.put("GOLD",5);
        testHand.put("OIL",4);

        List<Bid> testBidList = new ArrayList<>();
        testBidList.add(new Bid("Luke", "Will", 4 , "OIL"));
        testBidList.add(new Bid("James", "Will", 2 , "OIL"));
        testBidList.add(new Bid("Mason", "Luke", 4 , "GOLD"));

        Bid expectedBid = new Bid("Luke", "Will", 4 , "OIL");

        Bid actualBid = testObject.acceptBid(testBidList, testHand);

        // comparisons for test
        assertEquals(expectedBid.getOwner(), actualBid.getOwner());
        assertEquals(expectedBid.getAmount(), actualBid.getAmount());
        assertEquals(expectedBid.getCommodityToTrade(), actualBid.getCommodityToTrade());
        assertEquals(expectedBid.getRequester(), actualBid.getRequester());
    }
}
package client;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.util.*;

public class ExerciseFiveTest {
    // Test choose between accepting bid or making a bid

    private SimpleTraderLogic testObject;

    @Before
    public void setUp() {
        testObject = new SimpleTraderLogic("Will");
    }

    @Test
    public void shouldAcceptAnyBidWhenThereAreNoOffers() {
        Bid bidToAccept = new Bid("anyRequester", "Will", 3, "OIL");
        List<Offer> offers = new ArrayList<>();
        TraderAction expectedAction = new TraderAction(ActionType.ACCEPT_BID, bidToAccept);
        List<Bid> bids = Collections.singletonList(bidToAccept);
        Map<String, Integer> hand = new HashMap<>();
        hand.put("GOLD", 5);
        hand.put("OIL", 4);
        TraderAction actualAction = testObject.getTraderAction(hand, offers, bids);
        assertEquals(expectedAction.getActionType(), actualAction.getActionType());
        assertEquals(expectedAction.getTradeAction().getOwner(), actualAction.getTradeAction().getOwner());
        assertEquals(expectedAction.getTradeAction().getAmount(), actualAction.getTradeAction().getAmount());
        assertEquals(expectedAction.getTradeAction().getCommodityToTrade(), actualAction.getTradeAction().getCommodityToTrade());
    }

    @Test
    public void ifNoBidOrOffersReturnNull() {
        assertNull(testObject.getBetterOffer(null, null, null));
    }

    @Test
    public void testBidAndOffersSelectBestOffer() {
        TargetTrade testTrade = new TargetTrade("OIL", 4);

        // This is the current list of Offers in the market.
        List<Offer> testOfferList =  new ArrayList<>();
        testOfferList.add(new Offer("James", 3));
        testOfferList.add(new Offer("Luke", 4));
        testOfferList.add(new Offer("Mason", 2));

        Bid preferredBid = new Bid("SAM", "JAMES", 1, "GOLD");

        Offer expectedOffer = new Offer("Luke", 4);
        Offer actualOffer = testObject.getBetterOffer(preferredBid, testOfferList, testTrade);

        assertEquals(expectedOffer.getName(), actualOffer.getName());
        assertEquals(expectedOffer.getAmount(), actualOffer.getAmount());
    }

    @Test
    public void testBidAndOffersPreferBid() {
        TargetTrade testTrade = new TargetTrade("OIL", 4);
        Bid preferredBid = new Bid("SAM", "JAMES", 4, "GOLD");

        // This is the current list of Offers in the market.
        List<Offer> testOfferList =  new ArrayList<>();
        testOfferList.add(new Offer("James", 3));
        testOfferList.add(new Offer("Luke", 2));
        testOfferList.add(new Offer("Mason", 2));

        assertNull(testObject.getBetterOffer(preferredBid, testOfferList, testTrade));
    }
}

package client;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static client.Commodity.OIL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ExerciseFourTest {
    private static final String TRADER_NAME = "Will";
    // Test getting a better offer

    private SimpleTraderLogic testObject;
    private TargetTrade targetTrade;

    @Before
    public void setUp() {
        testObject = new SimpleTraderLogic(TRADER_NAME);
        targetTrade = new TargetTrade(OIL.name(), 3);
    }

    @Test
    public void ifNoBidOrOffersReturnNull() {
        assertNull(testObject.getBetterOffer(null, null, targetTrade));
        assertNull(testObject.getBetterOffer(null, Collections.EMPTY_LIST, targetTrade));
    }

    @Test
    public void preferredBidMatchesAcceptedBidAmount() {
        // The Trader has decided this bid is the preferred bid:
        // note the amount here is already what I selected to trade this round
        Bid preferredBid = new Bid("Player2", TRADER_NAME, 3, targetTrade.getType());

        // So there is no need to check for better offers, the method can return null
        assertNull(testObject.getBetterOffer(preferredBid, null, targetTrade));
    }

    @Test
    public void preferredBidIsBetterThanAnyOffers() {
        targetTrade = new TargetTrade(OIL.name(), 4);
        // The Trader has decided this bid is the preferred bid
        Bid preferredBid = new Bid("Player2", TRADER_NAME, 3, targetTrade.getType());

        // It is less than the amount we wanted to trade so lets check the offer list
        List<Offer> testOfferList = new ArrayList<>();
        testOfferList.add(new Offer("James", 1));
        testOfferList.add(new Offer("Luke", 2));
        testOfferList.add(new Offer("Mason", 2));

        // None of the offers were better than the preferred bid so we should return null
        assertNull(testObject.getBetterOffer(preferredBid, testOfferList, targetTrade));
    }

    @Test
    public void havePreferredBidAndOfferListIsNullOrEmpty() {
        targetTrade = new TargetTrade(OIL.name(), 4);
        // The Trader has decided this bid is the preferred bid
        Bid preferredBid = new Bid("Player2", TRADER_NAME, 3, targetTrade.getType());

        // There are no offers so we return null
        assertNull(testObject.getBetterOffer(preferredBid, null, targetTrade));
        assertNull(testObject.getBetterOffer(preferredBid, Collections.EMPTY_LIST, targetTrade));
    }

    @Test
    public void noPreferredBidAndCannotHonorAnyListedOffers() {
        targetTrade = new TargetTrade(OIL.name(), 1);
        // The Trader has not found any preferred bid

        // We cannot honor any offers in the offer list
        List<Offer> testOfferList = new ArrayList<>();
        testOfferList.add(new Offer("James", 3));
        testOfferList.add(new Offer("Luke", 2));
        testOfferList.add(new Offer("Mason", 4));

        // We can honor any of the offers so we should return null
        assertNull(testObject.getBetterOffer(null, testOfferList, targetTrade));
    }

    @Test
    public void preferredBidButBetterOffer() {
        targetTrade = new TargetTrade(OIL.name(), 4);
        // The Trader has decided this bid is the preferred bid
        Bid preferredBid = new Bid("Player2", TRADER_NAME, 2, targetTrade.getType());

        // There are 2 equal better offers in the offer list, but we just take the first
        List<Offer> testOfferList = new ArrayList<>();
        testOfferList.add(new Offer("James", 3));
        testOfferList.add(new Offer("Luke", 2));
        testOfferList.add(new Offer("Mason", 5));
        testOfferList.add(new Offer("Owen", 3));

        // We found was James the first of the 2 better offers
        Offer expectedOffer = new Offer("James", 3);

        // So we should expect that returned from the method
        Offer actualOffer = testObject.getBetterOffer(preferredBid, testOfferList, targetTrade);
        assertEquals(expectedOffer.getName(), actualOffer.getName());
        assertEquals(expectedOffer.getAmount(), actualOffer.getAmount());

        // If our Target Trade was 5 that Mason would be the better Offer
        targetTrade = new TargetTrade(OIL.name(), 5);
        // We found was James the first of the 2 better offers
        expectedOffer = new Offer("Mason", 5);

        // So we should expect that returned from the method
        actualOffer = testObject.getBetterOffer(preferredBid, testOfferList, targetTrade);
        assertEquals(expectedOffer.getName(), actualOffer.getName());
        assertEquals(expectedOffer.getAmount(), actualOffer.getAmount());
    }
}

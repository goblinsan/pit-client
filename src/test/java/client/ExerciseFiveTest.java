package client;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static client.Commodity.GOLD;
import static client.Commodity.OIL;
import static org.junit.Assert.assertEquals;

public class ExerciseFiveTest {
    private static final String TRADER_NAME = "Will";
    // Test choosing trader action

    private SimpleTraderLogic testObject;
    private TargetTrade targetTrade;
    private Map<String, Integer> hand;

    @Before
    public void setUp() {
        testObject = new SimpleTraderLogic(TRADER_NAME);
        targetTrade = new TargetTrade(OIL.name(), 3);
        hand = new HashMap<>();
        hand.put(GOLD.name(), 6);
        hand.put(OIL.name(), 3);
    }

    @Test
    public void shouldAcceptAnyBidWhenThereAreNoOffers() {
        // The bid list contains 1 bid for your trader
        Bid bidForMe = new Bid("player2", TRADER_NAME, 3, null);
        List<Bid> bids = Collections.singletonList(bidForMe);

        // The offer list is empty
        List<Offer> offers = new ArrayList<>();

        // In this case just select to Accept the Bid
        // TODO: NOTE that when you accept a bid you must tell the market server what commodity you are trading
        // So your bid will take this format:
        // Bid format : Requester[you], Owner[other player], amount, Commodity[type you will trade]
        Bid expectedBid = new Bid("player2", TRADER_NAME, 3, targetTrade.getType());
        TraderAction expectedAction = new TraderAction(ActionType.ACCEPT_BID, expectedBid);

        TraderAction actualAction = testObject.getTraderAction(hand, offers, bids);
        assertEquals(expectedAction.getActionType(), actualAction.getActionType());
        assertEquals(expectedAction.getTradeAction().getOwner(), actualAction.getTradeAction().getOwner());
        assertEquals(expectedAction.getTradeAction().getAmount(), actualAction.getTradeAction().getAmount());
        assertEquals(expectedAction.getTradeAction().getCommodityToTrade(), actualAction.getTradeAction().getCommodityToTrade());
    }


    @Test
    public void testBidAndOffersSelectBestOffer() {
        // The bid list contains 1 bid for your trader
        Bid bidToAccept = new Bid("Player2", TRADER_NAME, 1, null);
        List<Bid> bids = Collections.singletonList(bidToAccept);

        // This is the current list of Offers in the market.
        // It contains an offer that matches your target trade of 3
        List<Offer> testOfferList =  new ArrayList<>();
        testOfferList.add(new Offer("Player5", 3));
        testOfferList.add(new Offer("Player2", 4));
        testOfferList.add(new Offer("Player3", 2));

        // So the action should be to create a bid for player5 offer of 3 cards
        Bid sumbitBid = new Bid(TRADER_NAME, "Player5", 3, targetTrade.getType());
        TraderAction expectedAction = new TraderAction(ActionType.SUBMIT_BID, sumbitBid);

        TraderAction actualAction = testObject.getTraderAction(hand, testOfferList, bids);
        assertEquals(expectedAction.getActionType(), actualAction.getActionType());
        assertEquals(expectedAction.getTradeAction().getOwner(), actualAction.getTradeAction().getOwner());
        assertEquals(expectedAction.getTradeAction().getAmount(), actualAction.getTradeAction().getAmount());
        assertEquals(expectedAction.getTradeAction().getCommodityToTrade(), actualAction.getTradeAction().getCommodityToTrade());
    }

    @Test
    public void noActionableBidsOrOffersJustSubmitTargetTrade() {
        // When there are no bids or offers that can be accepted
        // the action should be to submit an offer for your target trade
        Offer submitOffer = new Offer(TRADER_NAME, targetTrade.getAmount());
        TraderAction expectedAction = new TraderAction(ActionType.SUBMIT_OFFER, submitOffer);

        // in this case the bids and offers are empty
        TraderAction actualAction = testObject.getTraderAction(hand, null, null);
        assertEquals(expectedAction.getActionType(), actualAction.getActionType());
        assertEquals(expectedAction.getTradeAction().getOwner(), actualAction.getTradeAction().getOwner());
        assertEquals(expectedAction.getTradeAction().getAmount(), actualAction.getTradeAction().getAmount());
        assertEquals(expectedAction.getTradeAction().getCommodityToTrade(), actualAction.getTradeAction().getCommodityToTrade());


        // in this case the bids and offers can't be used
        Bid bidNotForMe = new Bid("player2", "someone else", 1, null);
        List<Bid> bids = Collections.singletonList(bidNotForMe);

        // we cant use any of these offers
        List<Offer> testOfferList =  new ArrayList<>();
        testOfferList.add(new Offer("Player5", 4));
        testOfferList.add(new Offer("Player2", 4));
        testOfferList.add(new Offer("Player3", -2));

        actualAction = testObject.getTraderAction(hand, null, bids);
        assertEquals(expectedAction.getActionType(), actualAction.getActionType());
        assertEquals(expectedAction.getTradeAction().getOwner(), actualAction.getTradeAction().getOwner());
        assertEquals(expectedAction.getTradeAction().getAmount(), actualAction.getTradeAction().getAmount());
        assertEquals(expectedAction.getTradeAction().getCommodityToTrade(), actualAction.getTradeAction().getCommodityToTrade());
    }
}

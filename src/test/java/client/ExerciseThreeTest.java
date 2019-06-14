package client;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static client.Commodity.OIL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ExerciseThreeTest {
    // Testing choose preferred bid
    private static final String TRADER_NAME = "Will";

    private SimpleTraderLogic testObject;
    private TargetTrade targetTrade;

    @Before
    public void setUp() {
        testObject = new SimpleTraderLogic(TRADER_NAME);
        targetTrade = new TargetTrade(OIL.name(), 3);
    }

    @Test
    public void shouldReturnNullIfNoBids() {
        assertNull(testObject.choosePreferredBid(null, targetTrade));
    }

    @Test
    public void shouldChooseFirstBidForMyName() {
        // If this is the list of bids in the market
        List<Bid> testBidList = new ArrayList<>();
        testBidList.add(new Bid("Player2", TRADER_NAME, 2, null));
        testBidList.add(new Bid("Player3", "Player4", 4, null));
        testBidList.add(new Bid("Player2", TRADER_NAME, 3, null));

        // Then the trader should choose this expected bid:
        // TODO: NOTE the returned bid contains the targetTrade type as the commodity!!!
        Bid expectedBid = new Bid("Player2", TRADER_NAME, 2, targetTrade.getType());

        Bid actualBid = testObject.choosePreferredBid(testBidList, targetTrade);
        assertEquals(expectedBid.getOwner(), actualBid.getOwner());
        assertEquals(expectedBid.getAmount(), actualBid.getAmount());
        assertEquals(expectedBid.getCommodityToTrade(), actualBid.getCommodityToTrade());
        assertEquals(expectedBid.getRequester(), actualBid.getRequester());
    }

    @Test
    public void ifNoBidsForMyName() {
        // If this is the list of bids in the market
        List<Bid> testBidList = new ArrayList<>();
        testBidList.add(new Bid("Player2", "Player5", 2, null));
        testBidList.add(new Bid("Player3", "Player4", 4, null));
        testBidList.add(new Bid("Player5", "Player2", 3, null));

        // Then the method should return null
        assertNull(testObject.choosePreferredBid(testBidList, targetTrade));
    }

    @Test
    public void ifNoBidsForMyNameThatICanHonor() {
        // If this is the list of bids in the market
        List<Bid> testBidList = new ArrayList<>();
        testBidList.add(new Bid("Player2", "Player5", 2, null));
        testBidList.add(new Bid("Player3", TRADER_NAME, 4, null));
        testBidList.add(new Bid("Player5", "Player2", 3, null));

        // Because my Target Trade is only for 3 - I can't honor the request for 4
        // and the method should return null
        assertNull(testObject.choosePreferredBid(testBidList, targetTrade));
    }

}

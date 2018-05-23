package client;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
public class TraderRunnerTest {

    private TraderRunner testObject;
    private TraderLogic traderLogic;
    private GameConnectionService gameConnectionService;
    private Map<String, Integer> emptyHand;

    @Before
    public void setUp() {
        traderLogic = mock(TraderLogic.class);
        gameConnectionService = mock(GameConnectionService.class);
        testObject = new TraderRunner(traderLogic, gameConnectionService);
        when(gameConnectionService.joinGame()).thenReturn(true);
        when(gameConnectionService.getMarketState()).thenReturn("OPEN");
        when(traderLogic.cornerMarket(anyMap())).thenReturn(false).thenReturn(true);
        emptyHand = new HashMap<>();
    }

    @Test
    public void runnerHasLogicAndConnectionService() {
        assertNotNull(testObject.getTraderLogic());
        assertNotNull(testObject.getGameConnectionService());
    }

    @Test
    public void runnerJoinsGameOnce() {
        testObject.run();
        verify(gameConnectionService).joinGame();
    }

    @Test
    public void runnerGetsHandFromService() {
        when(gameConnectionService.getHand()).thenReturn(emptyHand);

        testObject.run();
        verify(gameConnectionService, times(2)).getHand();
    }

    @Test
    public void traderStateMovesFromStartToWonAfterWinning() {
        assertEquals(TraderState.START, testObject.getState());

        when(traderLogic.cornerMarket(anyMap())).thenReturn(true);
        testObject.run();
        assertEquals(TraderState.WON, testObject.getState());
    }

    @Test
    public void traderStateMovesFromStartToTradingIfNotWon() {
        when(traderLogic.cornerMarket(anyMap())).thenReturn(false).thenReturn(false);
        when(gameConnectionService.getMarketState()).thenReturn("OPEN").thenReturn("CLOSED");
        assertEquals(TraderState.START, testObject.getState());
        testObject.run();
        assertEquals(TraderState.TRADING, testObject.getState());
    }

    @Test
    public void assertBehaviorForTraderFlowWhenNoOfferOrMyBidIsBetter() {
        TargetTrade testTrade = new TargetTrade("OIL", 3);
        when(traderLogic.getTargetTrade(anyMap())).thenReturn(testTrade);
        when(traderLogic.isThereBetterOffer(any(Bid.class), anyList())).thenReturn(false);
        testObject.run();

        verify(traderLogic).getTargetTrade(anyMap());
        verify(traderLogic).submitOffer(anyMap());
        verify(gameConnectionService).submitOffer(any(Offer.class));
        verify(gameConnectionService).getBids();
        verify(traderLogic).acceptBid(anyList(), anyMap());
        verify(gameConnectionService).getOffers();
        verify(traderLogic).isThereBetterOffer(any(Bid.class), anyList());
        verify(gameConnectionService).acceptBid(any(Bid.class));
        verify(gameConnectionService).getTrades();
    }

    @Test
    public void assertBehaviorForTraderFlowWhenBetterOfferExists() {
        TargetTrade testTrade = new TargetTrade("OIL", 3);
        when(traderLogic.getTargetTrade(anyMap())).thenReturn(testTrade);
        when(traderLogic.isThereBetterOffer(any(Bid.class), anyList())).thenReturn(true);
        testObject.run();

        verify(gameConnectionService).submitBid(any(Bid.class));
        verify(gameConnectionService).getTrades();
    }
}
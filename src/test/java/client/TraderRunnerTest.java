package client;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

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
        TargetTrade targetTrade = new TargetTrade("Gold", 4);
        when(traderLogic.getTargetTrade(anyMapOf(String.class, Integer.class))).thenReturn(targetTrade);
        when(gameConnectionService.connect()).thenReturn(true);
        when(gameConnectionService.getMarketState()).thenReturn("OPEN").thenReturn("CLOSED");
        when(traderLogic.canCornerMarket(anyMapOf(String.class, Integer.class))).thenReturn(false).thenReturn(true);
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
        verify(gameConnectionService).connect();
    }

    @Test
    public void runnerGetsHandFromService() {
        when(gameConnectionService.getHand()).thenReturn(emptyHand);

        testObject.run();
        verify(gameConnectionService).getHand();
    }

    @Test
    public void traderStateMovesFromStartToTradingIfNotWon() {
        when(traderLogic.canCornerMarket(anyMapOf(String.class, Integer.class))).thenReturn(false).thenReturn(false).thenReturn(false);
        when(gameConnectionService.getMarketState()).thenReturn("OPEN").thenReturn("CLOSED");
        assertEquals(TraderState.START, testObject.getState());
        testObject.run();
        assertEquals(TraderState.TRADING, testObject.getState());
    }

}
package client;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static client.Commodity.GOLD;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class TraderRunnerTest {

    private TraderRunner testObject;
    private SimpleTraderLogic traderLogic;
    private GameConnectionService gameConnectionService;
    private Map<String, Integer> emptyHand;

    @Before
    public void setUp() {
        traderLogic = mock(SimpleTraderLogic.class);
        gameConnectionService = mock(GameConnectionService.class);
        testObject = new TraderRunner(traderLogic, gameConnectionService);
        TargetTrade targetTrade = new TargetTrade(GOLD.name(), 4);
        when(traderLogic.getTargetTrade(anyMapOf(String.class, Integer.class))).thenReturn(targetTrade);
        String tester = "TESTER";
        when(traderLogic.getName()).thenReturn(tester);
        TraderAction traderAction = new TraderAction(ActionType.SUBMIT_BID, new Offer(tester, 4));
        when(traderLogic.getTraderAction(anyMap(), anyList(), anyList())).thenReturn(traderAction);
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
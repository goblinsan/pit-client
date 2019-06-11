package client;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.LinkedHashMap;
import java.util.Map;

import static client.Commodity.*;
import static client.SwitchTraderLogic.MAX_CONSECUTIVE;
import static client.SwitchTraderLogic.MAX_SWITCH;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Mockito.when;

public class SwitchTraderLogicTest {

    private SwitchTraderLogic testObject;
    private TraderHistory mockTraderHistory;
    private Randomize mockRandomTrade;

    @Before
    public void setUp() {
        mockRandomTrade = Mockito.mock(Randomize.class);
        mockTraderHistory = Mockito.mock(TraderHistory.class);
        testObject = new SwitchTraderLogic("tester", mockTraderHistory, mockRandomTrade);
    }

    @Test
    public void whenDeadlockedShouldChooseRandomTrade() {
        when(mockTraderHistory.getConsecutiveTradeAmountAttempts()).thenReturn(MAX_CONSECUTIVE + 1);
        TargetTrade randomTrade = new TargetTrade("Random", 99);
        when(mockRandomTrade.getRandomTargetTrade(anyMap())).thenReturn(randomTrade);
        Map<String, Integer> hand = setupHand(3,3,3);

        TargetTrade actualTrade = testObject.getTargetTrade(hand);
        assertEquals(randomTrade.getType(), actualTrade.getType());
        assertEquals(randomTrade.getAmount(), actualTrade.getAmount());
    }

    @Test
    public void lastSwitchOverMaxAndSameHand() {
        when(mockTraderHistory.getLastSwitch()).thenReturn(0);
        when(mockTraderHistory.getRounds()).thenReturn(MAX_SWITCH);
        Map<String, Integer> hand = setupHand(2,3,4);
        when(mockTraderHistory.getLastHand()).thenReturn(hand);
        TargetTrade expectedTrade = new TargetTrade("GOLD", 2);

        TargetTrade actualTrade = testObject.getTargetTrade(hand);
        assertEquals(expectedTrade.getType(), actualTrade.getType());
        assertEquals(expectedTrade.getAmount(), actualTrade.getAmount());
    }

    @Test
    public void lastSwitchOverMaxAndDifferentHand_ContinueSimpleStrategy() {
        when(mockTraderHistory.getLastSwitch()).thenReturn(0);
        when(mockTraderHistory.getRounds()).thenReturn(MAX_SWITCH);
        Map<String, Integer> lastHand = setupHand(2,1,6);
        when(mockTraderHistory.getLastHand()).thenReturn(lastHand);
        TargetTrade expectedTrade = new TargetTrade("RICE", 2);
        Map<String, Integer> thisHand = setupHand(2,3,4);

        TargetTrade actualTrade = testObject.getTargetTrade(thisHand);
        assertEquals(expectedTrade.getType(), actualTrade.getType());
        assertEquals(expectedTrade.getAmount(), actualTrade.getAmount());
    }

    private Map<String, Integer> setupHand(int rice, int oil, int gold){
        Map<String, Integer> hand = new LinkedHashMap<>();
        hand.put(RICE.name(), rice);
        hand.put(OIL.name(), oil);
        hand.put(GOLD.name(), gold);
        return hand;
    }
}
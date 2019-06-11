package features;

import client.Randomize;
import client.SwitchTraderLogic;
import client.TargetTrade;
import client.TraderHistory;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.mockito.Mockito;

import java.util.LinkedHashMap;
import java.util.Map;

import static client.Commodity.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Mockito.when;

public class SwitchingTraderSteps {

    private SwitchTraderLogic testObject;
    private TraderHistory mockTraderHistory;
    private Randomize mockRandomTrade;
    private Map<String, Integer> hand;
    private TargetTrade selectedTargetTrade;

    @Before
    public void setup() {
        mockRandomTrade = Mockito.mock(Randomize.class);
        mockTraderHistory = Mockito.mock(TraderHistory.class);
        testObject = new SwitchTraderLogic("me", mockTraderHistory, mockRandomTrade);
        hand = new LinkedHashMap<>();
    }

    @Given("^the amount of consecutiveTradeAmountAttempts is \"([^\"]*)\"$")
    public void theAmountOfConsecutiveTradeAmountAttemptsIs(int consecutiveTradeAttempts) {
        when(mockTraderHistory.getConsecutiveTradeAmountAttempts()).thenReturn(consecutiveTradeAttempts);
        when(mockRandomTrade.getRandomTargetTrade(anyMap())).thenReturn(new TargetTrade("RANDOM", 99));
    }

    @And("^the trader's hand is \"([^\"]*)\" rice \"([^\"]*)\" oil and \"([^\"]*)\" gold$")
    public void theTraderSHandIsRiceOilAndGold(int rice, int oil, int gold) {
        hand.put(RICE.name(), rice);
        hand.put(OIL.name(), oil);
        hand.put(GOLD.name(), gold);
    }

    @When("^the trader gets the next target trade$")
    public void theTraderGetsTheNextTargetTrade() {
        selectedTargetTrade = testObject.getTargetTrade(hand);
    }

    @Then("^it should return the random result$")
    public void itShouldReturnTheRandomResult() {
        assertEquals("RANDOM", selectedTargetTrade.getType());
        assertEquals(99, selectedTargetTrade.getAmount());
    }

    @And("^the number of rounds is \"([^\"]*)\"$")
    public void theNumberOfRoundsIs(int numberOfRounds) {
        when(mockTraderHistory.getRounds()).thenReturn(numberOfRounds);
    }

    @And("^the last switch was round \"([^\"]*)\"$")
    public void theLastSwitchWasRound(int lastSwitchRound) {
        when(mockTraderHistory.getLastSwitch()).thenReturn(lastSwitchRound);
    }

    @And("^the last hand was \"([^\"]*)\" rice \"([^\"]*)\" oil and \"([^\"]*)\" gold$")
    public void theLastHandWasRiceOilAndGold(int rice, int oil, int gold) {
        Map<String, Integer> lastHand = new LinkedHashMap<>();
        lastHand.put(RICE.name(), rice);
        lastHand.put(OIL.name(), oil);
        lastHand.put(GOLD.name(), gold);
        when(mockTraderHistory.getLastHand()).thenReturn(lastHand);
    }

    @Then("^it should return a trade for \"([^\"]*)\" of \"([^\"]*)\"$")
    public void itShouldReturnATradeForOf(int amount, String type) {
        assertEquals(type.toUpperCase(), selectedTargetTrade.getType());
        assertEquals(amount, selectedTargetTrade.getAmount());
    }
}

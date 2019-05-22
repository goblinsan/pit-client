package features;

import client.SimpleTraderLogic;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.HashMap;
import java.util.Map;

import static client.Commodity.*;
import static org.junit.Assert.assertEquals;

public class SimpleTraderSteps {

    private SimpleTraderLogic testObject;
    private Map<String, Integer> hand;
    private boolean canCornerMarket;

    @Before
    public void setup() {
        testObject = new SimpleTraderLogic("Dani");
        hand = new HashMap<>();
    }

    @Given("^the trader has a hand consisting of \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\"$")
    public void theTraderHasAHandConsistingOfAnd(int rice, int oil, int gold)  {
        hand.put(RICE.name(), rice);
        hand.put(OIL.name(), oil);
        hand.put(GOLD.name(), gold);
    }

    @When("^the trader checks if they should win$")
    public void theTraderChecksIfTheyShouldWin() {
        canCornerMarket = testObject.canCornerMarket(hand);
    }

    @Then("^they decide \"([^\"]*)\"$")
    public void theyDecide(boolean shouldWin) {
        assertEquals(shouldWin, canCornerMarket);
    }
}

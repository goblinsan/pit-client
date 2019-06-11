package features;

import client.RandomTrade;
import client.TargetTrade;
import cucumber.api.DataTable;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static client.Commodity.*;
import static org.junit.Assert.assertTrue;

public class RandomTradeSteps {

    private Map<String, Integer> hand;
    private Map<TargetTrade, Integer> targetTradeProfile;
    private RandomTrade randomTrade;

    @Before
    public void setup() {
        hand = new LinkedHashMap<>();
        targetTradeProfile = new HashMap<>();
        targetTradeProfile.put(new TargetTrade(GOLD.name(), 1), 0);
        targetTradeProfile.put(new TargetTrade(GOLD.name(), 2), 0);
        targetTradeProfile.put(new TargetTrade(GOLD.name(), 3), 0);
        targetTradeProfile.put(new TargetTrade(OIL.name(), 1), 0);
        targetTradeProfile.put(new TargetTrade(OIL.name(), 2), 0);
        targetTradeProfile.put(new TargetTrade(OIL.name(), 3), 0);
        targetTradeProfile.put(new TargetTrade(RICE.name(), 1), 0);
        targetTradeProfile.put(new TargetTrade(RICE.name(), 2), 0);
        targetTradeProfile.put(new TargetTrade(RICE.name(), 3), 0);
        randomTrade = RandomTrade.INSTANCE;
    }

    @Given("^the trader has a hand consisting of \"([^\"]*)\" rice \"([^\"]*)\" oil and \"([^\"]*)\" gold$")
    public void theTraderHasAHandConsistingOfRiceOilAndGold(int rice, int oil, int gold) {
        hand.put(RICE.name(), rice);
        hand.put(OIL.name(), oil);
        hand.put(GOLD.name(), gold);
    }

    @When("^the random trade utility is run \"([^\"]*)\" times$")
    public void theRandomTradeUtilityIsRunTimes(int iterations) {
        for (int i = 0; i < iterations; i++) {
            TargetTrade targetTrade = randomTrade.getRandomTargetTrade(hand);
            int currentCount = targetTradeProfile.get(targetTrade);
            targetTradeProfile.put(targetTrade, currentCount + 1);
        }
    }

    @Then("^the distribution of target trades selected should be between the low and high$")
    public void theDistributionOfTargetTradesSelectedShouldBeBetweenTheLowAndHigh(DataTable dataTable) {
        List<DistributionInput> distributionInputs = dataTable.asList(DistributionInput.class);
        distributionInputs.forEach(di -> assertTrue(di.expectedDistribution(targetTradeProfile.get(di.getTargetTrade()))));
    }
}

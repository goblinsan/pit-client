package features;

import client.*;
import cucumber.api.DataTable;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static client.Commodity.*;
import static org.junit.Assert.assertEquals;

public class SimpleTraderSteps {

    private SimpleTraderLogic testObject;
    private Map<String, Integer> hand;
    private boolean canCornerMarket;
    private TargetTrade selectedTargetTrade;
    private TargetTrade targetTrade;
    private List<Bid> bidList;
    private Bid selectedBid;
    private List<Offer> offerList;
    private Offer selectedOffer;
    private TraderAction selectedTraderAction;

    @Before
    public void setup() {
        testObject = new SimpleTraderLogic("me");
        hand = new LinkedHashMap<>();
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

    @When("^the trader decides what to offer the market$")
    public void theTraderDecidesWhatToOfferTheMarket() {
        selectedTargetTrade = testObject.getTargetTrade(hand);
    }

    @Then("^they decide to trade \"([^\"]*)\" of \"([^\"]*)\"$")
    public void theyDecideToTradeOf(int numToTrade, String commodity) {
        assertEquals(numToTrade, selectedTargetTrade.getAmount());
        assertEquals(commodity, selectedTargetTrade.getType());
    }

    @Given("the market has this list of bids:")
    public void theMarketHasThisListOfBids(DataTable dataTable) {
        bidList = dataTable.asList(Bid.class);
    }

    @And("my target trade is:")
    public void myTargetTradeIs(DataTable dataTable) {
        targetTrade = dataTable.asList(TargetTrade.class).get(0);
    }

    @When("the trader decides what to bid to select")
    public void theTraderDecidesWhatToBidToSelect() {
        selectedBid = testObject.choosePreferredBid(bidList, targetTrade);
    }

    @Then("they should select bid:")
    public void theyShouldSelectBid(DataTable dataTable) {
        Bid bid = dataTable.asList(Bid.class).get(0);
        assertEquals(bid.getRequester(), selectedBid.getRequester());
        assertEquals(bid.getOwner(), selectedBid.getOwner());
        assertEquals(bid.getAmount(), selectedBid.getAmount());
    }

    @Given("the market has this list of offers:")
    public void theMarketHasThisListOfOffers(DataTable dataTable) {
        offerList = dataTable.asList(Offer.class);
    }

    @And("my selected bid was:")
    public void mySelectedBidWas(DataTable dataTable) {
        selectedBid = dataTable.asList(Bid.class).get(0);
    }

    @When("the trader decides if there is a better offer")
    public void theTraderDecidesIfThereIsABetterOffer() {
        selectedOffer = testObject.getBetterOffer(selectedBid, offerList, targetTrade);
    }

    @Then("they select the better offer of:")
    public void theySelectTheBetterOfferOf(DataTable dataTable) {
        Offer offer = dataTable.asList(Offer.class).get(0);
        assertEquals(offer.getOwner(), selectedOffer.getOwner());
        assertEquals(offer.getAmount(), selectedOffer.getAmount());
    }


    @When("the trader decides what action to take")
    public void theTraderDecidesWhatActionToTake() {
        selectedTraderAction = testObject.getTraderAction(hand, offerList, bidList);
    }

    @Then("they select to take the following action:")
    public void theySelectToTakeTheFollowingAction(DataTable dataTable) {
        TraderAction traderAction = dataTable.asList(TraderActionInput.class).get(0).toTraderAction();
        assertEquals(traderAction.getActionType(), selectedTraderAction.getActionType());
        assertEquals(traderAction.getTradeAction().getAmount(), selectedTraderAction.getTradeAction().getAmount());
        assertEquals(traderAction.getTradeAction().getRequester(), selectedTraderAction.getTradeAction().getRequester());
        assertEquals(traderAction.getTradeAction().getOwner(), selectedTraderAction.getTradeAction().getOwner());
        assertEquals(traderAction.getTradeAction().getCommodityToTrade(), selectedTraderAction.getTradeAction().getCommodityToTrade());
    }

}

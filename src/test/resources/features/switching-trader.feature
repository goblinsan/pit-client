Feature: Switching Trader

  With the simple trading strategy sometimes the market can get stuck.
  The switching trader seeks to overcome stuck markets by occasionally switching which commodity it is trying to trade.
  It will leverage the basic flow of the simple trader, but additionally keep track of the trades it has made.
  If it detects that the trade offer is not changing it will try a new commodity.

  This trader extends the SimpleTrader and adds logic to the "getTargetTrade" method
  Basic Logic flow:

#  Step 1 - Is the market deadlocked?

  Scenario: Deadlock Detected
    Given the amount of consecutiveTradeAmountAttempts is "7"
    And the trader's hand is "3" rice "3" oil and "3" gold
    When the trader gets the next target trade
    Then it should return the random result

#  Step 2 - Not deadlocked, but its been awhile since we've been trying this commodity.
#           If we are still making progress,we will keep trying, but if we are stuck with the same hand, let's switch.
#           Check if its been 15 rounds since the last switch then see if we're trying the same hand.

  Scenario: Been awhile but still making progress (continue with simple strategy)
    Given the trader's hand is "2" rice "3" oil and "4" gold
    And the number of rounds is "15"
    And the last switch was round "0"
    And the last hand was "3" rice "3" oil and "3" gold
    When the trader gets the next target trade
    Then it should return a trade for "2" of "RICE"

  Scenario: Been awhile but not making progress
    Given the trader's hand is "2" rice "3" oil and "4" gold
    And the number of rounds is "15"
    And the last switch was round "0"
    And the last hand was "2" rice "3" oil and "4" gold
    When the trader gets the next target trade
    Then it should return a trade for "2" of "GOLD"
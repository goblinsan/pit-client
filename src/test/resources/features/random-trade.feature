Feature: Random Trade

  To introduce some random activity the trading client offers a random trade feature.
  It will select a random commodity and amount from the trader's hand to select as the "Target Trade"


  Scenario: no bids or offers
    Given the trader has a hand consisting of "3" rice "3" oil and "3" gold
    When the random trade utility is run "9000" times
    Then the distribution of target trades selected should be between the low and high
      | commodity | amount | low | high |
      | rice      | 1      | 900 | 1100 |
      | rice      | 2      | 900 | 1100 |
      | rice      | 3      | 900 | 1100 |
      | oil       | 1      | 900 | 1100 |
      | oil       | 2      | 900 | 1100 |
      | oil       | 3      | 900 | 1100 |
      | gold      | 1      | 900 | 1100 |
      | gold      | 2      | 900 | 1100 |
      | gold      | 3      | 900 | 1100 |



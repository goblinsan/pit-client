Feature: Simple Trader

  The trading client has the basic functions needed to participate in the market.

  The client uses the GameConnectionService to interact with the market server.
  Using that service it retrieves a "Hand" of commodities that represent the total amount of each
  commodity the trader owns.  The number of commodities in play is equal to the number of traders in the game.
  There are 9 of each commodity, and to win a play must hold all 9 of a single commodity - this is "Cornering the Market"

  The trading client will execute a basic strategy to trade groups of like cards to other traders through the market.
  The trading flow is roughly this:
  Player 1 "Offers" an about to trade
  Player 2 "Bids" on Player 1's offer
  Player 1 accepts the bid from Player 2 and the market server registers the "Trade"


  Basic Logic flow:

#  Step 1 - Have I won the game?

  Scenario Outline: Cornering the Market
    Given the trader has a hand consisting of "<rice>" "<oil>" and "<gold>"
    When the trader checks if they should win
    Then they decide "<shouldWin>"

    Examples:
      | rice | oil | gold | shouldWin |
      | 1    | 2   | 6    | false     |
      | 9    | 0   | 0    | true      |

#  Step 2 - Choose a card to Trade
#  Here we just want to choose the card we have the least of and "Offer" that to the market.
#  If we have two commodities of the same amount we chose the first.

  Scenario Outline: Selection a Trade
    Given the trader has a hand consisting of "<rice>" "<oil>" and "<gold>"
    When the trader decides what to offer the market
    Then they decide to trade "<numberToTrade>" of "<selectedCommodity>"

    Examples:
      | rice | oil | gold | numberToTrade | selectedCommodity |
      | 1    | 2   | 6    | 1             | RICE              |
      | 0    | 3   | 6    | 3             | OIL               |
      | 2    | 2   | 5    | 2             | RICE              |

#  Step 3 - Check for bids we can accept
#  If there are any bids in the market for one of our previous offers and we still have that amount to trade, lets accept it.

  Scenario: Select a Bid
    Given the market has this list of bids:
      | requester | owner | amount | commodityToTrade |
      | p2        | me    | 4      | null             |
      | p3        | p2    | 2      | null             |
      | me        | p2    | 2      | null             |
      | p3        | me    | 2      | null             |
    And my target trade is:
      | amount | type |
      | 3      | OIL  |
    When the trader decides what to bid to select
    Then they should select bid:
      | requester | owner | amount | commodityToTrade |
      | p3        | me    | 2      | null             |

#  Step 4 - Check for better offers
#  If there were no bids we could except lets check the offers.
#  Or - If we found a bid, but it was less than our Target Trade - maybe there is a better offer available.
#  In this case a better offer is one that equals the amount we want to trade.

  Scenario: Better Offer?
    Given the market has this list of offers:
      | name | amount |
      | p1   | 1      |
      | p2   | 3      |
      | p3   | 2      |
    And my target trade is:
      | amount | type |
      | 3      | OIL  |
    And my selected bid was:
      | requester | owner | amount | commodityToTrade |
      | p3        | me    | 2      | null             |
    When the trader decides if there is a better offer
    Then they select the better offer of:
      | name | amount |
      | p2   | 3      |


#  Step 5 - The last step is to decide what to do.
#  If there are no acceptable bids or offers in the market, just submit an offer for your target trade.
#  If there are no bids, or if there is better offer available then submit a bid for that offer.
#  If there is an acceptable bid and no better offers, then accept the bid to complete the trade.

  Scenario: no bids or offers
    Given the market has this list of bids:
      | requester | owner | amount | commodityToTrade |
    And the market has this list of offers:
      | name | amount |
    And the trader has a hand consisting of "1" "2" and "6"
    When the trader decides what action to take
    Then they select to take the following action:
      | actionType   | requester | owner | amount | commodityToTrade |
      | SUBMIT_OFFER | null      | null  | 1      | null             |


  Scenario: no ACCEPTABLE bids or offers
    Given the market has this list of bids:
      | requester | owner | amount | commodityToTrade |
      | p2        | me    | 4      | null             |
      | p3        | p2    | 2      | null             |
      | me        | p2    | 2      | null             |

    And the market has this list of offers:
      | name | amount |
      | me   | 2      |
      | p4   | 4      |

    And the trader has a hand consisting of "1" "2" and "6"
    When the trader decides what action to take
    Then they select to take the following action:
      | actionType   | requester | owner | amount | commodityToTrade |
      | SUBMIT_OFFER | null      | null  | 1      | null             |


  Scenario: good bid, no better offer
    Given the market has this list of bids:
      | requester | owner | amount | commodityToTrade |
      | p3        | me    | 1      | null             |

    And the market has this list of offers:
      | name | amount |
      | p4   | 4      |

    And the trader has a hand consisting of "1" "2" and "6"
    When the trader decides what action to take
    Then they select to take the following action:
      | actionType | requester | owner | amount | commodityToTrade |
      | ACCEPT_BID | p3        | me    | 1      | RICE             |


  Scenario: good bid, better offer
    Given the market has this list of bids:
      | requester | owner | amount | commodityToTrade |
      | p3        | me    | 1      | null             |

    And the market has this list of offers:
      | name | amount |
      | p4   | 3      |

    And the trader has a hand consisting of "3" "0" and "6"
    When the trader decides what action to take
    Then they select to take the following action:
      | actionType | requester | owner | amount | commodityToTrade |
      | SUBMIT_BID | me        | p4    | 3      | RICE             |
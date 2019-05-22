Feature: The trading client has the basic functions needed to participate in the market

  The client uses the GameConnectionService to interact with the market server.
  Using that service it retrieves a "Hand" of commodities that represent the total amount of each
  commodity the trader owns.  The number of commodities in play is equal to the number of traders in the game.
  There are 9 of each commodity, and to win a play must hold all 9 of a single commodity - this is "Cornering the Market"

  The trading client will execute a basic strategy to trade groups of like cards to other traders through the market.
  The trading flow is roughly this:
  Player 1 "Offers" an about to trade
  Player 2 "Bids" on Player 1's offer
  Player 1 accepts the bid from Player 2 and the market server registers the "Trade"


  Scenario Outline: Cornering the Market
    Given the trader has a hand consisting of "<rice>" "<oil>" and "<gold>"
    When the trader checks if they should win
    Then they decide "<shouldWin>"

    Examples:
      | rice | oil | gold | shouldWin |
      | 1    | 2   | 6    | false     |
      | 9    | 0   | 0    | true      |
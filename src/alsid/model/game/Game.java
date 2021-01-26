package alsid.model.game;

import java.util.*;


public class Game
{
	private Board 				gameBoard;
	private ArrayList <Player> 	players;
	private Deck 				chanceDeck;

	private Bank				bank;
	private int 				currentPlayer = 0;

	public static final int		PLAYER_BANKRUPT = 1;
	public static final int 	TWO_FULL_SETS = 2;
	public static final int 	BANK_EMPTY = 3;

	/**
	 * Constructor for a game instance.
	 */
	public Game ()
	{
		players = new ArrayList<>();
	}

	/**
	 * Initializes the game. Only do this after players have been added.
	 */
	public void initGame()
	{
		gameBoard = new Board(players.size());
		bank = new Bank(players.size());
		chanceDeck = (new Deck());
		chanceDeck.initChance(this);
	}

	//...GETTERS

	/**
	 * Getter for the gameboard.
	 * @return Current board for <code>this</code> game.
	 */
	public Board getBoard()
	{
		return gameBoard;
	}

	/**
	 * Getter for the players.
	 * @return ArrayList of Players in this game.
	 */
	public ArrayList<Player> getPlayers()
	{
		return players;
	}

	/**
	 * Getter for the current player.
	 * @return Player number of the current player.
	 */
	public int getCurrentPlayer()
	{
		return currentPlayer;
	}

	/**
	 * Getter for the bank.
	 * @return The bank for this game.
	 */
	public Bank getBank()
	{
		return bank;
	}

	/**
	 * Getter for the deck of Chance cards.
	 * @return Deck of chance cards for this game.
	 */
	public Deck getChanceDeck()
	{
		return chanceDeck;
	}

	/**
	 * Adds a player to this game.
	 * @param player Player to add.
	 */
	public void addPlayer(Player player)
	{
		players.add(player);
	}

	/**
	 * Removes a player from the current game.
	 * @param player Player to remove.
	 */
	public void removePlayer(Player player)
	{
		players.remove(player);
	}

	/**
	 * Moves to the next player's turn. If all players have taken their turn,
	 * the first player will go again.
	 */
	public void nextTurn()
	{
		currentPlayer = (currentPlayer + 1) % players.size();
	}

	/**
	 * Checks if a game ending condition has been met.
	 * @return <code>0</code> if game has not yet ended. Will return a constant otherwise.
	 */
	public int checkGameEnd ()
	{
		for (Player player : players) {
			//check if a player does not have enough money to pay for rent, tax or fine
			if (player.isBankrupt())
				return PLAYER_BANKRUPT;

			//check if a player owns 2 full sets of properties with the same color
			if (player.getFullSetCount() >= 2)
				return TWO_FULL_SETS;
		}

		//check if the bank is out of cash
		if(bank.isBankrupt())
			return BANK_EMPTY;

		return 0;
	}

	/**
	 * Sorts the players according to their standing. Total net worth (money + property value) is
	 * checked first, then only money is checked to break ties.
	 */
	public void rankPlayers()
	{
		boolean hasTie = false;

		for(int i = 0; i < getPlayers().size(); i++)
		{
			for (int j = 0; j < getPlayers().size(); j++)
			{
				if (i != j && getPlayers().get(i).getNetWorth() == getPlayers().get(j).getNetWorth())
				{
					hasTie = true;
				}
			}
		}

		if(!hasTie)
		{
			getPlayers().sort((o1, o2) ->
			{
				if (o1.getNetWorth() == o2.getNetWorth()) {
					return 0;
				} else if (o1.getNetWorth() > o2.getNetWorth()) {
					return 1;
				}

				return -1;
			});
		}

		else
		{
			getPlayers().sort((o1, o2) ->
			{
				if (o1.getMoney() == o2.getMoney()) {
					return 0;
				} else if (o1.getMoney() > o2.getMoney()) {
					return 1;
				}

				return -1;
			});
		}

		Collections.reverse(getPlayers());
	}
}
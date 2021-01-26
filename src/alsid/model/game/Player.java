package alsid.model.game;

import alsid.model.asset.*;
import alsid.model.chance.Chance;

import java.util.*;

/**
 * Class for a player in the game. They perform many actions that advance the board state, including
 * buying, trading and paying for assets, etc.
 */
public class Player implements Payable
{
	//...ATTRIBUTES
	
	private String 	strName;
	private int 	nNumber;
	private int 	nPosition;
	
	private double 				dMoney = 1500;
	private ArrayList <Asset> 	assets;
	private ArrayList <Chance> 	ownedChance;	
	
	//...CONSTRUCTOR
	
	/**
     * Constructor for a Player.
     * @param 	name	Name of <code>this</code> player.
     * @param 	number	Corresponding number (for ordering) of <code>this</code> player.
     */
	public Player (String name, int number)
	{
		strName = name;
		nNumber = number;
		nPosition = 0;
		
		assets = new ArrayList <Asset> ();
		ownedChance = new ArrayList <Chance> ();
	}
	
	
	
	//...GETTERS
	
	/**
     * Gets the name of <code>this</code> player.
     * @return Name of player.
     */
	public String getName ()
	{
		return strName;
	}
	
	/**
     * Gets the number assigned to <code>this</code> player.
     * @return Number of <code>this</code> player.
     */
	public int getNumber ()
	{
		return nNumber;
	}
	
	/**
     * Gets the position of <code>this</code> player with respect to the board.
     * @return The position of <code>this</code> player.
     */
	public int getPosition ()
	{
		return nPosition;
	}

	/**
     * Gets the amount of money <code>this</code> player has.
     * @return Amount of money <code>this</code> player has.
     */
	public double getMoney ()
	{
		return dMoney;
	}
	
	/**
     * Gets the <code>ArrayList</code> of assets <code>this</code> player has.
     * @return <code>ArrayList</code> of <code>this</code> player's assets.
     */
	public ArrayList <Asset> getAssets ()
	{
		return assets;
	}

	/**
     * Gets the <code>ArrayList</code> of properties <code>this</code> player has.
     * @return <code>ArrayList</code> of <code>this</code> player's properties.
     */
	public ArrayList <Property> getProperties ()
	{
		ArrayList <Property> propTemp = new ArrayList <>();
		assets.forEach(asset -> {
			if (asset instanceof Property)
				propTemp.add((Property) asset);
		});
		
		return propTemp;
	}

	/**
     * Gets the <code>ArrayList</code> of Utilities <code>this</code> player has.
     * @return <code>ArrayList</code> of <code>this</code> player's utilities.
     */
	public ArrayList <Utility> getUtilities ()
	{
		ArrayList <Utility> utilTemp = new ArrayList <>();
		assets.forEach(asset -> {
			if (asset instanceof Utility)
				utilTemp.add((Utility) asset);
		});
		
		return utilTemp;
	}

	/**
     * Gets the <code>ArrayList</code> of Railroads <code>this</code> player has.
     * @return <code>ArrayList</code> of <code>this</code> player's railroads.
     */
	public ArrayList <Railroad> getRailroads ()
	{
		ArrayList <Railroad> railTemp = new ArrayList <>();
		assets.forEach(asset -> {
			if (asset instanceof Railroad)
				railTemp.add((Railroad) asset);
		});
		
		return railTemp;
	}

	/**
     * Gets the <code>ArrayList</code> of chance cards <code>this</code> player has.
     * @return <code>ArrayList</code> of <code>this</code> player's chance cards.
     */
	public ArrayList <Chance> getChanceCards()
	{
		return ownedChance;
	}

	/**
	 * Gets the net worth of the <code>this</code> Player (to be used for ranking).
	 * @return Net worth of <code>this</code> Player.
	 */
	public double getNetWorth()
	{
		double propertyValue = 0;
		for(int i = 0; i < getProperties().size(); i++)
			propertyValue += getProperties().get(i).getHouseCount() * getProperties().get(i).getHousePrice();

		return dMoney + propertyValue;
	}

	//...METHODS
	
	/**
     * Modifies the amount of money <code>this</code> player has.
     * @param amount How much <code>this</code> player's money will be increased or decreased.
     */
	@Override
	public void add (double amount)
	{
		dMoney += amount;
	}
	
	/**
     * Adds the model.asset.asset to the list of assets <code>this</code> player has.
     * @param asset The model.asset.asset to be owned by <code>this</code> player.
     */
	public void add (Asset asset)
	{
		assets.add(asset);
		asset.setOwner(this);
	}
	
	/**
     * Adds the chance card to <code>this</code> player's chance cards.
     * @param card The card to be added to <code>this</code> player.
     */
	public void add (Chance card)
	{
		ownedChance.add(card);
		//card.giveTo(this);
	}
	
	/**
     * Removes the model.asset.asset from <code>this</code> player's <code>ArrayList</code> of assets.
     * @param    asset    Asset to be removed from <code>this</code> player.
	 */
	public void removeAsset (Asset asset)
	{
		if (assets.contains(asset))
		{
			assets.remove(asset);
		}
	}
	
	
	/**
     * Decreases <code>this</code> player's money after purchasing the asset,
	 * adds the property to <code>this</code> player's Asset <code>ArrayList</code>, and
	 * sets the ownership of <code>this</code> Asset <code>this</code> player.
	 * @param asset Asset being purchased by <code>this</code> player.
	 */
	public void purchase (Asset asset)
	{
		if (!asset.isOwned())
		{
			add(-1 * asset.getPrice());
			add(asset);
			asset.setOwner(this);
		}
	}
	
	/**
     * Develops the property (constructs one house).
	 * @param    prop    The property to be developed by the owner.
	 */
	public void develop (Property prop)
	{
		if (prop.canDevelop() && prop.getOwner().equals(this)) {
			prop.incHouseCount();
			this.add(-1 * prop.getHousePrice());
		}
	}
	
	/**
     * Transfers corresponding rent money from <code>this</code> player to the property's owner.
	 * @param 	prop 				The property <code>this</code> player is currently located at.
	 * @return 	<code>true</code> if the transaction is successful
     */
	public String payRent (Property prop)
	{
		String dialogue = payTo(prop.getOwner(), prop.getRent());

		prop.incRentCollected(prop.getRent());
		prop.resetTempMod();
		return dialogue;
	}

	/**
     * Transfers corresponding rent money from <code>this</code> player to the <code>rail</code>'s owner.
	 * @param 	rail 	The railroad <code>this</code> player is currently located at.
	 * @return 	<code>true</code> if the transaction is successful
     */
	public String payRent (Railroad rail)
	{
		String dialogue = payTo(rail.getOwner(), rail.getRent());

		rail.resetTempMod();
		return dialogue;
	}

	/**
     * Transfers corresponding rent money from <code>this</code> player to the <code>util</code>'s owner.
	 * @param 	util 	The utility <code>this</code> player is currently located at.
	 * @param	nDiceRoll	Current dice roll this turn.
	 * @param 	ChanceEvent	If this payment is from a chance card.
	 * @return 	<code>true</code> if the transaction is successful
     */
	public String payRent (Utility util, int nDiceRoll, boolean ChanceEvent)
	{
		String dialogue;
		if (ChanceEvent)
			dialogue = payTo(util.getOwner(), nDiceRoll * 10);
		else
		{
			dialogue = payTo(util.getOwner(), util.getRent() * nDiceRoll);
			util.resetTempMod();
		}

		return dialogue;
	}

	/**
     * Checks if <code>this</code> player is bankrupt.
     * @return 	<code>true</code> if <code>this</code> player's money is less than or equal to 0
     */
	@Override
	public boolean isBankrupt ()
	{
		return dMoney <= 0;
	}

	/**
	 * Gets the number of full sets of properties that <code>this</code> player has.
	 * @return Full sets of properties.
	 */
	public int getFullSetCount() {
		int fullSetCount = 0;
		ArrayList <Integer> colorScanHistory = new ArrayList <>();
		ArrayList <Property> properties = getProperties();

		for (int i = 0; i < properties.size(); i++) {
			Integer color = properties.get(i).getColor();
			if (!colorScanHistory.contains(color)) {
				colorScanHistory.add(color);
				switch (color){
					case Property.COLOR_GRAY:
					case Property.COLOR_RED:
					case Property.COLOR_ORANGE: 
						{
							if (properties.get(i).getOwnerCount() == 2)
								fullSetCount++;
						} break;
					case Property.COLOR_PURPLE:
					case Property.COLOR_PINK:
					case Property.COLOR_BLUE:
					case Property.COLOR_GREEN:
						{
							if (properties.get(i).getOwnerCount() == 3)
								fullSetCount++;
						} break;
				}
			}
		}

		return fullSetCount;
	}

	/**
	 * Advances the player's position by <code>spaces</code>. If <code>nPosition</code> reaches
	 * Game.SPACE_COUNT, it will loop position back.
	 * @param spaces Number of spaces to advance by.
	 * @return <code>true</code> if <code>this</code> player passed START or not.
	 */
	public boolean move(int spaces) {
		int prevPosition = this.nPosition;

		this.nPosition = (this.nPosition + spaces) % Board.SPACE_COUNT;

		return this.nPosition < prevPosition;
	}

	/**
	 * Moves the player's position to a certain <code>space</code> in the board. If <code>nPosition</code> reaches
	 * Game.SPACE_COUNT, it will loop position back.
	 * @param space Number of spaces to advance by.
	 * @return <code>true</code> if <code>this</code> player passed START.
	 */
	public boolean moveTo(int space) {
		int prevPosition = this.nPosition;

		this.nPosition = space % Board.SPACE_COUNT;

		return this.nPosition < prevPosition;
	}

	//tbd
	public void setPosition (int position)
	{
		this.nPosition = position;
	}

	/**
	 * Changes the ownership of the Asset <code>toTrade</code> with the <code>trader</code>'s Asset <code>offer</code>.
	 * @param otherPlayer Player to trade with
	 * @param currentSpace Asset <code>this</code> player is currently on
	 * @param offer Asset that <code>this</code> player is offering
	 */
	public void trade(Player otherPlayer, Asset currentSpace, Asset offer) // Can they trade any type of ownable or is it just Property and Property, etc. ANS: Up to you
	{
		this.add(currentSpace);
		this.removeAsset(offer);

		otherPlayer.add(offer);
		otherPlayer.removeAsset(currentSpace);

		((Property) offer).resetFootTraffic();
		((Property) currentSpace).resetFootTraffic();
	}

	@Override
	public String toString()
	{
		return this.getName();
	}

	/**
	 * Pays the given <code>amount</code> to the <code>payee</code>.
	 * @param payee Player or bank to receive amount.
	 * @param amount Money to give.
	 *
	 * @return String dialogue to display.
	 */
	@Override
	public String payTo(Payable payee, double amount) {
		dMoney -= amount;
		payee.add(amount);

		return getName() + " paid ($" + amount + ") to " + payee.toString() + ". ";
	}
}
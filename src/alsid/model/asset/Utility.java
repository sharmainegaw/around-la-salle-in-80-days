package alsid.model.asset;

import javafx.scene.image.Image;

/**
 * Class for utility. A utility's rent is dependent on the die roll
 * and how much other utilities does its owner have.
 */
public class Utility extends Asset {
	
	//...ATTRIBUTES

    private static final double PRICE = 200;
    private int                 diceRoll;
	
	

	//...CONSTRUCTOR
	
    /**
     * Constructor for a utility.
     * @param strName Name of utility.
     */
    public Utility(String strName) {
        super(strName, PRICE);
        super.setImage(new Image("/alsid/assets/tile-utility.png"));
    }

    //...SETTERS

    /**
     * Passes the dice value landed on by the current player.
     * @param dice Dice value
     */
    public void setDiceRoll (int dice)
    {
        this.diceRoll = dice;
    }
	
	
	
	//...GETTERS

    /**
     * Gets the number of utilities that the owner has.
     * @return Number of utilities owner has.
     */
    public int getOwnerCount() {
        return getOwner().getUtilities().size();
    }

    /**
     * Gets the rent multiplier to be paid when a player lands on <code>this</code> utility. Note that
     * this value still needs to be multiplied to the dice roll to get the actual rent.
     * @return Rent to be paid.
     */
    @Override
    public double getRent() {
        return (getOwnerCount() >= 2 ? 10 : 4) * getRentPermMod() * getRentTempMod();
    }

    /**
     * Returns the name of this utility. To be used in more general cases.
     * @return Name of utility.
     */
    @Override
    public String toString()
    {
        return super.getName();
    }

    /**
     * Returns a summary of the attributes of this utility.
     * @return Summary of attributes in String.
     */
    @Override
    public String getInfo() {
        String info = this.getName() + " (" + getRentTempMod() + " " + getRentPermMod() + ") ";

        if (this.isOwned())
            info += " (owned by " + this.getOwner().getName() + ")";
        else info += " (unowned)";

        info += "\nPrice: $" + this.getPrice();

        if (this.isOwned())
            info += "\nOriginal rent: $" + this.getRent() * diceRoll;

        return info;
    }
}

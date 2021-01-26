package alsid.model.asset;

import alsid.model.game.Player;
import alsid.model.space.Space;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Abstract class for the Asset. This encompasses all spaces on the board that can be bought and owned
 * by the players.
 */
public abstract class Asset implements Space {
	
	//...ATTRIBUTES

    private String strName;
    private Player owner;
    private double dPrice;
    private double dRentCollected;
    private int nPosition;
    private ImageView imgView;

    private double nRentPermMod = 1;
    private double nRentTempMod = 1;

    //...CONSTRUCTOR

    /**
     * Constructor for an Asset.
     * @param strName Name of the Asset.
     * @param dPrice Price of the Asset.
     */
    public Asset(String strName, double dPrice) {
        this.strName = strName;
        this.owner = null; // Properties are initially unowned
        this.dPrice = dPrice;
    }



	//...GETTERS

    /**
     * Gets the name of <code>this</code> Asset.
     * @return Name of Asset.
     */
    public String getName() {
        return strName;
    }

    /**
     * Gets the owner of <code>this</code> Asset.
     * @return Owner of Asset.
     */
    public Player getOwner() {
        return owner;
    }
	
	/**
     * Gets the price to buy <code>this</code> Asset.
     * @return The price of <code>this</code> Asset.
     */
    public double getPrice() {
        return dPrice;
    }

    /**
     * Abstract method to get this Asset's rent.
     * @return Rent of this asset.
     */
    public abstract double getRent();

    /**
     * Abstract method to get number of assets owned in the same group.
     * @return Number of assets that this player owns in the same group.
     */
    public abstract int getOwnerCount();

    /**
     * Method to get the image assigned to this Asset.
     * @return ImageView of this Asset.
     */
    public ImageView getImage()
    {
        return imgView;
    }

    /**
     * Method to get this Asset's position on the board.
     * @return integer position of this Asset.
     */
    public int getPosition()
    {
        return nPosition;
    }

    /**
     * Gets the price modifier to this Asset.
     * @return Rent modifier.
     */
    public double getRentPermMod() {
        return nRentPermMod;
    }

    /**
     * Gets the temporary price modifier to this Asset.
     * @return Temporary rent modifier.
     */
    public double getRentTempMod() {
        return nRentTempMod;
    }

    /**
     * Returns the various information about this asset in String form.
     * @return Information of asset.
     */
    public abstract String getInfo();

    //...SETTERS

    /**
     * Changes the owner of <code>this</code> Asset to <code>newOwner</code>.
     * @param newOwner  Player to giver ownership to.
     */
    public void setOwner(Player newOwner) {
        this.owner = newOwner;
    }

    /**
     * Sets the image in the imageview of this Asset.
     * @param img   New image to be assigned.
     */
    public void setImage(Image img)
    {
        imgView = new ImageView(img);
    }

    /**
     * Changes this Asset's position on the board.
     * @param position New position of the Asset.
     */
    public void setPosition(int position)
    {
        nPosition = position;
    }
	
	
	
	//...METHODS

    /**
     * Checks if <code>this</code> Asset is owned by a player.
     * @return <code>true</code> if owner is assigned to a player
     */
    public boolean isOwned() {
        return owner != null;
    }


    /**
     * Increases the rent collected by amount passed.
     * @param dRent Amount to increase rent collected by.
     */
    public void incRentCollected(double dRent) {
        dRentCollected += dRent;
    }

    /**
     * Resets any temporary modifiers to rent.
     */
    public void resetTempMod() {
        nRentTempMod = 1;
    }

    /**
     * Multiplies the permanent rent modifier by the amount passed.
     * @param multiplier Multiply rent by this amount.
     */
    public void modifyRentPerm(double multiplier)
    {
        nRentPermMod *= multiplier;
    }

    /**
     * Multiplies the temporary rent modifier by the amount passed.
     * @param multiplier Multiply rent by this amount.
     */
    public void modifyRentTemp(double multiplier)
    {
        nRentTempMod *= multiplier;
    }

    /**
     * Returns the message dialogue to appear when a player lands on this Asset.
     * @param player Player who landed on this asset.
     * @return String message dialogue to display.
     */
    public String onLand(Player player)
    {
        return player.getName() + " landed on " + getName() + ".";
    }

}
package alsid.model.asset;

import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Class for properties. These make up the majority of the board, and the rent for this
 * is calculated by its development level.
 */
public class Property extends Asset {
	
	//...ATTRIBUTES

    /**
     * There are a total of two gray properties in each game.
     */
    public static final int COLOR_GRAY = 1;

    /**
     * There are a total of three gray properties in each game.
     */
    public static final int COLOR_PURPLE = 2;

    /**
     * There are a total of three pink properties in each game.
     */
    public static final int COLOR_PINK = 3;

    /**
     * There are a total of three green properties in each game.
     */
    public static final int COLOR_GREEN = 4;

    /**
     * There are a total of three blue properties in each game.
     */
    public static final int COLOR_BLUE = 5;

    /**
     * There are a total of two red properties in each game.
     */
    public static final int COLOR_RED = 6;

    /**
     * There are a total of two orange properties in each game.
     */
    public static final int COLOR_ORANGE = 7;

    public static final int UNIMPROVED = 0;
    public static final int FULLY_DEVELOPED = 4;
    public static final int HOTEL = 5;

    private final int       nColor;
    private final double    dHousePrice;
    private final double[]  aRentLevels;
    private final double    nMultiplier;
    private final double    nFootMax;

    private double  dRentCollected = 0;
    private int     nFootCount = 0;
    private int     nHouseCount = 0;

    //...CONSTRUCTOR

    /**
     * Constructor for a property.
     * @param strName       Name of property.
     * @param nColor        Color group of property.
     * @param dPrice        Price to buy property.
     * @param dHousePrice   Price to develop property.
     * @param aRentLevels   Rent to be collected depending of number of houses.
     * @param nMultiplier   Multiplier for Food Count.
     * @param nPlayerCount  Number of players in the current game.
     */
    public Property(String strName, int nColor,
                    double dPrice, double dHousePrice, double[] aRentLevels,
                    double nMultiplier, int nPlayerCount) {
        super(strName, dPrice);
		this.nColor = nColor;
        this.dHousePrice = dHousePrice;
        this.aRentLevels = aRentLevels;
        this.nMultiplier = nMultiplier;
        this.nFootMax = nMultiplier * nPlayerCount;

        switch(nColor)
        {
            case COLOR_GRAY: super.setImage(new Image("/alsid/assets/tile-color-gray.png")); break;
            case COLOR_PURPLE: super.setImage(new Image("/alsid/assets/tile-color-purple.png")); break;
            case COLOR_PINK: super.setImage(new Image("/alsid/assets/tile-color-pink.png")); break;
            case COLOR_GREEN: super.setImage(new Image("/alsid/assets/tile-color-blue.png")); break;
            case COLOR_BLUE: super.setImage(new Image("/alsid/assets/tile-color-green.png")); break;
            case COLOR_RED: super.setImage(new Image("/alsid/assets/tile-color-red.png")); break;
            case COLOR_ORANGE: super.setImage(new Image("/alsid/assets/tile-color-orange.png")); break;
        }
    }



    //...GETTERS

    /**
     * Gets the color group of <code>this</code> property.
     * @return Color group of property.
     */
    public int getColor() {
        return nColor;
    }

    /**
     * Gets the String Hex equivalent of this property's color.
     * @return String of the Hex of the color.
     */
    public String getHexColor()
    {
        switch(nColor)
        {
            case Property.COLOR_GRAY: return "#565c5a";
            case Property.COLOR_PURPLE: return "#8e4fab";
            case Property.COLOR_PINK: return "#f5a2e4";
            case Property.COLOR_BLUE: return "#3854a1";
            case Property.COLOR_GREEN: return "#38a164";
            case Property.COLOR_RED: return "#991c31";
            case Property.COLOR_ORANGE: return "#cf9211";
        }
        return "";
    }

    /**
     * Gets the price to be paid when <code>this</code> property is developed.
     * @return Price to build house.
     */
    public double getHousePrice() {
        return dHousePrice;
    }

    /**
     * Gets the array of base rents to be paid when a player lands on a property.
     * @return Array of rent prices.
     */
    public double[] getRentLevel () {
        return aRentLevels;
    }

    /**
     * Gets multiplier for foot traffic.
     * @return Foot traffic multiplier.
     */
    public double getMultiplier() {
        return nMultiplier;
    }

    /**
     * Gets the maximum foot traffic for <code>this</code> property.
     * @return Max foot traffic.
     */
    public double getFootMax() {
        return nFootMax;
    }

    /**
     * Gets the total rent collected for <code>this</code> property.
     * @return Rent collected from <code>this</code> property.
     */
    public double getRentCollected() {
        return dRentCollected;
    }

    /**
     * Gets the number of times players have landed on <code>this</code> property.
     * @return Foot traffic of <code>this</code> property.
     */
    public int getFootCount() {
        return nFootCount;
    }

    /**
     * Gets the number of times <code>this</code> property has been developed.
     * @return  0       if property is unimproved
     *          1-4     if that amount of houses were built
     *          5       if a hotel was built
     */
    public int getHouseCount() {
        return nHouseCount;
    }

    // Getters for implied values

    /**
     * Gets the rent to be paid when a player lands on <code>this</code> property.
     * @return Rent to be paid.
     */
    @Override
    public double getRent() {
        return (aRentLevels[nHouseCount] + (getOwnerCount() - 1) * 10) * getRentPermMod() * getRentTempMod();
    }

    /**
     * Gets the number of properties that the owner has of the same color group as <code>this</code> one.
     * @return Number of same-color property groups.
     */
    public int getOwnerCount() {
        ArrayList <Property> asTemp;
        if(isOwned())
        {
            asTemp = getOwner().getProperties();
            asTemp.removeIf(prop -> prop.getColor() != this.getColor());
            return asTemp.size();
        }

        return 0; // TODO Update to -1?
    }

    //...METHODS

    /**
     * Increases the foot traffic by one.
     */
    public void incFootCount() {
        nFootCount++;
    }

    /**
     * Increases the rent collected by dRent.
     */
    public void incRentCollected(double dRent) {
        dRentCollected += dRent;
    }

    /**
     * Increases the development level by one.
     */
    public void incHouseCount() {
        nHouseCount++;
    }

    /**
     * Checks if <code>this</code> property can be developed.
     * @return <code>true</code> if property can be developed.
     */
    public boolean canDevelop() {
        /*
        Get each color the player owns and check its hotel count
        */
        boolean canDevelop = true;

        if(this.getHouseCount() == FULLY_DEVELOPED)
        {
            ArrayList <Property> asTemp = new ArrayList <>(); // Get all properties
            getOwner().getAssets().forEach(asset -> {
                if (asset instanceof Property) {
                    asTemp.add((Property) asset);
                }
            });
            asTemp.removeIf(prop -> prop.getColor() != this.getColor()); // Remove all properties that are not the same color

            for (Property property : asTemp) {
                if (property.getHouseCount() != FULLY_DEVELOPED) {
                    canDevelop = false;
                    break;
                }
            }
        }

        if (this.getHouseCount() >= HOTEL || // Can't develop past hotel
            this.getFootCount() < getFootMax()) // Has not reached foot traffic limit
        {
            canDevelop = false;
        }

        return canDevelop;
    }



    /**
     * Resets cumulative foot traffic to zero. This function is called when properties are traded as
     * foot traffic is not kept between players.
     */
    public void resetFootTraffic()
    {
        nFootCount = 0;
    }

    /**
     * Returns this property's name along with its development level
     * @return String form of it's name and number of houses
     */
    @Override
    public String toString()
    {
        //if hotel isn't fully developed, display number of houses
        if(getHouseCount() <= 4)
            return super.getName() + "\n" + getHouseCount() + "\uD83C\uDFE0";

        //if hotel is fully developed, display hotel icon
        return super.getName() + "\n\uD83C\uDFE8";
    }

    /**
     * Returns a summary of the property's attributes in String
     * @return String form of property attributes
     */
    @Override
    public String getInfo()
    {
        String info = this.getName() + " (" + getRentTempMod() + " " + getRentPermMod() + ")";

        if (this.isOwned())
             info += " (owned by " + this.getOwner().getName() + ")";
        else info += " (unowned)";

        if(this.getHouseCount() <= FULLY_DEVELOPED)
            info += "\n" + this.getHouseCount() + "\uD83C\uDFE0";
        else info += "\n\uD83C\uDFE8";

        info += "\nPurchase Price: $" + this.getPrice() +
                "\nDevelop Price: $" + this.getHousePrice();

        if(this.isOwned())
        {
            info += "\nFoot count: " + this.getFootCount() + " / " + this.getFootMax();

            if(this.canDevelop())
                info += "\nCan develop: YES";
            else info += "\nCan develop: NO";

            info += "\nRent: $" + this.getRent() +
                    "\nCollected: $" + this.getRentCollected();
        }

        return info;
    }
}

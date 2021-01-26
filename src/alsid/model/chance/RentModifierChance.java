package alsid.model.chance;

import alsid.model.asset.Asset;
import alsid.model.asset.Property;

/**
 * Class for chance cards that modify the rent of an asset.
 */
public class RentModifierChance extends Chance implements AssetApplicable {

    private boolean isTemp;
    private double modifier;

    /**
     * Construct for rent modifying cards.
     * @param effect Specific effect.
     */
    public RentModifierChance(int effect)
    {
        super(effect);

        switch(effect)
        {
            case Chance.DOUBLE_RENT:
            {
                isTemp = true;
                modifier = 2;
                setText("DOUBLE RENT!\nApply this card to a property you own to double its rent for the next player who lands on it!");
            } break;
            case Chance.RENOVATION:
            {
                isTemp = false;
                modifier = 1.5;
                setText("RENOVATE A PROPERTY!\nApply this card to a property you own and pay its renovation cost to increase its rent by 50%!");
            } break;
            case Chance.DILAPIDATED:
            {
                isTemp = false;
                modifier = 0.9;
                setText("Dilapidated houses...\nThe rent of a property you own decreases by 10%.");
            } break;
            case Chance.UTIL_RAIL_INC:
            {
                isTemp = false;
                modifier = 1.1;
                setText("Lucky!\nThe rent of a utility or railroad you own increases by 10%!");
            } break;
            case Chance.UTIL_RAIL_DEC:
            {
                isTemp = false;
                modifier = 0.9;
                setText("Unlucky...\nThe rent of a utility or railroad you own decreases by 10%.");
            } break;
        }
    }

    /**
     * Changes the asset modifier to the specific amount.
     * @param asset Asset to affect.
     * @return String message of event.
     */
    @Override
    public String useEffect(Asset asset)
    {
        if (isTemp)
            asset.modifyRentTemp(modifier);
        else
            asset.modifyRentPerm(modifier);

        if(getEffect() == Chance.RENOVATION)
        {
            asset.getOwner().add((-25 * ((Property) asset).getHouseCount()) +
                    (-50 * (((Property) asset).getHouseCount() < 5 ? 0: ((Property) asset).getHouseCount() - 4)));
            return "Paid renovation costs. " + asset.getName() + "'s rent is multiplied by " + modifier + "x.";
        }

        return asset.getName() + "'s rent is multiplied by " + modifier + "x.";
    }
}

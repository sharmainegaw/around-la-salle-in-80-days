package alsid.model.space;

import alsid.model.game.Player;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class for the community service space. This space makes the player pay $50 to the bank when the player lands on it.
 */
public class CommunityServiceSpace implements Space {

    private int nPosition = 16;
    private ImageView imgView;

    /**
     * Constructor for the community service space.
     */
    public CommunityServiceSpace()
    {
        imgView = new ImageView(new Image("/alsid/assets/tile-empty.png"));
    }

    /**
     * Method to get this CommunityService space's position on the board.
     * @return integer position of this CommunityService space.
     */
    public int getPosition()
    {
        return nPosition;
    }

    /**
     * Changes this ChanceSpace's position on the board.
     * @param position   New position of the ChanceSpace.
     */
    public void setPosition(int position)
    {
        nPosition = position;
    }

    /**
     * Method to get the image assigned to this CommunityService space.
     * @return ImageView of this CommunityService space.
     */
    @Override
    public ImageView getImage()
    {
        return this.imgView;
    }

    /**
     * Deducts $50 from the <code>player</code> that lands on <code>this</code> space and gives it to the <code>bank</code>.
     * @param player Player that landed on this space.
     */
    @Override
    public String onLand(Player player) {
        return player.getName() + " landed on " + toString() + "! ";
    }

    /**
     * Getter for the payment that the player has to give when they land on this space.
     * @return Payment when landed on ($50).
     */
    public double getTax()
    {
        return 50;
    }

    /**
     * Returns the String form of this space.
     * @return Name of this space.
     */
    @Override
    public String toString()
    {
        return "CHURCH";
    }

}

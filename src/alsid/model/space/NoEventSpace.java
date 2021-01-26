package alsid.model.space;

import alsid.model.game.Player;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class template for Spaces that do nothing when a player lands on them (e.g. START, Free Parking)
 */
public class NoEventSpace implements Space {

    private String      strName;
    private int         nPosition;
    private ImageView imgView;

    /**
     * Constructor for no event spaces.
     * @param strName Name of this space.
     */
    public NoEventSpace(String strName)
    {
        this.strName = strName;
        imgView = new ImageView(new Image("/alsid/assets/tile-empty.png"));
    }

    /**
     * Method to get this space's position on the board.
     * @return integer position of this space.
     */
    public int getPosition()
    {
        return nPosition;
    }

    /**
     * Method to get the image assigned to this space.
     * @return ImageView of this space.
     */
    @Override
    public ImageView getImage()
    {
        return this.imgView;
    }

    /**
     * Changes this space's position on the board.
     * @param position   New position of the space.
     */
    public void setPosition(int position)
    {
        nPosition = position;
    }

    /**
     * These spaces do not affect the player.
     * @param player Player that landed on this space.
     * @return String message of event.
     */
    @Override
    public String onLand(Player player) {
        return player.getName() + " landed on the " + strName + " space. ";
    }

    /**
     * Returns the String form of this space.
     * @return Name of this space.
     */
    @Override
    public String toString()
    {
        return strName;
    }

}

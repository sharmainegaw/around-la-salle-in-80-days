package alsid.model.space;

import alsid.model.game.Player;
import javafx.scene.image.ImageView;

/**
 * Interface for a space. These make up the board of the game.
 */
public interface Space {

    /**
     * Effect to apply when a player lands on this space.
     * @param player Player that landed on this space.
     * @return String form of this event.
     */
    String onLand(Player player);

    /**
     * Sets the position of this space on the board.
     * @param position Index of position on the board.
     */
    void setPosition(int position);

    /**
     * Getter for this space's position.
     * @return Position of this space.
     */
    int getPosition();

    /**
     * Getter for the ImageView of this space.
     * @return Image of this space.
     */
    ImageView getImage();
}
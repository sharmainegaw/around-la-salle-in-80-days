package alsid.model.chance;

import alsid.model.asset.Asset;
import alsid.model.game.Board;
import alsid.model.game.Player;
import alsid.model.space.Space;

import java.util.*;

/**
 * Class template for cards that move the player to a certain asset (property, utility, railroad).
 */
public class MoveToSpaceChance extends Chance implements PlayerApplicable{

    private Space   space;
    private Board   board;
    private int     spaceLocation;
    private boolean canCollectStart;
    private boolean playerCollectedStart;

    /**
     * Constructor of chance cards that move the player to another space in the board.
     * @param effect Specific effect.
     * @param board Board to refenence.
     */
    public MoveToSpaceChance(int effect, Board board)
    {
        super(effect);
        this.board = board;
    }

    /**
     * Gets a list of spaces of the chosen type, then assigns it.
     * @param spaceType Type of space to get.
     * @param board Board to reference.
     */
    private void assignRandomSpace(int spaceType, Board board)
    {
        Random rand = new Random();

        ArrayList <Space> filteredSpaces = board.filterSpaceType(spaceType);
        this.space = filteredSpaces.get(rand.nextInt(filteredSpaces.size()));
        this.spaceLocation = board.getSpaces().lastIndexOf(space);
    }

    /**
     * Returns whether or not the player collected $200 from START.
     * @return <code>true</code> if player collected start.
     */
    public boolean playerCollectedStart()
    {
        return playerCollectedStart;
    }

    /**
     * Moves the player to the chosen spot and triggers the landing event.
     * @param player Player affected.
     * @return String version of event.
     */
    @Override
    public String useEffect(Player player) {
        if (!isUsed)
        {
            switch (getEffect())
            {
                case Chance.GO_TO_PROP:
                {
                    canCollectStart = false;
                    assignRandomSpace(Board.PROPERTY, board);
                    setText("You have a class at " + ((Asset) space).getName() + ". If you pass CADS, do not collect $200.");
                } break;

                case Chance.GO_TO_UTIL:
                {
                    canCollectStart = true;
                    space = board.getSpaces().get(board.getNearestUtility(player));
                    spaceLocation = space.getPosition();
                    setText("You need to request something from " + ((Asset) space).getName() + ". Collect $200 if you pass CADS.");
                } break;

                case Chance.GO_TO_RAIL:
                {
                    canCollectStart = true;
                    space = board.getSpaces().get(board.getNearestRailroad(player));
                    spaceLocation = space.getPosition();
                    setText("Pass by " + ((Asset) space).getName() + " on your way to class! Collect $200 if you pass CADS.");
                } break;

                case Chance.TRIP_TO_PROP:
                {
                    canCollectStart = true;
                    assignRandomSpace(Board.PROPERTY, board);
                    setText("There's an event at " + ((Asset) space).getName() + ". Collect $200 if you pass CADS.");
                } break;

                case Chance.GO_TO_START:
                {
                    canCollectStart = true;
                    space = board.getSpaces().get(Board.START);
                    spaceLocation = Board.START;
                    setText("Meet up with someone at CADS! Collect $200.");
                } break;

                case Chance.GO_TO_JAIL:
                {
                    canCollectStart = false;
                    space = board.getSpaces().get(Board.JAIL);
                    spaceLocation = Board.JAIL;
                    setText("GO DIRECTLY TO SDFO\nDO NOT PASS CADS\nDO NOT COLLECT $200");
                } break;
            }

            if (player.moveTo(spaceLocation) && canCollectStart)
            {
                player.add(200);
                playerCollectedStart = true;
            }

            isUsed = true;
        }

        return getEffectDialogue() + "\n\n";
    }
}

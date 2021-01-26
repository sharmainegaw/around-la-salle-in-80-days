package alsid.model.chance;

import alsid.model.game.Player;

/**
 * Abstract class for the Chance cards.
 */
public abstract class Chance
{
    //...ATTRIBUTES
    
    public static final int 
        GET_OUT_OF_JAIL = 1,
        GO_TO_PROP      = 2,
        GO_TO_UTIL      = 3,
        GO_TO_RAIL      = 4,
        BANK_DIVIDEND   = 5,
        TAX_REFUND      = 6,
        GO_TO_START     = 7,
        BIRTHDAY        = 8,
        WIN_COMPETITION = 9,
        GO_TO_JAIL      = 10,
        TRIP_TO_PROP    = 11,
        DOUBLE_RENT     = 12,
        RENOVATION      = 13,
        DILAPIDATED     = 14,
        UTIL_RAIL_INC   = 15,
        UTIL_RAIL_DEC   = 16,
        DONATE_MONEY    = 17,
        PAY_TAXES       = 18;
    
    public static final int ALREADY_USED = -1;
	
	private final int 	nEffect;
	private Player 		owner;
	protected boolean 	isUsed = false;
	private String 		strDialogue;

	//...CONSTRUCTOR

    /**
     * Constructor for a chance card. This is mainly called from a subclass.
     * @param effect Effect of this chance card.
     */
    public Chance(int effect)
    {
    	nEffect = effect;
    	owner = null;
    }

    /**
     * Another constructor for a chance card, but with a String dialogue to display.
     * @param effect Effect of this chance card.
     * @param text Dialogue text to display.
     */
    public Chance(int effect, String text)
    {
        this(effect);
        strDialogue = text;
    }

    //...GETTERS
    
	/**
     * Gets the effect of <code>this</code> chance card.
     * @return The corresponding number of the effect of <code>this</code> card.
     */
    public int getEffect()
    {
    	return nEffect;
    }
    
	/**
     * Gets the dialogue for <code>this</code> chance card.
     * @return The respective string dialogue of <code>this</code> chance card.
     */
    public String getEffectDialogue()
    {
    	return strDialogue;
    }  


    
    //...METHODS

    /**
     * Checks if <code>this</code> card is still in the deck in the game.
     * @return  <code>true</code> if the card is still in the deck,
     *          <code>false</code> otherwise, usually if it is already used or
     *                  in possession by another player
     */
    public boolean inDeck()
    {
    	return owner != null;
    }

    /**
     * Gives <code>this</code> card to a <code>player</code>.
     * @param player model.game.Player to give card to.
     */
    public void giveTo(Player player) {
        if (this.owner != null) {
            owner.getChanceCards().remove(this);
        }
        player.add(this);
        this.owner = player;
    }

    /**
     * Checks if the card's effect if of a certain type.
     * @param type Type to be checked.
     * @return <code>true</code> if effects match.
     */
    public boolean isType(int type)
    {
        return this.nEffect == type;
    }

    /**
     * Sets the dialogue of this card to the <code>text</code> provided.
     * @param text New text for <code>this</code> card.
     */
    public void setText(String text)
    {
        this.strDialogue = text;
    }

    /**
     * Discards the card.
     */
    public void discard()
    {
        owner = null;
    }

    /**
     * Checks if this card is owned by a player or not.
     * @return <code>true</code> if owner does not return nulln
     */
    public boolean isOwned()
    {
        return owner != null;
    }
}
package alsid.model.game;

import java.util.*;

import alsid.model.chance.*;

public class Deck {
	
	//...ATTRIBUTES

    private ArrayList <Chance> cards = new ArrayList <Chance>();
    private ArrayList <Chance> used = new ArrayList <Chance>();

	
	
	//...CONSTRUCTOR

    /**
     * Constructor for the starting deck in the game.
     */
    public Deck() {
    }

    /**
     * Initializes the chance cards.
     * @param game To reference the board and bank for certain chance cards.
     */
	public void initChance(Game game)
    {
        Random rand = new Random();

        for (int i = 0; i < 2; i++)
        {
            cards.add(new GetOutOfJailChance());
        }

        for (int i = 0; i < 6; i++)
        {
            cards.add(new MoveToSpaceChance(Chance.GO_TO_PROP + rand.nextInt(3), game.getBoard()));
        }

        for (int i = 0; i < 6; i++)
        {
            int temp = Chance.BANK_DIVIDEND + rand.nextInt(5);
            if (temp == Chance.GO_TO_START)
            {
                cards.add(new MoveToSpaceChance(temp, game.getBoard()));
            } else {
                cards.add(new ChangeMoneyChance(temp, game.getBank()));
            }
        }

        for (int i = 0; i < 4; i++)
        {
            cards.add(new MoveToSpaceChance(Chance.GO_TO_JAIL + rand.nextInt(2), game.getBoard()));
        }

        for (int i = 0; i < 7; i++)
        {
            cards.add(new RentModifierChance(Chance.DOUBLE_RENT + rand.nextInt(5)));
        }

        for (int i = 0; i < 3; i++)
        {
            cards.add(new ChangeMoneyChance(Chance.DONATE_MONEY + rand.nextInt(2), game.getBank()));
        }

        shuffle();
    }
	
	//...GETTERS
	
	/**
     * Gets the number of cards left in <code>this</code> deck.
     * @return Number of card that has not been drawn yet.
     */
    public int size() {
        return cards.size();
    }
	
	//...METHODS

    /**
     * Adds all of the cards that were drawn and not kept by players
     * (i.e. Get out of jail free cards) back into the deck.
     */
    public void resetUnowned() {
        used.forEach(card -> {
            if (!card.isOwned())
            {
                cards.add(card);
            }
        });
        used.removeAll(cards);
        shuffle();
    }

    /**
     * Shuffles <code>this</code> deck.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Removes the topmost item and returns it.
     * @return Top card of deck; <code>null</code> if deck is empty
     */
    public Chance draw() {
        if (!cards.isEmpty()) {
            Chance chnTemp = cards.remove(0);
            used.add(chnTemp);
            return chnTemp;
        }
        return null;
    }

    /**
     * Checks if the cards <code>ArrayList</code> is empty.
     * @return <code>true</code> if there are no more cards in the deck
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

}

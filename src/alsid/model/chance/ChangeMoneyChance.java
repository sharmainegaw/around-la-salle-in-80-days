package alsid.model.chance;

import alsid.model.game.Bank;
import alsid.model.game.Player;

import java.util.Random;

/**
 * Class for chance cards that alter the players money.
 */
public class ChangeMoneyChance extends Chance implements PlayerApplicable {

    private int amount;
    private Bank bank = null;

    /**
     * Constructor for chance cards that alter the players money.
     * @param effect Specific type of effect.
     * @param bank Bank to get money from, if applicable.
     */
    public ChangeMoneyChance(int effect, Bank bank)
    {
        super(effect);

        Random rand = new Random();

        switch (effect)
        {
            case Chance.BANK_DIVIDEND:
            {
                amount = 50;
                this.bank = bank;
                setText("Congratulations! Bank pays dividend of $50.");
            } break;

            case Chance.TAX_REFUND:
            {
                amount = 100;
                this.bank = bank;
                setText("Tax refund! Collect $100 from bank.");
            } break;

            case Chance.BIRTHDAY:
            {
                amount = 300;
                setText("Happy birthday! You got $300 in gifts.");
            } break;

            case Chance.WIN_COMPETITION:
            {
                amount = 150;
                setText("You won HackerCup! Collect $150.");
            } break;

            case Chance.DONATE_MONEY:
            {
                amount = - (rand.nextInt(10) + 1) * 20;
                setText("You donated $" + -amount + " for community development!");
            } break;

            case Chance.PAY_TAXES:
            {
                amount = - (rand.nextInt(15) + 1) * 20;
                setText("You paid $" + -amount + " in taxes.");
            } break;
        }
    }

    /**
     * Adds or removes <code>amount</code> from player.
     * @param player Player affected by effect.
     * @return String version of event.
     */
    @Override
    public String useEffect(Player player)
    {
        if (bank == null) {
            player.add(amount);
            return getEffectDialogue() + "\n\n" + player.getName() + ((amount > 0)? " got " + amount: " lost " + -amount) + ". ";
        } else {
            return getEffectDialogue() + "\n\n" + bank.payTo(player, amount);
        }
    }
}

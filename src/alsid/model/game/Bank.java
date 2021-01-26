package alsid.model.game;

/**
 * Class for the bank of the game. The bank contains a bank of money that players get from in certain events.
 * The game ends when <code>money</code> falls below zero.
 */
public class Bank implements Payable {

    private double money;

    /**
     * Constructor for a bank. The bank starts with money proportional to the number of players.
     * @param nPlayerCount Number of players.
     */
    public Bank(int nPlayerCount)
    {
        money = 2500 * nPlayerCount;
    }

    /**
     * Gives the player money from the bank.
     * @param payee Person to receive money.
     * @param amount Amount to give.
     * @return String form of this event.
     */
    @Override
    public String payTo(Payable payee, double amount) {
        money -= amount;
        payee.add(amount);

        return payee.toString() + " collected $" + amount + " from " + toString() + ". ";
    }

    /**
     * Adds this amount to <code>money</code>.
     * @param amount Amount to add.
     */
    @Override
    public void add(double amount) {
        money += amount;
    }

    /**
     * Checks if this bank is bankrupt. This is one of the ways the game can end.
     * @return <code>true</code> if money is empty or negative.
     */
    @Override
    public boolean isBankrupt() {
        return money <= 0;
    }

    /**
     * Getter for money.
     * @return Current amount bank has.
     */
    public double getMoney() {
        return money;
    }

    /**
     * Setter for money.
     * @param money Amount to set money to.
     */
    public void setMoney(double money)
    {
        this.money = money;
    }

    /**
     * Returns the String form of the name.
     * @return String form of bank.
     */
    public String toString()
    {
        return "The Bank";
    }
}

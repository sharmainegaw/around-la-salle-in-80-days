package alsid.model.game;

/**
 * Interface for entities that can give money.
 */
public interface Payable {

    /**
     * Pays the given <code>amount</code> to the <code>payee</code>.
     * @param payee Entity to receive money.
     * @param amount Amount to give.
     * @return String form of event.
     */
    String payTo(Payable payee, double amount);

    /**
     * Adds the given <code>amount</code> to the entity's reserve.
     * @param amount Amount to add.
     */
    void add(double amount);

    /**
     * Checks if the entity is bankrupt.
     * @return <code>true</code> if entity is bankrupt.
     */
    boolean isBankrupt();

}

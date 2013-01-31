package pjdci.examples.moneytransfer.data;

import pjdci.core.AbstractRolePlayer;

/**
 * A simple data class extending <code>AbstractRolePlayer</code>
 * to gain the capability of playing a role in a DCI context.
 */
public class Account extends AbstractRolePlayer {
    private int balance;

    public Account(int initialBalance) {
        balance = initialBalance;
    }

    public int getBalance() {
        return balance;
    }

    public void increaseBalance(int amount) {
        balance += amount;
    }

    public void decreaseBalance(int amount) {
        balance -= amount;
    }

    @Override
    public boolean objectEquals(Object o) {
        // No implementation is needed since the object do not have any identity fields
        // and equality is not an issue in this very simple money transfer example.
        throw new UnsupportedOperationException("Not implemented");
    }
}

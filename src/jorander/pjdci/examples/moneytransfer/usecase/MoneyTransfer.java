package jorander.pjdci.examples.moneytransfer.usecase;

import jorander.pjdci.core.Role;
import jorander.pjdci.examples.moneytransfer.data.Account;

/**
 * The DCI context of the well known money transfer example. It is
 * a very simple example only meant to give an idea of the syntax
 * and conventions used in this DCI implementation.
 */
public class MoneyTransfer {
    private final int amount;  // Not a role, just a data value used in the context.
    // Role identifiers
    private final Source Source;
    private final Destination Destination;

    public MoneyTransfer(Account source, Account destination, int amount) {
        this.amount = amount;
        // Role binding
        Source = new Source(source);
        Destination = new Destination(destination);
    }

    // Entry-point to the context
    public void execute() {
        Source.Transfer(amount);
    }

    // Role implementations
    private class Source extends Role<Account> {     // "Account" being the role contract

        // Unfortunately necessary boiler-plate constructor. Should never contain any more functionality
        // since a role always is stateless. State is being kept either in the context or, more commonly,
        // in the role player.
        public Source(Account source) {
            super(source);
        }

        // Role method implementation.
        public void Transfer(int amount){
            Destination.deposit(amount); // Role identifiers from the context are being used for clarity.
            Source.withdraw(amount);
        }

        private void withdraw(int amount) {
            // The rolePlayer-field of the Role super-class needs to be referenced to call
            // methods found on the role player, i.e. defined by the role contract.
            Source.rolePlayer.decreaseBalance(amount);
        }
    }

    private class Destination extends Role<Account> {

        public Destination(Account destination) {
            super(destination);
        }

        public void deposit(int amount) {
            Destination.rolePlayer.increaseBalance(amount);
        }
    }
}

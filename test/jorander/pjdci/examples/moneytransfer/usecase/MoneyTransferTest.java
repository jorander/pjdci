package jorander.pjdci.examples.moneytransfer.usecase;

import jorander.pjdci.examples.moneytransfer.data.Account;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class MoneyTransferTest {

    @Test
    public void transfersAmountFromSourceAccountToDestinationAccount(){
        final int initialSourceAmount = 10;
        final int initialDestinationAmount = 1;
        final int transferAmount = 6;
        final Account source = new Account(initialSourceAmount);
        final Account destination = new Account(initialDestinationAmount);
        final MoneyTransfer instance = new MoneyTransfer(source, destination, transferAmount);

        instance.execute();

        assertEquals("Balance of source account should be decreased by transfer amount", initialSourceAmount - transferAmount, source.getBalance());assertEquals("Balance of source amount should be decreased by transfer amount", initialSourceAmount - transferAmount, source.getBalance());
        assertEquals("Balance of destination account should be increased by transfer amount", initialDestinationAmount + transferAmount, destination.getBalance());assertEquals("Balance of source amount should be decreased by transfer amount", initialSourceAmount - transferAmount, source.getBalance());
    }
}

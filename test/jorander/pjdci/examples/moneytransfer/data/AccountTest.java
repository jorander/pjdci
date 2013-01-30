package jorander.pjdci.examples.moneytransfer.data;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class AccountTest {

    @Test
    public void returnsBalance(){
        final int initialBalance = 5;
        Account instance = new Account(initialBalance);
        assertEquals("Initial balance to be returned", initialBalance, instance.getBalance());
    }

    @Test
    public void updatesBalanceWhenIncreased(){
        final int initialBalance = 3;
        final int increase = 5;
        Account instance = new Account(initialBalance);
        instance.increaseBalance(increase);
        assertEquals("Balance should hold sum of initial balance and increase", initialBalance + increase, instance.getBalance());
    }

    @Test
    public void updatesBalanceWhenDecreased(){
        final int initialBalance = 5;
        final int decrease = 3;
        Account instance = new Account(initialBalance);
        instance.decreaseBalance(decrease);
        assertEquals("Balance should hold rest of initial balance and decreaseđ", initialBalance - decrease, instance.getBalance());
    }
}

// Import Account Class
import server.Account;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

// Import JUnit
import org.junit.Test;

public class TestAccount {
    static final Account clientAccount1 = new Account(0, 5000);
    static final Account clientAccount2 = new Account(1, 7500);

    @Test
    public void testWithdraw(){
        // Assert Default
        assertTrue("Id for client0 is not the same", clientAccount1.getId() == 0);
        assertTrue("Id for client0 is not the same", clientAccount2.getId() == 1);

        clientAccount1.setBalance(5000);
        clientAccount2.setBalance(7500);
        assertTrue("Balance for client0 is not the same", clientAccount1.getBalance() == 5000);
        assertTrue("Balance for client0 is not the same", clientAccount2.getBalance() == 7500);
        
        // Withdraw from 1 Account
        clientAccount1.withdraw(500);
        assertFalse("Balance for client0 is not the same", clientAccount1.getBalance() == 5000);
        assertTrue("Balance for client0 is not the same", clientAccount1.getBalance() == 4500);


        // Withdraw 2 Accounts
        clientAccount1.withdraw(500);
        clientAccount2.withdraw(3500);
        assertTrue( "Balance for client0 is not the same", clientAccount1.getBalance() == 4000);
        assertTrue("Different balance", clientAccount1.getBalance() == clientAccount2.getBalance());
    }

    @Test
    public void testDeposit(){
        // Assert Default
        assertTrue("Id for client0 is not the same", clientAccount1.getId() == 0);
        assertTrue("Id for client0 is not the same", clientAccount2.getId() == 1);

        clientAccount1.setBalance(5000);
        clientAccount2.setBalance(7500);
        assertTrue("Balance for client0 is not the same", clientAccount1.getBalance() == 5000);
        assertTrue("Balance for client0 is not the same", clientAccount2.getBalance() == 7500);
        
        // Withdraw from 1 Account
        clientAccount1.deposit(500);
        assertFalse("Balance for client0 is not the same", clientAccount1.getBalance() == 5000);
        assertTrue("Balance for client0 is not the same", clientAccount1.getBalance() == 5500);


        // Withdraw 2 Accounts
        clientAccount1.deposit(3000);
        clientAccount2.deposit(1000);
        assertTrue( "Balance for client0 is not the same", clientAccount1.getBalance() == 8500);
        assertTrue("Different balance", clientAccount1.getBalance() == clientAccount2.getBalance());
    }


}
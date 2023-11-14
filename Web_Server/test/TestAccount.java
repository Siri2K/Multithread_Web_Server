// Import Account Class
import server.Account;

// Import JUnit
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestAccount{
    static final Account clientAccount1 = new Account(0, 5000);
    static final Account clientAccount2 = new Account(1, 7500);

    @Test
    void testWithdraw(){
        // Assert Default
        assertEquals(0,clientAccount1.getId(), "Id for client0 is not the same");
        assertEquals(1,clientAccount2.getId(), "Id for client1 is not the same");
        
        assertEquals(5000,clientAccount1.getBalance(), "Balance for client0 is not the same");
        assertEquals(7500,clientAccount2.getBalance(), "Balance for client1 is not the same");

        // Withdraw from 1 Account
        clientAccount1.withdraw(500);
        assertEquals(4500,clientAccount1.getBalance(), "Balance for client0 is not the same");


        // Withdraw 2 Accounts
        clientAccount1.withdraw(500);
        clientAccount2.withdraw(3500);
        assertEquals(4000,clientAccount1.getBalance(), "Balance for client0 is not the same");
        assertEquals(clientAccount1.getBalance(),clientAccount2.getBalance(), "Different balance");
    }

    @Test
    void testDeposit(){
        // Assert Default
        assertEquals(0,clientAccount1.getId(), "Id for client0 is not the same");
        assertEquals(1,clientAccount2.getId(), "Id for client1 is not the same");
        
        assertEquals(5000,clientAccount1.getBalance(), "Balance for client0 is not the same");
        assertEquals(7500,clientAccount2.getBalance(), "Balance for client1 is not the same");

        // Withdraw from 1 Account
        clientAccount1.deposit(500);
        assertEquals(5500,clientAccount1.getBalance(), "Balance for client0 is not the same");


        // Withdraw 2 Accounts
        clientAccount1.deposit(3000);
        clientAccount2.deposit(1000);
        assertEquals(8500,clientAccount1.getBalance(), "Balance for client0 is not the same");
        assertEquals(clientAccount1.getBalance(),clientAccount2.getBalance(), "Different balance");
    }


}
package fall2018.csc2017.GameCentre;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class AccountManagerTest {
    private AccountManager accountManager;

    private List<Account> users = new ArrayList<>();

    @Before
    public void setUp() {
        Account user1 = new Account("user1", "pwd1");
        Account user2 = new Account("user2", "pwd2");
        Account user3 = new Account("user3", "pwd3");
        Account user4 = new Account("user4", "pwd4");
        Account user5 = new Account("user5", "pwd5");

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        accountManager = new AccountManager(users);
    }

    /**
     * Test if isExistingUser returns true if the user exists
     */
    @Test
    public void isExistingUserTrueTest() {
        assertTrue(String.format("Expected %b but got %b", true, false), accountManager.isExistingUser("user1"));
    }

    /**
     * Test if isExistingUser returns False if the user exists
     */
    @Test
    public void isExistingUserFalseTest() {
        assertFalse(String.format("Expected %b but got %b", false, true), accountManager.isExistingUser("user6"));
    }

    /**
     * Test if addUser adds the user to accountManager
     */
    @Test
    public void addUserTest() {
        Account user6 = new Account("user6", "pwd6");
        accountManager.addUser(user6);
        assertTrue(String.format("Expected %b but got %b", true, false), accountManager.isExistingUser("user6"));
    }

}
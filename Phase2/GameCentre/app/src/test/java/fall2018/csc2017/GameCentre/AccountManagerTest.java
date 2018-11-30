package fall2018.csc2017.GameCentre;

import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

public class AccountManagerTest {
    private AccountManager accountManager;

    @Before
    public void setUp() {
        List<Account> users = new ArrayList<>();
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
}

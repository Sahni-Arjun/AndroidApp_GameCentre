package fall2018.csc2017.GameCentre;

import java.io.Serializable;
import java.util.List;

/**
 * This is an AccountManager class. This AccountManager stores usernames and passwords
 * and aids log in procedures as well as any account managing activities.
 */
class AccountManager implements Serializable {

    /**
     * List of all accounts in the app.
     */
    private List<Account> allAccounts;

    static final long serialVersionUID = 6469620024832591618L;

    /**
     * Create a new account manager with the given list of accounts.
     *
     * @param accounts the list of accounts
     */
    AccountManager(List<Account> accounts) {
        this.allAccounts = accounts;
    }

    /**
     * Return true iff user exists.
     *
     * @param username user's username
     * @return true or false if user is an existing user
     */
    boolean isExistingUser(String username) {
        if (allAccounts != null) {
            for (Account user : allAccounts) {
                if (user.getUsername().equals(username)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Add a new user to the manager.
     *
     * @param newUser a new user
     */
    void addUser(Account newUser) {
        if (!(isExistingUser(newUser.getUsername()))) {
            allAccounts.add(newUser);
        }
    }

    /**
     * Return True iff sign in was successful that is, the username and password is correct.
     *
     * @return whether or not login was successful
     */
    boolean logInSuccessful(Account user) {
        if (!isExistingUser(user.getUsername())) {
            return false;
        }
        Account sameUsername = this.findUser(user.getUsername());
        return this.isSamePassword(user, sameUsername);
    }

    /**
     * Returns true if the input password is correct.
     *
     * @param user newly entered user
     * @param otherUser the existing user
     * @return true or false
     */
    boolean isSamePassword(Account user, Account otherUser) {
        return user.getPassword().equals(otherUser.getPassword()) &&
                user.getUsername().equals(otherUser.getUsername());
    }

    /**
     * Return the user with username
     *
     * @return account which occurring to the
     */
    Account findUser(String username) {
        for (Account user : allAccounts) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}

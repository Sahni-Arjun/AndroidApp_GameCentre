package fall2018.csc2017.GameCentre;

import java.io.Serializable;

/**
 * User Account that contains login info and game state info.
 */
class Account implements Serializable {

    static final long serialVersionUID = 4860302647072124829L;

    /**
     * User's username and password.
     */
    private String username, password;

    /**
     * User's save data.
     */
    private SaveManager saveManager = new SaveManager();

    /**
     * Creates a new User Account with username and password.
     *
     * @param username User's username
     * @param password User's password
     */
    Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Return the user's save data.
     *
     * @return the user's save data
     */
    SaveManager getSaveManager() {
        return saveManager;
    }

    /**
     * Return the username of the account object.
     *
     * @return account object's username
     */
    String getUsername() {
        return this.username;
    }

    /**
     * Return the user's password.
     *
     * @return user's password.
     */
    private String getPassword() {
        return this.password;
    }

    /**
     * Returns true if the input password is correct.
     *
     * @param other newly entered pw
     * @return true or false
     */
    boolean isSamePassword(Account other) {
        return this.password.equals(other.getPassword()) && this.getUsername().equals(other.getUsername());
    }

    /**
     * Check if the account is a valid account.
     *
     * @return boolean
     */
    boolean isValidAccount() {
        return !(this.getPassword().isEmpty() || this.getUsername().isEmpty());
    }

    /**
     * Set username.
     *
     * @param username username of user input
     */
    void setUsername(String username) {
        this.username = username;
    }

    /**
     * Set password.
     *
     * @param password password of user input
     */
    void setPassword(String password) {
        this.password = password;
    }
}

/*
Controller
 */
package fall2018.csc2017.GameCentre;

import android.content.Context;

/**
 * Controller for the sign up procedure for the app.
 */
class SignUpActivityController {
    /**
     * The filesystem
     */
    private FileSystem fileSystem;

    /**
     * The account for the user that is signing in.
     */
    private Account newUser = new Account("", "");

    /**
     * Creates a new controller for the activity.
     * @param fileSystem the file system.
     */
    SignUpActivityController(FileSystem fileSystem){
        this.fileSystem = fileSystem;
    }

    /**
     * Waits for the user to press enter and creates a new account for the user if input fields are
     * valid.
     * @param currentContext the current activity.
     * @param username the desired username.
     * @param password the desired password.
     * @return toast message if the account was made.
     */
    String enterButtonListener(Context currentContext, String username, String password){
        String message = addSignUpFillIn(username, password);
        if (newUser != null) {
            if (!(newUser.getUsername().isEmpty() || newUser.getPassword().isEmpty())) {
                fileSystem.testExistingFile(currentContext);
                AccountManager accountManager = fileSystem.loadAccount(currentContext);
                if (!accountManager.isExistingUser(newUser.getUsername())) {
                    accountManager.addUser(newUser);
                    fileSystem.saveAccount(currentContext, accountManager);
                    StartingLoginActivity.currentUser = newUser.getUsername();
                    return "Loading...";
                } else {
                    return "Username exists. " +
                            "Please choose a different one";
                }
            }
        }
        return message;
    }

    /**
     * Add new user's information provided
     */
    private String addSignUpFillIn(String username, String password) {
        String wrongUsername = this.inputLimits(username, "Username");
        String wrongPassword = this.inputLimits(password, "Password");

        if (!wrongUsername.equals("Success!")) {
            return wrongUsername;
        }else{
            this.newUser.setUsername(username);
            return addSignUpFillInPassword(wrongPassword, password);
        }
    }

    /**
     * Check if password is correct.
     *
     * @param wrongPassword a flag for if the password is correct
     * @param password      the user's password
     */
    private String addSignUpFillInPassword(String wrongPassword, String password) {
        if (!wrongPassword.equals("Success!")) {
            return "Please choose a different one";
        } else {
            this.newUser.setPassword(password);
            return "";
        }
    }

    /**
     * Put limits on the username and password.
     *
     * @param userInput   the input the user enters in when signing up
     * @param typeOfInput the type of input
     * @return String invalid input message
     */
    private String inputLimits(String userInput, String typeOfInput) {
        String trimUsername = userInput.trim();
        if (trimUsername.length() != userInput.length()) {
            return "No trailing spaces in " + typeOfInput + ".";
        }
        if (trimUsername.length() < 4) {
            return typeOfInput + " should be at least 4 characters long.";
        }
        for (char ch : trimUsername.toCharArray()) {
            if (Character.isWhitespace(ch)) {
                return "No whitespaces in " + typeOfInput + ".";
            }
        }
        return "Success!";
    }
}

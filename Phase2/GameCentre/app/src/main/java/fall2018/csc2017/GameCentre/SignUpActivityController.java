package fall2018.csc2017.GameCentre;

import android.content.Context;

class SignUpActivityController {
    private FileSystem fileSystem;

    private DisplayToast displayToast;

    /**
     * The account for the user that is signing in.
     */
    private Account newUser = new Account("", "");

    SignUpActivityController(FileSystem fileSystem, DisplayToast displayToast){
        this.fileSystem = fileSystem;
        this.displayToast = displayToast;
    }

    boolean enterButtonListener(Context currentContext, String username, String password){
        addSignUpFillIn(currentContext, username, password);
        if (newUser != null) {
            if (!(newUser.getUsername().isEmpty() || newUser.getPassword().isEmpty())) {
                fileSystem.testExistingFile(currentContext);
                AccountManager accountManager = fileSystem.loadAccount(currentContext);
                if (!accountManager.isExistingUser(newUser.getUsername())) {
                    displayToast.displayToast(currentContext, "Loading...");
                    accountManager.addUser(newUser);
                    fileSystem.saveAccount(currentContext, accountManager);
                    StartingLoginActivity.currentUser = newUser.getUsername();
                    return true;
                } else {
                    displayToast.displayToast(currentContext, "Username exists. " +
                            "Please choose a different one");
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Add new user's information provided
     */
    private void addSignUpFillIn(Context context, String username, String password) {
        String wrongUsername = this.inputLimits(username, "Username");
        String wrongPassword = this.inputLimits(password, "Password");

        if (!wrongUsername.equals("Success!")) {
            displayToast.displayToast(context, "Please choose a different one");
        } else if (wrongUsername.equals("Success!")) {
            this.newUser.setUsername(username);
            addSignUpFillInPassword(wrongPassword, password, context);
        }
    }

    /**
     * Check if password is correct.
     *
     * @param wrongPassword a flag for if the password is correct
     * @param password      the user's password
     */
    private void addSignUpFillInPassword(String wrongPassword, String password, Context context) {
        if (!wrongPassword.equals("Success!")) {
            displayToast.displayToast(context, "Please choose a different one");
        } else {
            this.newUser.setPassword(password);
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

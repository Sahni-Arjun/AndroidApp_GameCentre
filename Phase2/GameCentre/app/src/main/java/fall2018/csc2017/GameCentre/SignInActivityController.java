/*
Controller
 */
package fall2018.csc2017.GameCentre;

import android.content.Context;

/**
 * Controller for the sign in procedure for the app.
 */
class SignInActivityController {
    /**
     * The file read and write object.
     */
    private FileSystem fileSystem;

    /**
     * The account for the user that is signing in.
     */
    private Account signInUser;

    /**
     * Creates a new controller that manages the logic of the activity.
     * @param fileSystem The file read and write object.
     */
    SignInActivityController(FileSystem fileSystem){
        this.fileSystem = fileSystem;
    }

    boolean enterButtonListener(Context currentContext, String username, String password){
        addLoginFillIn(username, password);
        fileSystem.testExistingFile(currentContext);
        AccountManager accountManager = fileSystem.loadAccount(currentContext);
        if (accountManager.logInSuccessful(signInUser)) {
            StartingLoginActivity.currentUser = signInUser.getUsername();
            return true;
        }
        return false;
    }

    /**
     * Add user information provided
     */
    private void addLoginFillIn(String username, String password) {
        username = username.trim();
        password = password.trim();
        this.signInUser = new Account(username, password);
    }
}

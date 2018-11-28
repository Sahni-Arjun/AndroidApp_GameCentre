package fall2018.csc2017.GameCentre;

import android.content.Context;

class SignInActivityController {
    private FileSystem fileSystem;

    private DisplayToast displayToast;

    /**
     * The account for the user that is signing in.
     */
    private Account signInUser;

    SignInActivityController(FileSystem fileSystem, DisplayToast displayToast){
        this.fileSystem = fileSystem;
        this.displayToast = displayToast;
    }

    boolean enterButtonListener(Context currentContext, String username, String password){
        addLoginFillIn(username, password);
        fileSystem.testExistingFile(currentContext);
        AccountManager accountManager = fileSystem.loadAccount(currentContext);
        if (accountManager.logInSuccessful(signInUser)) {
            displayToast.displayToast(currentContext, "Loading...");
            StartingLoginActivity.currentUser = signInUser.getUsername();
            return true;
        } else {
            displayToast.displayToast(currentContext, "Wrong username or password");
            return false;
        }
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

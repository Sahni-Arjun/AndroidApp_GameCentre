package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The activity which facilitates sign-ups.
 */
public class SignUpActivity extends AppCompatActivity {

    /**
     * The file system
     */
    private FileSystem fileSystem = new FileSystem();

    private DisplayToast displayToast = new DisplayToast();

    private Context currentContext = this;

    private SignUpActivityController controller;

    /**
     * A blank account for a new user.
     */
    private Account newUser = new Account("", "");

    /**
     * Account manager for the sign-up activity.
     */
    private AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        controller = new SignUpActivityController(fileSystem, displayToast);
        addEnterButtonListener();
    }

    /**
     * Returns a toast according to whether or not username exists after Enter is clicked.
     * Directs to GameMenu if it is correct.
     */
    private void addEnterButtonListener() {
        Button enterButton = findViewById(R.id.button2);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText temp_user = findViewById(R.id.editText3);
                EditText temp_password = findViewById(R.id.editText4);

                Boolean signUpSuccess = controller.enterButtonListener(currentContext, temp_user.getText().toString(), temp_password.getText().toString());

                if (signUpSuccess){
                    switchToGameMenu();
                }

//                addSignUpFillIn();
//                if (newUser != null) {
//                    if (!(newUser.getUsername().isEmpty() || newUser.getPassword().isEmpty())) {
//                        fileSystem.testExistingFile(currentContext);
//                        accountManager = fileSystem.loadAccount(currentContext);
//                        if (!accountManager.isExistingUser(newUser.getUsername())) {
//                            Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_SHORT).show();
//                            accountManager.addUser(newUser);
//                            fileSystem.saveAccount(currentContext, accountManager);
//                            switchToGameMenu();
//                            StartingLoginActivity.currentUser = newUser.getUsername();
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Username exists. " +
//                                    "Please choose a different one", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
            }
        });
    }

    /**
     * Switch to GameMenu view to go to the menu page
     */
    private void switchToGameMenu() {
        Intent tmp = new Intent(this, NotRobotActivity.class);
        startActivity(tmp);
    }

//    /**
//     * Add new user's information provided
//     */
//    private void addSignUpFillIn() {
//
//        EditText temp_user = findViewById(R.id.editText3);
//        EditText temp_password = findViewById(R.id.editText4);
//
//        String username = temp_user.getText().toString();
//        String password = temp_password.getText().toString();
//
//        String wrongUsername = this.inputLimits(username, "Username");
//        String wrongPassword = this.inputLimits(password, "Password");
//
//        if (!wrongUsername.equals("Success!")) {
//            Toast.makeText(getApplicationContext(), wrongUsername +
//                    "Please choose a different one", Toast.LENGTH_SHORT).show();
//        } else if (wrongUsername.equals("Success!")) {
//            this.newUser.setUsername(username);
//            addSignUpFillInPassword(wrongPassword, password);
//        }
//    }
//
//    /**
//     * Check if password is correct.
//     *
//     * @param wrongPassword a flag for if the password is correct
//     * @param password      the user's password
//     */
//    private void addSignUpFillInPassword(String wrongPassword, String password) {
//        if (!wrongPassword.equals("Success!")) {
//            Toast.makeText(getApplicationContext(), wrongPassword +
//                    "Please choose a different one", Toast.LENGTH_SHORT).show();
//        } else {
//            this.newUser.setPassword(password);
//        }
//    }
//
//    /**
//     * Put limits on the username and password.
//     *
//     * @param userInput   the input the user enters in when signing up
//     * @param typeOfInput the type of input
//     * @return String invalid input message
//     */
//    private String inputLimits(String userInput, String typeOfInput) {
//        String trimUsername = userInput.trim();
//        if (trimUsername.length() != userInput.length()) {
//            return "No trailing spaces in " + typeOfInput + ".";
//        }
//        if (trimUsername.length() < 4) {
//            return typeOfInput + " should be at least 4 characters long.";
//        }
//        for (char ch : trimUsername.toCharArray()) {
//            if (Character.isWhitespace(ch)) {
//                return "No whitespaces in " + typeOfInput + ".";
//            }
//        }
//        return "Success!";
//    }
}

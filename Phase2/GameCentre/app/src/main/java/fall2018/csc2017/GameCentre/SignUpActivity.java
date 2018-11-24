package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * The activity which facilitates sign-ups.
 */
public class SignUpActivity extends AppCompatActivity {

    /**
     * A blank account for a new user.
     */
    private Account newUser = new Account("", "");

    /**
     * Account manager for the sign-up activity.
     */
    private AccountManager accountManager;

    /**
     * The scoreboard for all games.
     */
    private Scoreboard scoreboard;

    /**
     * Existence of a serializable file for accountManager and Scoreboard.
     */
    private boolean existingFile = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

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
                addSignUpFillIn();
                if (!newUser.getUsername().isEmpty() && !newUser.getPassword().isEmpty()) {
                    loadFromFile();
                    if (!existingFile) {
                        List<Account> emptyAccounts = new ArrayList<>();
                        accountManager = new AccountManager(emptyAccounts);
                        scoreboard = new Scoreboard();
                        saveToFile();
                        existingFile = true;
                    }
                    loadFromFile();
                    if (!accountManager.isExistingUser(newUser.getUsername())) {
                        Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_SHORT).show();
                        accountManager.addUser(newUser);
                        saveToFile();
                        switchToGameMenu();
                        StartingLoginActivity.currentUser = newUser.getUsername();
                    } else {
                        Toast.makeText(getApplicationContext(), "Username exists. " +
                                "Please choose a different one", Toast.LENGTH_SHORT).show();
                    }
                }
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

    /**
     * Add new user's information provided
     */
    private void addSignUpFillIn() {
        EditText temp_user = findViewById(R.id.editText3);
        EditText temp_password = findViewById(R.id.editText4);

        String username = temp_user.getText().toString();
        String password = temp_password.getText().toString();

        String wrongUsername = this.inputLimits(username, "Username");
        String wrongPassword = this.inputLimits(password, "Password");

        if (!wrongUsername.equals("Success!")) {
            Toast.makeText(getApplicationContext(), wrongUsername +
                    "Please choose a different one", Toast.LENGTH_SHORT).show();
        } else if (wrongUsername.equals("Success!")) {
            this.newUser.setUsername(username);
            addSignUpFillInPassword(wrongPassword, password);
        }
    }

    /**
     * Check if password is correct.
     *
     * @param wrongPassword a flag for if the password is correct
     * @param password      the user's password
     */
    private void addSignUpFillInPassword(String wrongPassword, String password) {
        if (!wrongPassword.equals("Success!")) {
            Toast.makeText(getApplicationContext(), wrongPassword +
                    "Please choose a different one", Toast.LENGTH_SHORT).show();
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

    /**
     * Load the account manager from fileName.
     */
    private void loadFromFile() {

        try {
            InputStream inputStreamAccount = this.openFileInput(StartingLoginActivity.SAVE_ACCOUNT_MANAGER);
            if (inputStreamAccount != null) {
                ObjectInputStream input = new ObjectInputStream(inputStreamAccount);
                accountManager = (AccountManager) input.readObject();
                inputStreamAccount.close();
            }
            InputStream inputStreamScoreBoard = this.openFileInput(StartingLoginActivity.SAVE_SCOREBOARD);
            if (inputStreamScoreBoard != null) {
                ObjectInputStream input = new ObjectInputStream(inputStreamScoreBoard);
                scoreboard = (Scoreboard) input.readObject();
                inputStreamScoreBoard.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            existingFile = false;
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the board manager to fileName.
     */
    private void saveToFile() {
        try {
            ObjectOutputStream outputStreamAccount = new ObjectOutputStream(
                    this.openFileOutput(StartingLoginActivity.SAVE_ACCOUNT_MANAGER, MODE_PRIVATE));
            outputStreamAccount.writeObject(accountManager);
            outputStreamAccount.close();

            ObjectOutputStream outputStreamScoreBoard = new ObjectOutputStream(
                    this.openFileOutput(StartingLoginActivity.SAVE_SCOREBOARD, MODE_PRIVATE));
            outputStreamScoreBoard.writeObject(scoreboard);
            outputStreamScoreBoard.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}

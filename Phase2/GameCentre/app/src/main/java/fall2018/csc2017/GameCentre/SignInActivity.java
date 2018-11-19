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
 * Activity for the sign in procedure for the app.
 */
public class SignInActivity extends AppCompatActivity {

    /**
     * The account for the user that is signing in.
     */
    protected Account signInUser;

    /**
     * The account manager for all accounts.
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
        setContentView(R.layout.activity_sign_in);

        addEnterButtonListener();
    }

    /**
     * Returns a toast according to the username and password correctness after Enter is clicked.
     * Directs to GameMenu if it is correct.
     */
    private void addEnterButtonListener() {
        Button enterButton = findViewById(R.id.button);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLoginFillIn();
                loadFromFile();
                if (!existingFile) {
                    List<Account> emptyAccounts = new ArrayList<>();
                    accountManager = new AccountManager(emptyAccounts);
                    scoreboard = new Scoreboard();
                    saveToFile();
                    existingFile = true;
                }
                if (accountManager.logInSuccessful(signInUser)) {
                    Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_SHORT).show();
                    switchToGameMenu();
                    StartingLoginActivity.currentUser = signInUser.getUsername();
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong username or password",
                            Toast.LENGTH_SHORT).show();
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
     * Add user information provided
     */
    private void addLoginFillIn() {
        EditText temp_user = findViewById(R.id.editText);
        EditText temp_password = findViewById(R.id.editText2);

        String username = temp_user.getText().toString().trim();
        String password = temp_password.getText().toString().trim();
        this.signInUser = new Account(username, password);
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

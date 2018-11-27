package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

public class HangmanStartingActivity extends AppCompatActivity {

    /**
     * The activity controller.
     */
    private HangmanStartingActivityController hangmanStartingActivityController;

    /**
     * The current context used for file reading/writing.
     */
    private Context currentContext = this;

    /**
     * The word manager.
     */
    public static WordManager wordManager;

    /**
     * The account manager.
     */
    private AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FileSystem fileSystem = new FileSystem();
        DisplayToast displayToast = new DisplayToast();
        hangmanStartingActivityController = new HangmanStartingActivityController(fileSystem);
        setContentView(R.layout.activity_hangman_starting);
        addNewGameButtonListener();
        addLoadButtonListener();
        addContinueButtonListener();
    }

    @Override
    public void onBackPressed() {
        Intent tmp = new Intent(this, ChooseGameMenuActivity.class);
        startActivity(tmp);
    }

    // todo make into mvc
    /**
     * Activate the start button.
     */
    private void addNewGameButtonListener() {
        Button startButton = findViewById(R.id.btnHangManNewGame);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hangmanStartingActivityController.newGameButtonListener(currentContext);
                switchToHangman();
            }
        });
    }

    /**
     * Switch to the HangmanActivity to specify complexity.
     */
    private void switchToHangmanComplexity() {
        Intent tmp = new Intent(this, HangmanComplexityActivity.class);
        startActivity(tmp);
    }

    /**
     * Switch to the HangmanActivity
     */
    private void switchToHangman() {
        Intent tmp = new Intent(this, HangmanActivity.class);
        startActivity(tmp);
    }

    // todo make into mvc
    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.btnHangManLoad);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hangmanStartingActivityController.loadButtonListener(currentContext);
                // todo needs to satisfy a certain requirement refer to the controller, maybe make it so that loadbuttonlitsener returns a boolean?
                switchToGame();
            }
        });
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    // todo make mvc
    /**
     * Activate the continue button.
     */
    private void addContinueButtonListener() {
        Button loadButton = findViewById(R.id.btnHangManContinue);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromFile();
                Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
                SaveManager currSavManager = currentAccount.getSaveManager();

                if (currSavManager.getLength("auto", SaveManager.hangmanName) != 0) {
                    wordManager = ((HangmanState) currSavManager.getLastState("auto", SaveManager.hangmanName)).getWordManager();
                    HangmanState lastAutoState = (HangmanState) currSavManager.getLastState("auto", SaveManager.hangmanName);
                    HangmanComplexityActivity.complexity = lastAutoState.getComplexity();
                    // Word.length = HangmanComplexityActivity.complexity;
                    Word.numRows = 1;
                    Word.numCols = HangmanComplexityActivity.complexity + 1; // todo: discuss if we shall relate complexity to length or to word content
                    makeToastLoadedText();
                    switchToGame();
                } else {
                    Toast.makeText(getApplicationContext(), "you can't continue a game " +
                            "that hasn't started!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Read the account manager from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadFromFile();
    }

    /**
     * Switch to the HangmanActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, HangmanActivity.class);
        startActivity(tmp);
    }

    // todo make into mvc
    /**
     * Load the account manager from fileName.
     */
    private void loadFromFile() {

        try {
            InputStream inputStream =
                    this.openFileInput(StartingLoginActivity.SAVE_ACCOUNT_MANAGER);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                accountManager = (AccountManager) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected" +
                    " data type: " + e.toString());
        }
    }

    /**
     * Save the account manager to fileName.
     */
    private void saveToFile() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(StartingLoginActivity.SAVE_ACCOUNT_MANAGER, MODE_PRIVATE));
            outputStream.writeObject(accountManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}

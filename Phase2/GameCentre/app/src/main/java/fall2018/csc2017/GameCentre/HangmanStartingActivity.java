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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FileSystem fileSystem = new FileSystem();
        DisplayToast displayToast = new DisplayToast();
        hangmanStartingActivityController = new HangmanStartingActivityController(fileSystem, displayToast);
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
     * Switch to the HangmanActivity
     */
    private void switchToHangman() {
        Intent tmp = new Intent(this, HangmanActivity.class);
        startActivity(tmp);
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.btnHangManLoad);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hangmanStartingActivityController.loadButtonListener(currentContext)) {
                    switchToGame();
                }
            }
        });
    }

    /**
     * Activate the continue button.
     */
    private void addContinueButtonListener() {
        Button loadButton = findViewById(R.id.btnHangManContinue);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hangmanStartingActivityController.continueButtonListener(currentContext)) {
                    switchToGame();
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
    }

    /**
     * Switch to the HangmanActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, HangmanActivity.class);
        startActivity(tmp);
    }
}

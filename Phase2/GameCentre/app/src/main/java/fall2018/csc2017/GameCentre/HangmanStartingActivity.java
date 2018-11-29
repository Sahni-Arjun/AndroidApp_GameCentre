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

        addHangmanSave1ButtonListener();
        addHangmanSave2ButtonListener();
        addHangmanSave3ButtonListener();

        addHangmanContinue1ButtonListener();
        addHangmanContinue2ButtonListener();
        addHangmanContinue3ButtonListener();

        addHangmanNewGame1ButtonListener();
        addHangmanNewGame2ButtonListener();
        addHangmanNewGame3ButtonListener();
    }

    @Override
    public void onBackPressed() {
        Intent tmp = new Intent(this, ChooseGameMenuActivity.class);
        startActivity(tmp);
    }


    /**
     * Switch to the HangmanActivity
     */
    public void switchToHangman() {
        Intent tmp = new Intent(this, HangmanActivity.class);
        startActivity(tmp);
    }

    /**
     * Read the account manager from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }


    /**
     * Activate the load save 1 button.
     */
    private void addHangmanSave1ButtonListener() {
        Button startButton = findViewById(R.id.hangmanSave1);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hangmanStartingActivityController.loadSave(1, currentContext);
            }
        });
    }

    /**
     * Activate the load save 2 button.
     */
    private void addHangmanSave2ButtonListener() {
        Button startButton = findViewById(R.id.hangmanSave2);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hangmanStartingActivityController.loadSave(2, currentContext);
            }
        });
    }

    /**
     * Activate the load save 3 button.
     */
    private void addHangmanSave3ButtonListener() {
        Button startButton = findViewById(R.id.hangmanSave3);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hangmanStartingActivityController.loadSave(3, currentContext);
            }
        });
    }

    /**
     * Activate the continue 1 button.
     */
    private void addHangmanContinue1ButtonListener() {
        Button startButton = findViewById(R.id.hangmanContinue1);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hangmanStartingActivityController.continueSave(1, currentContext);
            }
        });
    }

    /**
     * Activate the continue 2 button.
     */
    private void addHangmanContinue2ButtonListener() {
        Button startButton = findViewById(R.id.hangmanContinue2);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hangmanStartingActivityController.continueSave(2, currentContext);
            }
        });
    }

    /**
     * Activate the continue 3 button.
     */
    private void addHangmanContinue3ButtonListener() {
        Button startButton = findViewById(R.id.hangmanContinue3);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hangmanStartingActivityController.continueSave(3, currentContext);
            }
        });
    }

    /**
     * Activate the new game 1 button.
     */
    private void addHangmanNewGame1ButtonListener() {
        Button startButton = findViewById(R.id.hangmanNewGame1);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hangmanStartingActivityController.newGame(1, currentContext);
            }
        });
    }

    /**
     * Activate the new game 2 button.
     */
    private void addHangmanNewGame2ButtonListener() {
        Button startButton = findViewById(R.id.hangmanNewGame2);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hangmanStartingActivityController.newGame(2, currentContext);
            }
        });
    }

    /**
     * Activate the new game 3 button.
     */
    private void addHangmanNewGame3ButtonListener() {
        Button startButton = findViewById(R.id.hangmanNewGame3);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hangmanStartingActivityController.newGame(3, currentContext);
            }
        });
    }

}

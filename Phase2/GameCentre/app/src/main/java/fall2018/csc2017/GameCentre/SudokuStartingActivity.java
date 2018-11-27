package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * The activity for loading games for the sliding puzzle tile game.
 */
public class SudokuStartingActivity  extends AppCompatActivity {

    /**
     * The board manager.
     */
    public static SudokuBoardManager boardManager;

    /**
     * The account manager.
     */
    private AccountManager accountManager;

    /**
     * The filesystem.
     */
    private FileSystem fileSystem;

    /**
     * The current context for file reading/writing.
     */
    private Context currentContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fileSystem = new FileSystem();
//        slidingTilesBoardManager = new SudokuBoardManager();
        setContentView(R.layout.activity_sudoku_starting);
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
     * goes to SudokuDifficultActivity
     */
    public void switchToDifficulty(){
        Intent tmp = new Intent(this, SudokuDifficultyActivity.class);
        startActivity(tmp);
    }

    /**
     * Activate the start button.
     */
    private void addNewGameButtonListener() {
        Button startButton = findViewById(R.id.btnSudokuNewGame);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToDifficulty();
            }
        });
    }

    /**
     * Switch to the SudokuActivity to specify complexity.
     */
    private void switchtoSudoku() {
        Intent tmp = new Intent(this, SudokuActivity.class);
        startActivity(tmp);
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.btnSudokuLoad);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountManager = fileSystem.loadAccount(currentContext);
                Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
                SaveManager currSavManager = currentAccount.getSaveManager();

                if (currSavManager.getLength("perma", SaveManager.sudokuName) != 0) {
                    boardManager = ((SudokuState) currSavManager.getLastState("perma", SaveManager.sudokuName)).getBoardManager();
                    currSavManager.updateSave("auto", SaveManager.sudokuName);
//                    SudokuState prePermaState = (SudokuState) currSavManager.getLastState("perma", SaveManager.sudokuName);
                    //TODO add difficulty when implemented.
//                    SlidingTileComplexityActivity.complexity = prePermaState.getComplexity();
                    Board.numRows = 9;
                    Board.numCols = 9;
                    makeToastLoadedText();
                    fileSystem.saveAccount(currentContext, accountManager);
                    switchtoSudoku();
                } else {
                    Toast.makeText(getApplicationContext(), "Use common sense you can't" +
                            " load if you haven't saved yet!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the continue button.
     */
    private void addContinueButtonListener() {
        Button loadButton = findViewById(R.id.btnSudokuContinue);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountManager = fileSystem.loadAccount(currentContext);
                Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
                SaveManager currSavManager = currentAccount.getSaveManager();

                if (currSavManager.getLength("auto", SaveManager.sudokuName) != 0) {
                    boardManager = ((SudokuState) currSavManager.getLastState("auto", SaveManager.sudokuName)).getBoardManager();
//                    SudokuState lastAutoState = (SudokuState) currSavManager.getLastState("auto", SaveManager.sudokuName);
//                    SlidingTileComplexityActivity.complexity = lastAutoState.getComplexity();
//                    Board.numRows = SlidingTileComplexityActivity.complexity;
//                    Board.numCols = SlidingTileComplexityActivity.complexity;
                    Board.numRows = 9;
                    Board.numCols = 9;
                    makeToastLoadedText();
                    switchtoSudoku();
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
        accountManager = fileSystem.loadAccount(currentContext);
    }

}

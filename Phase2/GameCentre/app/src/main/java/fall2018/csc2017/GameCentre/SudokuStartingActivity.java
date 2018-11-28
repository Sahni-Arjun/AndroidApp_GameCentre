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
//    public static SudokuBoardManager boardManager;

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
        setContentView(R.layout.activity_sudoku_starting);
//        addNewGameButtonListener();
//        addLoadButtonListener();
//        addContinueButtonListener();

        addSudokuSave1ButtonListener();
        addSudokuSave2ButtonListener();
        addSudokuSave3ButtonListener();

        addSudokuContinue1ButtonListener();
        addSudokuContinue2ButtonListener();
        addSudokuContinue3ButtonListener();

        addSudokuNewGame1ButtonListener();
        addSudokuNewGame2ButtonListener();
        addSudokuNewGame3ButtonListener();
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

//    /**
//     * Goes to the screen to chose the difficulty of the game.
//     */
//    private void addNewGameButtonListener() {
//        Button startButton = findViewById(R.id.btnSudokuNewGame);
//        startButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switchToDifficulty();
//            }
//        });
//    }

    /**
     * Switch to the SudokuActivity to specify complexity.
     */
    private void switchtoSudoku() {
        Intent tmp = new Intent(this, SudokuActivity.class);
        startActivity(tmp);
    }

//    /**
//     * Activate the load button.
//     */
//    private void addLoadButtonListener() {
//        Button loadButton = findViewById(R.id.btnSudokuLoad);
//        loadButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                accountManager = fileSystem.loadAccount(currentContext);
//                Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
//                SaveManager currSavManager = currentAccount.getSaveManager();
//
//                if (currSavManager.getLength("perma", SaveManager.sudokuName) != 0) {
//                    SudokuDifficultyActivity.boardManager = ((SudokuState) currSavManager.getLastState("perma", SaveManager.sudokuName)).getBoardManager();
//                    currSavManager.updateSave("auto", SaveManager.sudokuName);
////                    SudokuState prePermaState = (SudokuState) currSavManager.getLastState("perma", SaveManager.sudokuName);
//                    //TODO add difficulty when implemented.
////                    SlidingTileComplexityActivity.complexity = prePermaState.getComplexity();
//                    SudokuState prePermaState = (SudokuState) currSavManager.getLastState(SaveManager.perma, SaveManager.sudokuName);
//                    SudokuDifficultyActivity.difficulty = prePermaState.getDifficulty();
//                    Board.numRows = 9;
//                    Board.numCols = 9;
//                    makeToastLoadedText();
//                    fileSystem.saveAccount(currentContext, accountManager);
//                    switchtoSudoku();
//                } else {
//                    Toast.makeText(getApplicationContext(), "Use common sense you can't" +
//                            " load if you haven't saved yet!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

//    /**
//     * Activate the continue button.
//     */
//    private void addContinueButtonListener() {
//        Button loadButton = findViewById(R.id.btnSudokuContinue);
//        loadButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                accountManager = fileSystem.loadAccount(currentContext);
//                Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
//                SaveManager currSavManager = currentAccount.getSaveManager();
//
//                if (currSavManager.getLength("auto", SaveManager.sudokuName) != 0) {
//                    SudokuDifficultyActivity.boardManager = ((SudokuState) currSavManager.getLastState("auto", SaveManager.sudokuName)).getBoardManager();
////                    SudokuState lastAutoState = (SudokuState) currSavManager.getLastState("auto", SaveManager.sudokuName);
////                    SlidingTileComplexityActivity.complexity = lastAutoState.getComplexity();
////                    Board.numRows = SlidingTileComplexityActivity.complexity;
////                    Board.numCols = SlidingTileComplexityActivity.complexity;
//                    SudokuState lastAutoState = (SudokuState) currSavManager.getLastState(SaveManager.auto, SaveManager.sudokuName);
//                    SudokuDifficultyActivity.difficulty = lastAutoState.getDifficulty();
//                    Board.numRows = 9;
//                    Board.numCols = 9;
//                    makeToastLoadedText();
//                    switchtoSudoku();
//                } else {
//                    Toast.makeText(getApplicationContext(), "you can't continue a game " +
//                            "that hasn't started!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

    /**
     * Read the account manager from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        accountManager = fileSystem.loadAccount(currentContext);
    }

    /**
     * Activate the load save 1 button.
     */
    private void addSudokuSave1ButtonListener() {
        Button startButton = findViewById(R.id.sudokuSave1);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSave(1, currentContext);
            }
        });
    }

    /**
     * Activate the load save 2 button.
     */
    private void addSudokuSave2ButtonListener() {
        Button startButton = findViewById(R.id.sudokuSave2);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSave(2, currentContext);
            }
        });
    }

    /**
     * Activate the load save 3 button.
     */
    private void addSudokuSave3ButtonListener() {
        Button startButton = findViewById(R.id.sudokuSave3);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSave(3, currentContext);
            }
        });
    }

    /**
     * Activate the continue 1 button.
     */
    private void addSudokuContinue1ButtonListener() {
        Button startButton = findViewById(R.id.sudokuContinue1);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueSave(1, currentContext);
            }
        });
    }

    /**
     * Activate the continue 2 button.
     */
    private void addSudokuContinue2ButtonListener() {
        Button startButton = findViewById(R.id.sudokuContinue2);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueSave(2, currentContext);
            }
        });
    }

    /**
     * Activate the continue 3 button.
     */
    private void addSudokuContinue3ButtonListener() {
        Button startButton = findViewById(R.id.sudokuContinue3);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueSave(3, currentContext);
            }
        });
    }

    /**
     * Activate the new game 1 button.
     */
    private void addSudokuNewGame1ButtonListener() {
        Button startButton = findViewById(R.id.sudokuNewGame1);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame(1, currentContext);
            }
        });
    }

    /**
     * Activate the new game 2 button.
     */
    private void addSudokuNewGame2ButtonListener() {
        Button startButton = findViewById(R.id.sudokuNewGame2);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame(2, currentContext);
            }
        });
    }

    /**
     * Activate the new game 3 button.
     */
    private void addSudokuNewGame3ButtonListener() {
        Button startButton = findViewById(R.id.sudokuNewGame3);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame(3, currentContext);
            }
        });
    }

    /**
     * The method that loads saved files
     * @param gameFile the number of the game file the user would like to open
     * @param currentContext the current SlidingTileStartingActivity
     */
    void loadSave(int gameFile, Context currentContext){
        accountManager = fileSystem.loadAccount(currentContext);
        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        currentAccount.setCurrentGame(gameFile);
        SaveManager currSavManager = currentAccount.getCurrentSaveManager(Account.sudokuName);

        if (currSavManager.getLength(SaveManager.perma, SaveManager.sudokuName) != 0) {
            SudokuDifficultyActivity.boardManager = ((SudokuState) currSavManager.getLastState("perma", SaveManager.sudokuName)).getBoardManager();
            currSavManager.updateSave(SaveManager.auto, SaveManager.sudokuName);
            SudokuState prePermaState = (SudokuState) currSavManager.getLastState(SaveManager.perma, SaveManager.sudokuName);
            SudokuDifficultyActivity.difficulty = prePermaState.getDifficulty();
            Board.numRows = 9;
            Board.numCols = 9;
            Toast.makeText(getApplicationContext(), "Loaded Game " + String.valueOf(gameFile), Toast.LENGTH_SHORT).show();
            fileSystem.saveAccount(currentContext, accountManager);
            switchtoSudoku();
        } else {
            Toast.makeText(getApplicationContext(), "Use common sense you can't" +
                    " load if you haven't saved yet!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Load the continue
     * @param gameFile
     * @param currentContext
     */
    void continueSave(int gameFile, Context currentContext){
        accountManager = fileSystem.loadAccount(currentContext);
        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        currentAccount.setCurrentGame(gameFile);
        SaveManager currSavManager = currentAccount.getCurrentSaveManager(Account.sudokuName);

        if (currSavManager.getLength("auto", SaveManager.sudokuName) != 0) {
            SudokuDifficultyActivity.boardManager = ((SudokuState) currSavManager.getLastState("auto", SaveManager.sudokuName)).getBoardManager();
            SudokuState lastAutoState = (SudokuState) currSavManager.getLastState(SaveManager.auto, SaveManager.sudokuName);
            SudokuDifficultyActivity.difficulty = lastAutoState.getDifficulty();
            Board.numRows = 9;
            Board.numCols = 9;
            fileSystem.saveAccount(currentContext, accountManager);
            Toast.makeText(getApplicationContext(), "Loaded Game " + String.valueOf(gameFile), Toast.LENGTH_SHORT).show();
            switchtoSudoku();
        } else {
            Toast.makeText(getApplicationContext(), "you can't continue a game " +
                    "that hasn't started!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Load new game
     * @param gameFile
     * @param currentContext
     */
    void newGame(int gameFile, Context currentContext){
        accountManager = fileSystem.loadAccount(currentContext);
        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        currentAccount.setCurrentGame(gameFile);
        fileSystem.saveAccount(currentContext, accountManager);
        switchToDifficulty();
    }
}

package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * The activity for loading games for the sliding puzzle tile game.
 */
public class SudokuStartingActivity  extends AppCompatActivity {

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

    /**
     * Switch to the SudokuActivity to specify complexity.
     */
    private void switchtoSudoku() {
        Intent tmp = new Intent(this, SudokuActivity.class);
        startActivity(tmp);
    }

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
            currSavManager.setContinueOrLoad(SaveManager.perma);
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
     * @param gameFile The specified save file.
     * @param currentContext The current activity
     */
    void continueSave(int gameFile, Context currentContext){
        accountManager = fileSystem.loadAccount(currentContext);
        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        currentAccount.setCurrentGame(gameFile);
        SaveManager currSavManager = currentAccount.getCurrentSaveManager(Account.sudokuName);

        if (currSavManager.getLength("auto", SaveManager.sudokuName) != 0) {
            currSavManager.setContinueOrLoad(SaveManager.auto);
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
     * @param gameFile The specified save file
     * @param currentContext The current activity
     */
    void newGame(int gameFile, Context currentContext){
        accountManager = fileSystem.loadAccount(currentContext);
        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        currentAccount.setCurrentGame(gameFile);
        fileSystem.saveAccount(currentContext, accountManager);
        switchToDifficulty();
    }
}

package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SudokuDifficultyActivity extends AppCompatActivity {
    /**
     * The difficulty of the game.
     */
    public static Integer difficulty;
    /**
     * The current context for file reading/writing.
     */
    private SudokuDifficultyActivity currentContext = this;

    /**
     * the controoler for this actvity
     */
    private SudokuDifficultyActivityController sCon;

    public static SudokuBoardManager boardManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sCon = new SudokuDifficultyActivityController();
        setContentView(R.layout.activity_sudoku_difficulty);
        addButtonNoobListener();
        addButtonAmateurListener();
        addButtonProListener();
    }

    @Override
    public void onBackPressed() {
        Intent tmp = new Intent(this, SudokuStartingActivity.class);
        startActivity(tmp);
    }


    /**
     * this goes to SudokuActivity (the actual game).
     */
    void switchToSudoku() {
        Intent tmp = new Intent(this, SudokuActivity.class);
        startActivity(tmp);
    }

    /**
     * intitiates everything needed for the game to start.
     */
//    private void startGame() {
//
//        boardManager = new SudokuBoardManager();
//        accountManager = fileSystem.loadAccount(currentContext);
//        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
//        SaveManager currSavManager = currentAccount.getSaveManager();
//        currSavManager.wipeSave(SaveManager.auto, SaveManager.sudokuName);
//
//        //Start new game with chosen number of undos
//        SudokuState newState = new
//                SudokuState(boardManager, 0);
//
//        //TODO set correct difficulty when implemented.
//        newState.setDifficulty(0);
//
//        //TODO set number of undos when implemented.
//        newState.setUnlimitedUndo();
//
////                if (SetUndoActivity.unlimited) {
////                    newState.setUnlimitedUndo();
////                } else {
////                    newState.setMaxNumMovesUndone(SetUndoActivity.undo);
////                }
//        currSavManager.addState(newState, SaveManager.sudokuName);
//        fileSystem.saveAccount(currentContext, accountManager);
//        switchToSudoku();
//    }
//

    /**
     * Activate button to set difficulty to 1(easy).
     */
    private void addButtonNoobListener() {
        Button button3x3 = findViewById(R.id.Noob);
        button3x3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficulty = 1;
                boardManager = new SudokuBoardManager();
                sCon.boardManager = SudokuDifficultyActivity.boardManager;
                sCon.startGame(currentContext);
            }
        });

    }

    /**
     * Activate button to set difficulty to 2(medium).
     */
    private void addButtonAmateurListener() {
        Button button4x4 = findViewById(R.id.Amateur);
        button4x4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficulty = 2;
                boardManager = new SudokuBoardManager();
                sCon.boardManager = SudokuDifficultyActivity.boardManager;
                sCon.startGame(currentContext);
            }
        });
    }

    /**
     * Activate button to set difficulty to 4(hard).
     */
    private void addButtonProListener() {
        Button button5x5 = findViewById(R.id.Pro);
        button5x5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficulty = 4;
                boardManager = new SudokuBoardManager();
                sCon.boardManager = SudokuDifficultyActivity.boardManager;
                sCon.startGame(currentContext);
            }
        });
    }
}


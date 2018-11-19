package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * The game activity.
 */
public class SudokuActivity extends AppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    private SudokuBoardManager boardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * The account manage for the app.
     */
    private AccountManager accountManager;

    /**
     * Scoreboard for the sidling tiles game.
     */
    private Scoreboard scoreBoard;

    // Grid View and calculated column height and width based on device size
    private SudokuGestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * The current number selected.
     */
    public static int currentNumber = 1;

    private long startTime;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile(StartingLoginActivity.SAVE_ACCOUNT_MANAGER, "Account");
//        boardManager = SlidingTileStartingActivity.boardManager;
        boardManager = SudokuStartingActivity.boardManager;
        createTileButtons(this);
        setContentView(R.layout.activity_sudoku);
        addSaveButtonListener();
        addUndoButtonListener();
        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(Board.numCols);
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / Board.numCols;
                        columnHeight = displayHeight / Board.numRows;

                        display();
                    }
                });
        addChooseOneButtonListener();
        addChooseTwoButtonListener();
        addChooseThreeButtonListener();
        addChooseFourButtonListener();
        addChooseFiveButtonListener();
        addChooseSixButtonListener();
        addChooseSevenButtonListener();
        addChooseEightButtonListener();
        addChooseNineButtonListener();
        startTime = System.currentTimeMillis();
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.btnSudokuSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromFile(StartingLoginActivity.SAVE_ACCOUNT_MANAGER, "Account");

                Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
                currentAccount.getSaveManager().updateSave("perma", SaveManager.sudokuName);

                saveToFile(StartingLoginActivity.SAVE_ACCOUNT_MANAGER, "Account");
            }
        });
    }

    /**
     * Activate the start button.
     */
    private void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.btnSudokuUndo);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromFile(StartingLoginActivity.SAVE_ACCOUNT_MANAGER, "Account");

                Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
                SaveManager currSavManager = currentAccount.getSaveManager();

                boolean canUndo = currSavManager.getLastState("auto", SaveManager.sudokuName).canUndo();
                SudokuState currentAutoState = (SudokuState) currSavManager.getLastState("auto", SaveManager.sudokuName);

                if ((currSavManager.getLength("auto", SaveManager.sudokuName) != 1) && canUndo) {
                    int prevMovesUndone = currentAutoState.getNumMovesUndone();
                    currSavManager.undo(SaveManager.sudokuName);
                    SudokuState prevState;
                    prevState = (SudokuState) currSavManager.getLastState("auto", SaveManager.sudokuName);

                    Tile[][] prevTiles = prevState.getBoardManager().getBoard().getTiles();
                    boardManager.getBoard().setTiles(prevTiles);

                    gridView.setBoardManager(boardManager);
                    currSavManager.getLastState("auto", SaveManager.sudokuName).incrementNumMoves(prevMovesUndone);
                    saveToFile(StartingLoginActivity.SAVE_ACCOUNT_MANAGER, "Account");
                    display();

                } else {
                    Toast.makeText(getApplicationContext(), "Max moves undone" +
                            "", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
//        SudokuBoard board = boardManager.getThisBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != Board.numRows; row++) {
            for (int col = 0; col != Board.numCols; col++) {
                Button tmp = new Button(context);
//                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                //TODO Uncomment the above line after pregenerated board is implemented.
                tmp.setBackgroundResource(R.drawable.tile_25);
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        SudokuBoard board = boardManager.getBoard();
        int nextPos = 0;

        for (Button b : tileButtons) {
            int row = nextPos / SudokuBoard.numRows;
            int col = nextPos % SudokuBoard.numCols;
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
        }
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        SaveManager currSavManager = currentAccount.getSaveManager();
        SudokuState lastAutoState = (SudokuState) currSavManager.getLastState("auto", SaveManager.sudokuName);
        long lastTime = lastAutoState.getTime();
        long newTime = lastTime + System.currentTimeMillis() - startTime;
        lastAutoState.setTime(newTime);
        saveToFile(StartingLoginActivity.SAVE_ACCOUNT_MANAGER, "Account");
    }

    /**
     * Dispatch onResume() to fragments.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadFromFile(StartingLoginActivity.SAVE_ACCOUNT_MANAGER, "Account");
        startTime = System.currentTimeMillis();
        display();
    }

    /**
     * Load scoreboard or account manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName, String type) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                if (type.equals("Account")) {
                    accountManager = (AccountManager) input.readObject();
                } else {
                    scoreBoard = (Scoreboard) input.readObject();
                }
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected " +
                    "data type: " + e.toString());
        }
    }

    /**
     * Save the account manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName, String type) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            if (type.equals("Account")) {
                outputStream.writeObject(accountManager);
            } else {
                outputStream.writeObject(scoreBoard);
            }
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    @Override
    public void onBackPressed() {
        Intent tmp = new Intent(this, SudokuStartingActivity.class);
        startActivity(tmp);
    }

    @Override
    public void update(Observable o, Object arg) {
        loadFromFile(StartingLoginActivity.SAVE_ACCOUNT_MANAGER, "Account");

        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        SaveManager currSavManager = currentAccount.getSaveManager();
        SudokuState lastAutoState = (SudokuState) currSavManager.getLastState("auto", SaveManager.sudokuName);
        int numMoves = currSavManager.getLength("auto", SaveManager.sudokuName);
        long lastTime = lastAutoState.getTime();
        long newTime = lastTime + System.currentTimeMillis() - startTime;

        //TODO set the difficulty of the game when it is implemented. (Currently it's 0).
        //Creating new game state with field values of the previous state.
        SudokuState newState = new SudokuState(boardManager, numMoves,
                0, SetUndoActivity.undo,
                lastAutoState.getNumMovesUndone(), lastAutoState.getUnlimitedUndo(),
                newTime);
        currSavManager.addState(newState, SaveManager.sudokuName);
        saveToFile(StartingLoginActivity.SAVE_ACCOUNT_MANAGER, "Account");
        Toast.makeText(getApplicationContext(), "" + newTime/1000, Toast.LENGTH_SHORT).show();
        display();
        startTime = System.currentTimeMillis();
//
//        //Saving/Displaying the score if the game is over.
//        if (newState.getBoardManager().puzzleSolved()) {
//            loadFromFile(StartingLoginActivity.SAVE_SCOREBOARD, "scoreboard");
//            scoreBoard.addToScoreBoard(scoreBoard.createScore(StartingLoginActivity.currentUser,
//                    newState.getScore()));
//            ScoreBoardActivity.slidingTileScoreBoard = scoreBoard;
//            saveToFile(StartingLoginActivity.SAVE_SCOREBOARD, "scoreboard");
//            currSavManager.wipeAutoSave();
//            currSavManager.wipePermaSave();
//            saveToFile(StartingLoginActivity.SAVE_ACCOUNT_MANAGER, "Account");
//            switchToWinning();
//        }

    }

    /**
     * Switch to the SlidingTileActivity view to play the game.
     */
    private void switchToWinning() {
        Intent tmp = new Intent(this, WinningActivity.class);
        startActivity(tmp);
    }

    /**
     * Activate the Choose one button.
     */
    private void addChooseOneButtonListener() {
        Button saveButton = findViewById(R.id.btnSudoku1);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentNumber = 1;
            }
        });
    }

    /**
     * Activate the Choose one button.
     */
    private void addChooseTwoButtonListener() {
        Button saveButton = findViewById(R.id.btnSudoku2);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentNumber = 2;
            }
        });
    }

    /**
     * Activate the Choose one button.
     */
    private void addChooseThreeButtonListener() {
        Button saveButton = findViewById(R.id.btnSudoku3);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentNumber = 3;
            }
        });
    }

    /**
     * Activate the Choose one button.
     */
    private void addChooseFourButtonListener() {
        Button saveButton = findViewById(R.id.btnSudoku4);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentNumber = 4;
            }
        });
    }

    /**
     * Activate the Choose one button.
     */
    private void addChooseFiveButtonListener() {
        Button saveButton = findViewById(R.id.btnSudoku5);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentNumber = 5;
            }
        });
    }

    /**
     * Activate the Choose one button.
     */
    private void addChooseSixButtonListener() {
        Button saveButton = findViewById(R.id.btnSudoku6);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentNumber = 6;
            }
        });
    }

    /**
     * Activate the Choose one button.
     */
    private void addChooseSevenButtonListener() {
        Button saveButton = findViewById(R.id.btnSudoku7);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentNumber = 7;
            }
        });
    }

    /**
     * Activate the Choose one button.
     */
    private void addChooseEightButtonListener() {
        Button saveButton = findViewById(R.id.btnSudoku8);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentNumber = 8;
            }
        });
    }

    /**
     * Activate the Choose one button.
     */
    private void addChooseNineButtonListener() {
        Button saveButton = findViewById(R.id.btnSudoku9);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentNumber = 9;
            }
        });
    }
}

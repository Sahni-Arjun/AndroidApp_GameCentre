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
public class SlidingTileActivity extends AppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    private SlidingTilesBoardManager slidingTilesBoardManager;

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
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

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
        slidingTilesBoardManager = SlidingTileStartingActivity.slidingTilesBoardManager;
        createTileButtons(this);
        setContentView(R.layout.activity_main);
        addSaveButtonListener();
        addUndoButtonListener();
        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(Board.numCols);
        gridView.setBoardManager(slidingTilesBoardManager);
        slidingTilesBoardManager.getBoard().addObserver(this);
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
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromFile(StartingLoginActivity.SAVE_ACCOUNT_MANAGER, "Account");

                Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
                currentAccount.getSaveManager().updateSave("perma", SaveManager.slidingTilesName);

                saveToFile(StartingLoginActivity.SAVE_ACCOUNT_MANAGER, "Account");
            }
        });
    }

    /**
     * Activate the start button.
     */
    private void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.UndoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromFile(StartingLoginActivity.SAVE_ACCOUNT_MANAGER, "Account");

                Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
                SaveManager currSavManager = currentAccount.getSaveManager();

                boolean canUndo = currSavManager.getLastState("auto", SaveManager.slidingTilesName).canUndo();
                SlidingTilesState currentAutoState = (SlidingTilesState) currSavManager.getLastState("auto", SaveManager.slidingTilesName);

                if ((currSavManager.getLength("auto", SaveManager.slidingTilesName) != 1) && canUndo) {
                    int prevMovesUndone = currentAutoState.getNumMovesUndone();
                    currSavManager.undo(SaveManager.slidingTilesName);
                    SlidingTilesState prevState;
                    prevState = (SlidingTilesState) currSavManager.getLastState("auto", SaveManager.slidingTilesName);

                    Tile[][] prevTiles = prevState.getSlidingTilesBoardManager().getBoard().getTiles();
                    slidingTilesBoardManager.getBoard().setTiles(prevTiles);

                    gridView.setBoardManager(slidingTilesBoardManager);
                    currSavManager.getLastState("auto", SaveManager.slidingTilesName).incrementNumMoves(prevMovesUndone);
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
        Board board = slidingTilesBoardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != Board.numRows; row++) {
            for (int col = 0; col != Board.numCols; col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board board = slidingTilesBoardManager.getBoard();
        int nextPos = 0;

        for (Button b : tileButtons) {
            int row = nextPos / Board.numRows;
            int col = nextPos % Board.numCols;
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
        saveToFile(StartingLoginActivity.SAVE_ACCOUNT_MANAGER, "Account");
    }

    /**
     * Dispatch onResume() to fragments.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadFromFile(StartingLoginActivity.SAVE_ACCOUNT_MANAGER, "Account");
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
        Intent tmp = new Intent(this, SlidingTileStartingActivity.class);
        startActivity(tmp);
    }

    @Override
    public void update(Observable o, Object arg) {
        loadFromFile(StartingLoginActivity.SAVE_ACCOUNT_MANAGER, "Account");

        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        SaveManager currSavManager = currentAccount.getSaveManager();
        currSavManager.updateState(SaveManager.slidingTilesName, slidingTilesBoardManager);
        saveToFile(StartingLoginActivity.SAVE_ACCOUNT_MANAGER, "Account");
        display();

        SlidingTilesState prevState = (SlidingTilesState) currSavManager.getLastState("auto", SaveManager.slidingTilesName);
        //Saving/Displaying the score if the game is over.
        if (slidingTilesBoardManager.puzzleSolved()) {
            loadFromFile(StartingLoginActivity.SAVE_SCOREBOARD, "scoreboard");
            scoreBoard.addToScoreBoard(scoreBoard.createScore(StartingLoginActivity.currentUser,
                    prevState.getScore()));
            saveToFile(StartingLoginActivity.SAVE_SCOREBOARD, "scoreboard");


            currSavManager.wipeAutoSave(SaveManager.slidingTilesName);
            currSavManager.wipePermaSave(SaveManager.slidingTilesName);
            saveToFile(StartingLoginActivity.SAVE_ACCOUNT_MANAGER, "Account");
            switchToWinning();
        }

    }

    /**
     * Switch to the SlidingTileActivity view to play the game.
     */
    private void switchToWinning() {
        Intent tmp = new Intent(this, WinningActivity.class);
        startActivity(tmp);
    }
}

package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

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
     * The filesystem.
     */
    private FileSystem fileSystem;

    /**
     * The current context for file reading/writing.
     */
    private Context currentContext = this;

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
        fileSystem = new FileSystem();
        accountManager = fileSystem.loadAccount(currentContext);
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
                accountManager = fileSystem.loadAccount(currentContext);

                Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
                currentAccount.getCurrentSaveManager(Account.slidingName).updateSave("perma", SaveManager.slidingTilesName);

                fileSystem.saveAccount(currentContext, accountManager);
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
                // load the account manager.
                accountManager = fileSystem.loadAccount(currentContext);

                Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
                SaveManager currSavManager = currentAccount.getCurrentSaveManager(Account.slidingName);

                boolean undoed = currSavManager.undoMove();
                if (undoed){
                    // update the current board manager with the new tiles.
                    slidingTilesBoardManager.getBoard().setTiles(currSavManager.getboardArrangement());
                    gridView.setBoardManager(slidingTilesBoardManager);
                    fileSystem.saveAccount(currentContext, accountManager);
                    display();
                }else{
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
        fileSystem.saveAccount(currentContext, accountManager);
    }

    /**
     * Dispatch onResume() to fragments.
     */
    @Override
    protected void onResume() {
        super.onResume();
        accountManager = fileSystem.loadAccount(currentContext);
        display();
    }

    @Override
    public void onBackPressed() {
        Intent tmp = new Intent(this, SlidingTileStartingActivity.class);
        startActivity(tmp);
    }

    @Override
    public void update(Observable o, Object arg) {
        // load the account manager.
        accountManager = fileSystem.loadAccount(currentContext);

        // save the account, savemanager in varaibles for future use.
        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        SaveManager currSavManager = currentAccount.getCurrentSaveManager(Account.slidingName);

        // add new save state in save manager.
        currSavManager.updateState(SaveManager.slidingTilesName, slidingTilesBoardManager);

        // save the account manager.
        fileSystem.saveAccount(currentContext, accountManager);

        // display the board.
        display();

        SlidingTilesState prevState = (SlidingTilesState) currSavManager.getLastState(SaveManager.auto, SaveManager.slidingTilesName);
        //Saving/Displaying the score if the game is over.
        if (slidingTilesBoardManager.puzzleSolved()) {
            scoreBoard = fileSystem.loadScoreboard(currentContext);
            scoreBoard.addToScoreBoard(scoreBoard.createScore(StartingLoginActivity.currentUser,
                    prevState.getScore()));
            fileSystem.saveScoreBoard(currentContext, scoreBoard);

            currSavManager.wipeSave(SaveManager.auto, SaveManager.slidingTilesName);
            currSavManager.wipeSave(SaveManager.perma, SaveManager.slidingTilesName);
            fileSystem.saveAccount(currentContext, accountManager);
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

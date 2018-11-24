package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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
public class HangmanActivity extends AppCompatActivity implements Observer, KeyEvent.Callback {

    /**
     * The board manager.
     */
    private WordManager wordManager;

    /**
     * The letters to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * The account manage for the app.
     */
    private AccountManager accountManager;

    /**
     * Scoreboard for the Hangman game.
     */
    private Scoreboard scoreBoard;

    // one-row only Grid View and calculated column height and width based on device size
    private HangmanGestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * Set up the background image for each letter based on the master list
     * of positions, and then call the adapter to set the view.
     */
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile("Account");
        wordManager = HangmanStartingActivity.wordManager;
        createTileButtons(this);
        setContentView(R.layout.activity_hangman_main);
        addSaveButtonListener();
        addUndoButtonListener();
        // Add View to activity
        gridView = findViewById(R.id.HangmanGrid);
        gridView.setNumColumns(Word.numCols);
        gridView.setWordManager(wordManager);
        wordManager.getWord().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / Word.numCols;
                        columnHeight = displayHeight;

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

                loadFromFile("Account");

                Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
                currentAccount.getSaveManager().updateSave("perma", SaveManager.hangmanName);

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
                loadFromFile("Account");

                Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
                SaveManager currSavManager = currentAccount.getSaveManager();

                boolean canUndo = currSavManager.getLastState("auto", SaveManager.hangmanName).canUndo();
                HangmanState currentAutoState = (HangmanState) currSavManager.getLastState("auto", SaveManager.hangmanName);

                if ((currSavManager.getLength("auto", SaveManager.hangmanName) != 1) && canUndo) {
                    int prevMovesUndone = currentAutoState.getNumMovesUndone();
                    currSavManager.undo(SaveManager.hangmanName);
                    HangmanState prevState;
                    prevState = (HangmanState) currSavManager.getLastState("auto", SaveManager.hangmanName);

                    Tile[][] prevTiles = prevState.getWordManager().getWord().getTiles();
                    wordManager.getWord().setTiles(prevTiles);

                    gridView.setWordManager(wordManager);
                    currSavManager.getLastState("auto", SaveManager.hangmanName).incrementNumMoves(prevMovesUndone);
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
     * Create the buttons for displaying the letters.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Word word = wordManager.getWord();
        tileButtons = new ArrayList<>();
        for (int col = 0; col != Word.numCols; col++) { // todo: adjust to kevin's implementation
                Button tmp = new Button(context);
                tmp.setBackgroundResource(word.getLetter(0, col).getBackground());
                this.tileButtons.add(tmp);

        }
    }

    /**
     * Update the backgrounds on the buttons to match the letters.
     */
    private void updateTileButtons() {
        Word word = wordManager.getWord();
        int nextPos = 0;

        for (Button b : tileButtons) {

            if (word.getLetter(0, nextPos).hidden){
                b.setBackgroundResource(R.drawable.letter_empty);
            }
            else{

                int backgroundId = word.getLetter(0, nextPos).getId();

                switch (backgroundId) {
                    case 0:
                        b.setBackgroundResource(R.drawable.letter_empty);
                        break;
                    case 65:
                        b.setBackgroundResource(R.drawable.letter_a);
                        break;
                    case 66:
                        b.setBackgroundResource(R.drawable.letter_b);
                        break;
                    case 67:
                        b.setBackgroundResource(R.drawable.letter_c);
                        break;
                    case 68:
                        b.setBackgroundResource(R.drawable.letter_d);
                        break;
                    case 69:
                        b.setBackgroundResource(R.drawable.letter_e);
                        break;
                    case 70:
                        b.setBackgroundResource(R.drawable.letter_f);
                        break;
                    case 71:
                        b.setBackgroundResource(R.drawable.letter_g);
                        break;
                    case 72:
                        b.setBackgroundResource(R.drawable.letter_h);
                        break;
                    case 73:
                        b.setBackgroundResource(R.drawable.letter_i);
                        break;
                    case 74:
                        b.setBackgroundResource(R.drawable.letter_j);
                        break;
                    case 75:
                        b.setBackgroundResource(R.drawable.letter_k);
                        break;
                    case 76:
                        b.setBackgroundResource(R.drawable.letter_l);
                        break;
                    case 77:
                        b.setBackgroundResource(R.drawable.letter_m);
                        break;
                    case 78:
                        b.setBackgroundResource(R.drawable.letter_n);
                        break;
                    case 79:
                        b.setBackgroundResource(R.drawable.letter_o);
                        break;
                    case 80:
                        b.setBackgroundResource(R.drawable.letter_p);
                        break;
                    case 81:
                        b.setBackgroundResource(R.drawable.letter_q);
                        break;
                    case 82:
                        b.setBackgroundResource(R.drawable.letter_r);
                        break;
                    case 83:
                        b.setBackgroundResource(R.drawable.letter_s);
                        break;
                    case 84:
                        b.setBackgroundResource(R.drawable.letter_t);
                        break;
                    case 85:
                        b.setBackgroundResource(R.drawable.letter_u);
                        break;
                    case 86:
                        b.setBackgroundResource(R.drawable.letter_v);
                        break;
                    case 87:
                        b.setBackgroundResource(R.drawable.letter_w);
                        break;
                    case 88:
                        b.setBackgroundResource(R.drawable.letter_x);
                        break;
                    case 89:
                        b.setBackgroundResource(R.drawable.letter_y);
                        break;
                    case 90:
                        b.setBackgroundResource(R.drawable.letter_z);
                        break;
                    default:
                        b.setBackgroundResource(R.drawable.letter_empty);
                }
            }
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
        loadFromFile("Account");
        display();
    }

    /**
     * Load scoreboard or account manager from fileName.
     *
     * @param type of loading: scoreboard or account manager
     */
    private void loadFromFile(String type) {

        try {
            InputStream inputStream = this.openFileInput(StartingLoginActivity.SAVE_ACCOUNT_MANAGER);
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
        Intent tmp = new Intent(this, HangmanStartingActivity.class);
        startActivity(tmp);
    }

    @Override
    public void update(Observable o, Object arg) {
        loadFromFile("Account");

        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        SaveManager currSavManager = currentAccount.getSaveManager();
        HangmanState lastAutoState = (HangmanState) currSavManager.getLastState("auto", SaveManager.hangmanName);
        int numMoves = currSavManager.getLength("auto", SaveManager.hangmanName);

        //Creating new game state with field values of the previous state.
        HangmanState newState = new HangmanState(wordManager, numMoves,
                HangmanComplexityActivity.complexity, SetUndoActivity.undo,
                lastAutoState.getNumMovesUndone(), lastAutoState.getUnlimitedUndo());
        currSavManager.addState(newState, SaveManager.hangmanName);
        saveToFile(StartingLoginActivity.SAVE_ACCOUNT_MANAGER, "Account");
        display();

        //Saving/Displaying the score if the game is over.
        if (newState.getWordManager().puzzleSolved()) {
            loadFromFile("scoreboard");
            scoreBoard.addToScoreBoard(scoreBoard.createScore(StartingLoginActivity.currentUser,
                    newState.getScore()));
            saveToFile(StartingLoginActivity.SAVE_SCOREBOARD, "scoreboard");
            currSavManager.wipeSave(SaveManager.auto,SaveManager.hangmanName);
            currSavManager.wipeSave(SaveManager.perma,SaveManager.hangmanName);
            saveToFile(StartingLoginActivity.SAVE_ACCOUNT_MANAGER, "Account");
            switchToWinning(); // todo: implement hangman winning and loosing
        }

    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        // The switch statement can be replaced by:
        //wordManager.keyBoard(keyCode + 36);
        //return true;

        switch (keyCode) {
            case KeyEvent.KEYCODE_A:
                wordManager.keyBoard(65);
                return true;

            case KeyEvent.KEYCODE_B:
                wordManager.keyBoard(66);
                return true;

            case KeyEvent.KEYCODE_C:
                wordManager.keyBoard(67);
                return true;

            case KeyEvent.KEYCODE_D:
                wordManager.keyBoard(68);
                return true;

            case KeyEvent.KEYCODE_E:
                wordManager.keyBoard(69);
                return true;

            case KeyEvent.KEYCODE_F:
                wordManager.keyBoard(70);
                return true;

            case KeyEvent.KEYCODE_G:
                wordManager.keyBoard(71);
                
                return true;

            case KeyEvent.KEYCODE_H:
                wordManager.keyBoard(72);
                
                return true;

            case KeyEvent.KEYCODE_I:
                wordManager.keyBoard(73);
                
                return true;

            case KeyEvent.KEYCODE_J:
                wordManager.keyBoard(74);
                
                return true;

            case KeyEvent.KEYCODE_K:
                wordManager.keyBoard(75);
                
                return true;

            case KeyEvent.KEYCODE_L:
                wordManager.keyBoard(76);
                
                return true;

            case KeyEvent.KEYCODE_M:
                wordManager.keyBoard(77);
                
                return true;

            case KeyEvent.KEYCODE_N:
                wordManager.keyBoard(78);
                
                return true;

            case KeyEvent.KEYCODE_O:
                wordManager.keyBoard(79);
                
                return true;

            case KeyEvent.KEYCODE_P:
                wordManager.keyBoard(80);
                
                return true;

            case KeyEvent.KEYCODE_Q:
                wordManager.keyBoard(81);
                
                return true;

            case KeyEvent.KEYCODE_R:
                wordManager.keyBoard(82);
                
                return true;

            case KeyEvent.KEYCODE_S:
                wordManager.keyBoard(83);
                
                return true;

            case KeyEvent.KEYCODE_T:
                wordManager.keyBoard(84);
                
                return true;

            case KeyEvent.KEYCODE_U:
                wordManager.keyBoard(85);
                
                return true;

            case KeyEvent.KEYCODE_V:
                wordManager.keyBoard(86);
                
                return true;

            case KeyEvent.KEYCODE_W:
                wordManager.keyBoard(87);
                
                return true;

            case KeyEvent.KEYCODE_X:
                wordManager.keyBoard(88);
                
                return true;

            case KeyEvent.KEYCODE_Y:
                wordManager.keyBoard(89);
                
                return true;

            case KeyEvent.KEYCODE_Z:
                wordManager.keyBoard(90);
                
                return true;
       
            default:
                return super.onKeyUp(keyCode, event);
        }
    }

    /**
     * Switch to the winning/loosing view.
     */
    private void switchToWinning() {
        Intent tmp = new Intent(this, WinningActivity.class); // todo: implement and connect to hangman winning and loosing
        startActivity(tmp);
    }
}
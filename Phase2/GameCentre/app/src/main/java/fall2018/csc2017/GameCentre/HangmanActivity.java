/*
View class
 */
package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * The game activity.
 */
public class HangmanActivity extends AppCompatActivity implements Observer, KeyEvent.Callback {

    /**
     * The controller for this activity.
     */
    private HangmanActivityController hangmanActivityController;

    /**
     * The board manager.
     */
    public static WordManager wordManager;

    /**
     * The current context for file reading/writing.
     */
    private Context currentContext = this;

    /**
     * The letters to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * The picture of the doll representing the Hangman
     */
    private static Button doll;

    // one-row only Grid View and calculated column height and width based on device size
    private HangmanGestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    AccountManager accountManager;

    FileSystem fileSystem;

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
        fileSystem = new FileSystem();
        DisplayToast displayToast = new DisplayToast();
        hangmanActivityController = new HangmanActivityController(fileSystem, displayToast);
        wordManager = HangmanStartingActivity.wordManager;
        createTileButtons(this);
        setContentView(R.layout.activity_hangman_main);
        addSaveButtonListener();
        addUndoButtonListener();
        // Add View to activity
        doll = findViewById(R.id.doll);
        doll.setBackgroundResource(R.drawable.hangman_head);
        gridView = findViewById(R.id.HangmanGrid);
        gridView.setNumColumns(Word.numCols);
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
                hangmanActivityController.saveListener(currentContext);
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
                hangmanActivityController.undoListener(currentContext);
                display();
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
        for (int col = 0; col != Word.numCols; col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(word.getLetter(col).getBackground());
                this.tileButtons.add(tmp);
        }
    }

    /**
     * Update the backgrounds on the buttons to match the letters.
     */
    private void updateTileButtons() {
        Word word = wordManager.getWord();

        switch (WordManager.tries){

            case 0:
                doll.setBackgroundResource(R.drawable.hangman_head);
                break;
            case 1:
                doll.setBackgroundResource(R.drawable.hangman_trunk);
                break;
            case 2:
                doll.setBackgroundResource(R.drawable.hangman_upper_limbs);
                break;
            case 3:
                doll.setBackgroundResource(R.drawable.hangman_lower_limbs);
                break;
            case 4:
                doll.setBackgroundResource(R.drawable.hangman_eyes);
                break;
            case 5:
                doll.setBackgroundResource(R.drawable.hangman_hands);
                break;
        }

        int nextPos = 0;

        for (Button b : tileButtons) {
            if (word.getLetter(nextPos).hidden){
                b.setBackgroundResource(R.drawable.letter_empty);
            }
            else{

                int backgroundId = word.getLetter(nextPos).getId();

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
    }

    /**
     * Dispatch onResume() to fragments.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onBackPressed() {
        Intent tmp = new Intent(this, HangmanStartingActivity.class);
        startActivity(tmp);
    }

    @Override
    public void update(Observable o, Object arg) {
        hangmanActivityController.updateGameListener(this);
        display();
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

}
/*
View
 */
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
 * The game activity for sudoku.
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
     * The filesystem.
     */
    private FileSystem fileSystem = new FileSystem();

    /**
     * The current context for file reading/writing.
     */
    private Context currentContext = this;

    private SudokuActivityController controller;

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * The current number selected.
     */
    public static int currentNumber = 1;

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
        Board.numRows = 9;
        Board.numCols = 9;
        super.onCreate(savedInstanceState);
        controller = new SudokuActivityController(fileSystem);
        boardManager = controller.onCreateListener(currentContext);

        createTileButtons(currentContext);
        setContentView(R.layout.activity_sudoku);
        addSaveButtonListener();
        addUndoButtonListener();
        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(SudokuBoard.numCols);
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

                        columnWidth = displayWidth / SudokuBoard.numCols;
                        columnHeight = displayHeight / SudokuBoard.numRows;

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
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.btnSudokuSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.saveListener(currentContext);
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
                Boolean undone = controller.undoListener(currentContext, boardManager);
                if (undone){
                    display();
                } else {
                    Toast.makeText(currentContext, "Max moves undone", Toast.LENGTH_SHORT).show();
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
        tileButtons = new ArrayList<>();
        for (int row = 0; row != SudokuBoard.numRows; row++) {
            for (int col = 0; col != SudokuBoard.numCols; col++) {
                Button tmp = new Button(context);
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
        controller.onPauseListener(currentContext);
    }

    /**
     * Dispatch onResume() to fragments.
     */
    @Override
    protected void onResume() {
        super.onResume();
        controller.onResumeListener(this);
    }

    @Override
    public void onBackPressed() {
        Intent tmp = new Intent(this, SudokuStartingActivity.class);
        startActivity(tmp);
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
        Boolean gameOver = controller.updateListener(currentContext, boardManager);
        if (gameOver){
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

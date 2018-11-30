/*
View class
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
 * The game activity for sliding tiles.
 */
public class SlidingTileActivity extends AppCompatActivity implements Observer {

    /**
     * The controller that manages the logic for this activity.
     */
    private SlidingTileActivityController slidingTileActivityController;

    /**
     * The board manager.
     */
    private SlidingTilesBoardManager slidingTilesBoardManager;

    /**
     * The current context for file reading/writing.
     */
    private Context currentContext = this;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons = new ArrayList<>();

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    public void display() {
        slidingTileActivityController.updateTileButtons(slidingTilesBoardManager,tileButtons);
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FileSystem fileSystem = new FileSystem();
        slidingTileActivityController = new SlidingTileActivityController(fileSystem);
        slidingTilesBoardManager = slidingTileActivityController.onCreateListener(currentContext);
        slidingTileActivityController.createTileButtons(this,slidingTilesBoardManager,this.tileButtons);
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
                slidingTileActivityController.saveListener(currentContext);
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
            boolean maxMoves = slidingTileActivityController.undoListener(currentContext, slidingTilesBoardManager);
            display();
            if (maxMoves){
                Toast.makeText(currentContext, "Max moves undone", Toast.LENGTH_SHORT).show();
            }
            }
        });
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
        slidingTileActivityController.onResumeListener(this);
    }

    /**
     * Goes back to the load game screen.
     */
    @Override
    public void onBackPressed() {
        Intent tmp = new Intent(this, SlidingTileStartingActivity.class);
        startActivity(tmp);
    }

    @Override
    public void update(Observable o, Object arg) {
        Boolean solved = slidingTileActivityController.updateGameListener(this, slidingTilesBoardManager);
        display();
        if (solved){
            Intent tmp = new Intent(this, WinningActivity.class);
            startActivity(tmp);
        }
    }

}

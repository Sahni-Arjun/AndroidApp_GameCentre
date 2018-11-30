/*
View
 */
package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * The activity responsible for adding the not robot feature.
 */
public class NotRobotActivity extends AppCompatActivity implements Observer {

    /**
     * the controller for this activity
     */
    private NotRobotActivityController controller;
    /**
     * The board manager.
     */
    private SlidingTilesBoardManager slidingTilesBoardManager = new SlidingTilesBoardManager();


    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons = new ArrayList<>();

    /**
     *  Grid View and calculated column height and width based on device size
     */
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    public void display() {
        controller.updateTileButtons(slidingTilesBoardManager,tileButtons);
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new NotRobotActivityController();
        Board.numRows = 2;
        Board.numCols = 2;
        controller.createTileButtons(this,slidingTilesBoardManager,tileButtons);
        setContentView(R.layout.not_robot);
        addNewGameButtonListener();

        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(2);
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

                        columnWidth = displayWidth / 2;
                        columnHeight = displayHeight / 2;

                        display();
                    }
                });
    }

    /**
     * Activates button for new board.
     */
    private void addNewGameButtonListener() {
        Button newButton = findViewById(R.id.New);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
            }
        });
    }

    /**
     * Switches to new game.
     */
    private void newGame() {
        Intent tmp = new Intent(this, NotRobotActivity.class);
        startActivity(tmp);
    }

    /**
     * Switch to new the main menu after the puzzle is solved.
     */
    private void switchToGameMenu() {
        Intent tmp = new Intent(this, MainMenuActivity.class);
        startActivity(tmp);
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
        if (slidingTilesBoardManager.puzzleSolved()) {
            switchToGameMenu();
        }
    }
}

/*
View
 */
package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * The activity which activates when the user wins the game.
 */
public class WinningActivity extends AppCompatActivity {

    /**
     * The file system.
     */
    private FileSystem fileSystem = new FileSystem();

    /**
     * The controller for the logic in the activity.
     */
    private WinningActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.controller = new WinningActivityController(fileSystem);
        setContentView(R.layout.activity_winning);
        String scoreMessage = controller.onOpenListener(this);
        TextView scoreTxt = findViewById(R.id.scoreText);
        scoreTxt.setText(scoreMessage);
        addBackButtonListener();
    }

    /**
     * displays the scores on screen.
     */
    @Override
    protected void onResume() {
        super.onResume();
        String scoreMessage = controller.onOpenListener(this);
        TextView scoreTxt = findViewById(R.id.scoreText);
        scoreTxt.setText(scoreMessage);
    }

    /**
     * Activate the Back Button.
     */
    private void addBackButtonListener() {
        Button saveButton = findViewById(R.id.backButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGameMenu();
            }
        });
    }

    /**
     * Switch to the SlidingTilesMenu to start a new game.
     */
    private void switchToGameMenu() {
        Intent tmp = new Intent(this, ChooseGameMenuActivity.class);
        startActivity(tmp);
    }

    @Override
    public void onBackPressed() {
        switchToGameMenu();
    }
}

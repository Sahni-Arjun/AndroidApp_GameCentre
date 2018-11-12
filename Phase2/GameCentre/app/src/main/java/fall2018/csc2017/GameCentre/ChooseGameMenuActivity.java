package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * The activity where you choose which game you want to launch.
 */
public class ChooseGameMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_game_menu);
        addSlidingTilesButtonListener();
    }

    /**
     * Activate the sliding tiles button.
     */
    private void addSlidingTilesButtonListener() {
        Button button = findViewById(R.id.SlidingTiles);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSlidingTilesMenu();
            }
        });
    }

    /**
     * Switch to the sliding tiles game.
     */
    private void switchToSlidingTilesMenu() {
        Intent tmp = new Intent(this, SlidingTileStartingActivity.class);
        startActivity(tmp);
    }

    /**
     * Go back to the main menu.
     */
    @Override
    public void onBackPressed() {
        Intent tmp = new Intent(this, MainMenuActivity.class);
        startActivity(tmp);
    }
}

package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * The activity which chooses the complexity of the sliding tile game.
 */
public class SlidingTileComplexityActivity extends AppCompatActivity {

    /**
     * The complexity of the game.
     */
    public static Integer complexity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tile_complexity);
        addButton3x3Listener();
        addButton4x4Listener();
        addButton5x5Listener();
    }

    @Override
    public void onBackPressed() {
    }

    /**
     * Switch to picture selection for the tiles.
     */
    private void switchToPicture() {
        Intent ChoosePicture = new Intent(this, SetUndoActivity.class);
        startActivity(ChoosePicture);
    }

    /**
     * Activate button to set complexity to 3.
     */
    private void addButton3x3Listener() {
        Button button3x3 = findViewById(R.id.button3x3);
        button3x3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {complexity = 3; switchToPicture();
            }
        });

    }

    /**
     * Activate button to set complexity to 4.
     */
    private void addButton4x4Listener() {
        Button button4x4 = findViewById(R.id.button4x4);
        button4x4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {complexity = 4; switchToPicture();
            }
        });
    }

    /**
     * Activate button to set complexity to 5.
     */
    private void addButton5x5Listener() {
        Button button5x5 = findViewById(R.id.button5x5);
        button5x5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {complexity = 5; switchToPicture();
            }
        });
    }
}

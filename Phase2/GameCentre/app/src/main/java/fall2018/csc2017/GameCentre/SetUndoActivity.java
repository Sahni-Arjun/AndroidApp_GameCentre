/*
View
 */
package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The activity which allows the user to choose number of moves to undo.
 */
public class SetUndoActivity extends AppCompatActivity {
    AccountManager accountManager;

    /**
     * The current context for file reading/writing.
     */
    private Context currentContext = this;

    /**
     * The controller that manages the logic of this class.
     */
    private SetUndoActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new SetUndoActivityController(new FileSystem());
        setContentView(R.layout.activity_set_undo);
        addSetUndoButtonListener();
    }

    /**
     * The user must finish making a saved game before being allowed to go back.
     */
    @Override
    public void onBackPressed() {
    }

    /**
     * Activate the SetUndoButton.
     */
    private void addSetUndoButtonListener() {
        Button buttonSetUndo = findViewById(R.id.btnSetUndo);
        buttonSetUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtSetUndo = findViewById(R.id.txtSetUndo);
                String setUndo = txtSetUndo.getText().toString().trim();
                Boolean validUndoValue = controller.setUndoListener(currentContext, setUndo);
                if (validUndoValue){
                    switchToGame();
                }else{
                    Toast.makeText(getApplicationContext(), "Invalid value entered",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Switch to the SlidingTileActivity to specify new picture.
     */
    private void switchToGame() {
        Intent StartingActivity = new Intent(this, SlidingTileActivity.class);
        startActivity(StartingActivity);
    }

}

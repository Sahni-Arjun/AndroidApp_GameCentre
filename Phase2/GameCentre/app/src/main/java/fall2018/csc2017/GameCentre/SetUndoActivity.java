package fall2018.csc2017.GameCentre;

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

    /**
     * The number of moves allowed to undo.
     */
    public static int undo;

    /**
     * If unlimited moves is allowed.
     */
    public static boolean unlimited = false;

    /**
     * If a valid number or the string "unlimited" was entered.
     */
    private boolean validValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_undo);
        addSetUndoButtonListener();
    }

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
                fillInSetUndo();
                if (validValue) {
                    switchToChoosePicture();
                }else{
                    Toast.makeText(getApplicationContext(), "Invalid value entered",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Switch to the SlidingTileChoosePictureActivity to specify new picture.
     */
    private void switchToChoosePicture() {
        Intent tmp = new Intent(this, SlidingTileChoosePictureActivity.class);
        startActivity(tmp);
    }

    /**
     * Save the undo value the User specifies.
     */
    private void fillInSetUndo(){
        EditText txtSetUndo = findViewById(R.id.txtSetUndo);
        String setUndo = txtSetUndo.getText().toString().trim();
        validValue = true;
        try {
            switch (setUndo) {
                case "unlimited":
                    unlimited = true;
                    break;
                case "":
                    undo = 0;
                    break;
                default:
                    undo = Integer.parseInt(setUndo);
                    break;
            }
        }catch (NumberFormatException e){
            validValue = false;
        }
    }
}

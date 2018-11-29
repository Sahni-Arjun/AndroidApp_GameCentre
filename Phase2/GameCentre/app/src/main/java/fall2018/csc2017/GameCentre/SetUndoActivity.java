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
     * The filesystem.
     */
    private FileSystem fileSystem;

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

    /**
     * The current context for file reading/writing.
     */
    private Context currentContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_undo);
        addSetUndoButtonListener();
        this.fileSystem = new FileSystem();
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
                    accountManager = fileSystem.loadAccount(currentContext);

                    Board.numCols = SlidingTileComplexityActivity.complexity;
                    Board.numRows = SlidingTileComplexityActivity.complexity;
                    SlidingTileStartingActivity.slidingTilesBoardManager = new SlidingTilesBoardManager();

                    Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
                    SaveManager currSavManager = currentAccount.getCurrentSaveManager(Account.slidingName);
                    currSavManager.wipeSave(SaveManager.auto);

                    //Start new game with chosen number of undos
                    SlidingTilesState newState = new
                            SlidingTilesState(SlidingTileStartingActivity.slidingTilesBoardManager, 0);
                    newState.setComplexity(SlidingTileComplexityActivity.complexity);
                    if (SetUndoActivity.unlimited) {
                        newState.setUnlimitedUndo();
                    } else {
                        newState.setMaxNumMovesUndone(SetUndoActivity.undo);
                    }
                    currSavManager.addState(newState);
                    fileSystem.saveAccount(currentContext, accountManager);
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

//    /**
//     * Activate default tile option button.
//     */
//    private void addDefaultButtonListener() {
//        Button DefaultButton = findViewById(R.id.DefaultButton);
//        DefaultButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                accountManager = fileSystem.loadAccount(currentContext);
//
//                Board.numCols = SlidingTileComplexityActivity.complexity;
//                Board.numRows = SlidingTileComplexityActivity.complexity;
//                SlidingTileStartingActivity.slidingTilesBoardManager = new SlidingTilesBoardManager();
//
//                Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
//                SaveManager currSavManager = currentAccount.getCurrentSaveManager(Account.slidingName);
//                currSavManager.wipeSave(SaveManager.auto, SaveManager.slidingTilesName);
//
//                //Start new game with chosen number of undos
//                SlidingTilesState newState = new
//                        SlidingTilesState(SlidingTileStartingActivity.slidingTilesBoardManager, 0);
//                newState.setComplexity(SlidingTileComplexityActivity.complexity);
//                if (SetUndoActivity.unlimited) {
//                    newState.setUnlimitedUndo();
//                } else {
//                    newState.setMaxNumMovesUndone(SetUndoActivity.undo);
//                }
//                currSavManager.addState(newState, SaveManager.slidingTilesName);
//                fileSystem.saveAccount(currentContext, accountManager);
//                switchToGame();
//            }
//        });
//    }

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

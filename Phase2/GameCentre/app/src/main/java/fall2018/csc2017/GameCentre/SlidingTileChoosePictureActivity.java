package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SlidingTileChoosePictureActivity extends AppCompatActivity {
    AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_tile_choose_picture);
        addDefaultButtonListener();
    }

    @Override
    public void onBackPressed() {  // no going back to board size choice
    }


    private void switchToGame() {
        Intent StartingActivity = new Intent(this, SlidingTileActivity.class);
        startActivity(StartingActivity);
    }

    /**
     * Activate default tile option button.
     */
    private void addDefaultButtonListener() {
        Button DefaultButton = findViewById(R.id.DefaultButton);
        DefaultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromFile();

                Board.numCols = SlidingTileComplexityActivity.complexity;
                Board.numRows = SlidingTileComplexityActivity.complexity;
                SlidingTileStartingActivity.slidingTilesBoardManager = new SlidingTilesBoardManager();

                Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
                SaveManager currSavManager = currentAccount.getSaveManager();
                currSavManager.wipeAutoSave(SaveManager.slidingTilesName);

                //Start new game with chosen number of undos
                SlidingTilesState newState = new
                        SlidingTilesState(SlidingTileStartingActivity.slidingTilesBoardManager, 0);
                newState.setComplexity(SlidingTileComplexityActivity.complexity);
                if (SetUndoActivity.unlimited) {
                    newState.setUnlimitedUndo();
                } else {
                    newState.setMaxNumMovesUndone(SetUndoActivity.undo);
                }
                currSavManager.addState(newState, SaveManager.slidingTilesName);
                saveToFile(StartingLoginActivity.SAVE_ACCOUNT_MANAGER);
                switchToGame();
            }
        });
    }

    /**
     * Load the board manager from fileName.
     */
    private void loadFromFile() {

        try {
            InputStream inputStream = this.openFileInput(StartingLoginActivity.SAVE_ACCOUNT_MANAGER);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                accountManager = (AccountManager) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(accountManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

}

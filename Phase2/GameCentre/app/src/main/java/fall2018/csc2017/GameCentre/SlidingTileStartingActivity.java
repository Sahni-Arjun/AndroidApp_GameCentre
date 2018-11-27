package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * The activity for loading games for the sliding puzzle tile game.
 */
public class SlidingTileStartingActivity extends AppCompatActivity {

    /**
     * The board manager.
     */
    public static SlidingTilesBoardManager slidingTilesBoardManager;

    /**
     * The filesystem.
     */

    private SlidingTilesStartingActivityController controller;

    /**
     * The current context for file reading/writing.
     */
    private Context currentContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FileSystem fileSystem = new FileSystem();
        DisplayToast displayToast = new DisplayToast();
        controller = new SlidingTilesStartingActivityController(fileSystem, displayToast);
        slidingTilesBoardManager = new SlidingTilesBoardManager();
        setContentView(R.layout.activity_starting_);

        addSlidingSave1ButtonListener();
        addSlidingSave2ButtonListener();
        addSlidingSave3ButtonListener();

        addSlidingContinue1ButtonListener();
        addSlidingContinue2ButtonListener();
        addSlidingContinue3ButtonListener();

        addSlidingNewGame1ButtonListener();
        addSlidingNewGame2ButtonListener();
        addSlidingNewGame3ButtonListener();
    }

    @Override
    public void onBackPressed() {
        Intent tmp = new Intent(this, ChooseGameMenuActivity.class);
        startActivity(tmp);
    }

    /**
     * Switch to the SlidingTileComplexityActivity to specify complexity.
     */
    void switchToTileComplexity() {
        Intent tmp = new Intent(this, SlidingTileComplexityActivity.class);
        startActivity(tmp);
    }


    /**
     * Read the account manager from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
//        accountManager = fileSystem.loadAccount(currentContext);
    }

    /**
     * Switch to the SlidingTileActivity view to play the game.
     */
    public void switchToGame() {
        Intent tmp = new Intent(this, SlidingTileActivity.class);
        startActivity(tmp);
    }

//    /**
//     * Load the account manager from fileName.
//     */
//    private void loadFromFile() {
//
//        try {
//            InputStream inputStream =
//                    this.openFileInput(StartingLoginActivity.SAVE_ACCOUNT_MANAGER);
//            if (inputStream != null) {
//                ObjectInputStream input = new ObjectInputStream(inputStream);
//                accountManager = (AccountManager) input.readObject();
//                inputStream.close();
//            }
//        } catch (FileNotFoundException e) {
//            Log.e("login activity", "File not found: " + e.toString());
//        } catch (IOException e) {
//            Log.e("login activity", "Can not read file: " + e.toString());
//        } catch (ClassNotFoundException e) {
//            Log.e("login activity", "File contained unexpected" +
//                    " data type: " + e.toString());
//        }
//    }
//
//    /**
//     * Save the account manager to fileName.
//     */
//    private void saveToFile() {
//        try {
//            ObjectOutputStream outputStream = new ObjectOutputStream(
//                    this.openFileOutput(StartingLoginActivity.SAVE_ACCOUNT_MANAGER, MODE_PRIVATE));
//            outputStream.writeObject(accountManager);
//            outputStream.close();
//        } catch (IOException e) {
//            Log.e("Exception", "File write failed: " + e.toString());
//        }
//    }

    /**
     * Activate the load save 1 button.
     */
    private void addSlidingSave1ButtonListener() {
        Button startButton = findViewById(R.id.slidingSave1);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.loadSave(1, currentContext);
            }
        });
    }

    /**
     * Activate the load save 2 button.
     */
    private void addSlidingSave2ButtonListener() {
        Button startButton = findViewById(R.id.slidingSave2);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.loadSave(2, currentContext);
            }
        });
    }

    /**
     * Activate the load save 3 button.
     */
    private void addSlidingSave3ButtonListener() {
        Button startButton = findViewById(R.id.slidingSave3);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.loadSave(3, currentContext);
            }
        });
    }

    /**
     * Activate the continue 1 button.
     */
    private void addSlidingContinue1ButtonListener() {
        Button startButton = findViewById(R.id.slidingContinue1);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.continueSave(1, currentContext);
            }
        });
    }

    /**
     * Activate the continue 2 button.
     */
    private void addSlidingContinue2ButtonListener() {
        Button startButton = findViewById(R.id.slidingContinue2);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.continueSave(2, currentContext);
            }
        });
    }

    /**
     * Activate the continue 3 button.
     */
    private void addSlidingContinue3ButtonListener() {
        Button startButton = findViewById(R.id.slidingContinue3);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.continueSave(3, currentContext);
            }
        });
    }

    /**
     * Activate the new game 1 button.
     */
    private void addSlidingNewGame1ButtonListener() {
        Button startButton = findViewById(R.id.slidingNewGame1);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.newGame(1, currentContext);
            }
        });
    }

    /**
     * Activate the new game 2 button.
     */
    private void addSlidingNewGame2ButtonListener() {
        Button startButton = findViewById(R.id.slidingNewGame2);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.newGame(2, currentContext);
            }
        });
    }

    /**
     * Activate the new game 3 button.
     */
    private void addSlidingNewGame3ButtonListener() {
        Button startButton = findViewById(R.id.slidingNewGame3);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.newGame(3, currentContext);
            }
        });
    }

}

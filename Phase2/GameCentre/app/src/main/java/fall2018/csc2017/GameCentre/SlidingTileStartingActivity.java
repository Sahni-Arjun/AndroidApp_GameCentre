package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * The activity for loading games for the sliding puzzle tile game.
 */
public class SlidingTileStartingActivity extends AppCompatActivity {

    /**
     * The board manager.
     */
    public static SlidingTilesBoardManager slidingTilesBoardManager;

    /**
     * The account manager.
     */
    private AccountManager accountManager;

    /**
     * The filesystem.
     */
    private FileSystem fileSystem;

    /**
     * The current context for file reading/writing.
     */
    private Context currentContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fileSystem = new FileSystem();
        slidingTilesBoardManager = new SlidingTilesBoardManager();
        setContentView(R.layout.activity_starting_);
        addNewGameButtonListener();
        addLoadButtonListener();
        addContinueButtonListener();

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
     * Activate the start button.
     */
    private void addNewGameButtonListener() {
        Button startButton = findViewById(R.id.NewGameButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToTileComplexity();
            }
        });
    }

    /**
     * Switch to the SlidingTileComplexityActivity to specify complexity.
     */
    private void switchToTileComplexity() {
        Intent tmp = new Intent(this, SlidingTileComplexityActivity.class);
        startActivity(tmp);
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountManager = fileSystem.loadAccount(currentContext);
                Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
                SaveManager currSavManager = currentAccount.getSaveManager();

                if (currSavManager.getLength("perma", SaveManager.slidingTilesName) != 0) {
                    slidingTilesBoardManager = ((SlidingTilesState) currSavManager.getLastState("perma", SaveManager.slidingTilesName)).getSlidingTilesBoardManager();
                    currSavManager.updateSave("auto", SaveManager.slidingTilesName);
                    SlidingTilesState prePermaState = (SlidingTilesState) currSavManager.getLastState("perma", SaveManager.slidingTilesName);
                    SlidingTileComplexityActivity.complexity = prePermaState.getComplexity();
                    Board.numRows = SlidingTileComplexityActivity.complexity;
                    Board.numCols = SlidingTileComplexityActivity.complexity;
                    makeToastLoadedText();
                    fileSystem.saveAccount(currentContext, accountManager);
                    switchToGame();
                } else {
                    Toast.makeText(getApplicationContext(), "Use common sense you can't" +
                            " load if you haven't saved yet!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the continue button.
     */
    private void addContinueButtonListener() {
        Button loadButton = findViewById(R.id.ContinueButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountManager = fileSystem.loadAccount(currentContext);
                Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
                SaveManager currSavManager = currentAccount.getSaveManager();

                if (currSavManager.getLength("auto", SaveManager.slidingTilesName) != 0) {
                    slidingTilesBoardManager = ((SlidingTilesState) currSavManager.getLastState("auto", SaveManager.slidingTilesName)).getSlidingTilesBoardManager();
                    SlidingTilesState lastAutoState = (SlidingTilesState) currSavManager.getLastState("auto", SaveManager.slidingTilesName);
                    SlidingTileComplexityActivity.complexity = lastAutoState.getComplexity();
                    Board.numRows = SlidingTileComplexityActivity.complexity;
                    Board.numCols = SlidingTileComplexityActivity.complexity;
                    makeToastLoadedText();
                    switchToGame();
                } else {
                    Toast.makeText(getApplicationContext(), "you can't continue a game " +
                            "that hasn't started!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Read the account manager from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        accountManager = fileSystem.loadAccount(currentContext);
    }

    /**
     * Switch to the SlidingTileActivity view to play the game.
     */
    private void switchToGame() {
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


    private void loadSave(int gameFile){
        accountManager = fileSystem.loadAccount(currentContext);
        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        currentAccount.setCurrentGame(gameFile);
        SaveManager currSavManager = currentAccount.getCurrentSaveManager(Account.slidingName);

        if (currSavManager.getLength(SaveManager.perma, SaveManager.slidingTilesName) != 0) {
            slidingTilesBoardManager = ((SlidingTilesState) currSavManager.getLastState(SaveManager.perma, SaveManager.slidingTilesName)).getSlidingTilesBoardManager();
            currSavManager.updateSave(SaveManager.auto, SaveManager.slidingTilesName);
            SlidingTilesState prePermaState = (SlidingTilesState) currSavManager.getLastState(SaveManager.perma, SaveManager.slidingTilesName);
            SlidingTileComplexityActivity.complexity = prePermaState.getComplexity();
            Board.numRows = SlidingTileComplexityActivity.complexity;
            Board.numCols = SlidingTileComplexityActivity.complexity;
            makeToastLoadedText();
            fileSystem.saveAccount(currentContext, accountManager);
            switchToGame();
        } else {
            Toast.makeText(getApplicationContext(), "Use common sense you can't" +
                    " load if you haven't saved yet!", Toast.LENGTH_SHORT).show();
        }
    }

    private void continueSave(int gameFile){
        accountManager = fileSystem.loadAccount(currentContext);
        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        currentAccount.setCurrentGame(gameFile);
        SaveManager currSavManager = currentAccount.getCurrentSaveManager(Account.slidingName);

        if (currSavManager.getLength(SaveManager.auto, SaveManager.slidingTilesName) != 0) {
            slidingTilesBoardManager = ((SlidingTilesState) currSavManager.getLastState(SaveManager.auto, SaveManager.slidingTilesName)).getSlidingTilesBoardManager();
            SlidingTilesState lastAutoState = (SlidingTilesState) currSavManager.getLastState(SaveManager.auto, SaveManager.slidingTilesName);
            SlidingTileComplexityActivity.complexity = lastAutoState.getComplexity();
            Board.numRows = SlidingTileComplexityActivity.complexity;
            Board.numCols = SlidingTileComplexityActivity.complexity;
            makeToastLoadedText();
            switchToGame();
        } else {
            Toast.makeText(getApplicationContext(), "you can't continue a game " +
                    "that hasn't started!", Toast.LENGTH_SHORT).show();
        }
    }

    private void newGame(int gameFile){
        accountManager = fileSystem.loadAccount(currentContext);
        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        currentAccount.setCurrentGame(gameFile);
        fileSystem.saveAccount(currentContext, accountManager);
        switchToTileComplexity();
    }

    /**
     * Activate the load save 1 button.
     */
    private void addSlidingSave1ButtonListener() {
        Button startButton = findViewById(R.id.slidingSave1);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSave(1);
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
                loadSave(2);
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
                loadSave(3);
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
                continueSave(1);
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
                continueSave(2);
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
                continueSave(3);
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
                newGame(1);
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
                newGame(2);
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
                newGame(3);
            }
        });
    }

}

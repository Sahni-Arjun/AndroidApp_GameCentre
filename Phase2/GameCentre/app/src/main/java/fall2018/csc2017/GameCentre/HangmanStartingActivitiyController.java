package fall2018.csc2017.GameCentre;

import android.content.Context;

public class HangmanStartingActivitiyController {

    /**
     * The filesystem for this class.
     */
    private FileSystem fileSystem;

    /**
     * The account manager for this class.
     */
    private AccountManager accountManager;

    /**
     * Initializes the fileSystem for the controller.
     *
     * @param fileSystem the fileSystem
     */
    HangmanStartingActivitiyController(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    /**
     * The logic that must be processed before the new game is created.
     *
     * @param context the context for the activity
     */
    void newGameButtonListener(Context context) {
    }

    /**
     * The logic that must be processed before the game is loaded.
     *
     * @param context the context for the activity
     */
    void loadButtonListener(Context context) {
    }

    /**
     * The logic that must be processed before the game is continued.
     * @param context the context for the activity
     */
    void continueButtonListener(Context context) {
    }
}

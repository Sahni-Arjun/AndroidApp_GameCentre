package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.content.Intent;
import android.content.Context;


public class HangmanActivityController {

    /**
     * The filesystem.
     */
    private FileSystem fileSystem;

    /**
     * The account manager for the app.
     */
    private AccountManager accountManager;

    /**
     * The toast view class.
     */
    private DisplayToast displayToast;

    HangmanActivityController(FileSystem fileSystem, DisplayToast displayToast){
        this.fileSystem = fileSystem;
        this. displayToast = displayToast;
    }

    void undoListener(Context context){
        // load the account manager.
        accountManager = fileSystem.loadAccount(context);

        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        SaveManager currSavManager = currentAccount.getCurrentSaveManager(Account.hangmanName);

        boolean undone = currSavManager.undoMove("hangman");
        if (undone){
            // update the current word manager with the new letters.
            HangmanActivity.wordManager.getWord().setLetters(currSavManager.getWordArrangement());
            fileSystem.saveAccount(context, accountManager);
        }else{
            displayToast.displayToast(context, "Max moves undone");
        }
    }

    void updateGameListener(Context context){
        accountManager = fileSystem.loadAccount(context);

        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        SaveManager currSavManager = currentAccount.getCurrentSaveManager(Account.hangmanName);

        currSavManager.updateState(SaveManager.hangmanName, HangmanActivity.wordManager);

        fileSystem.saveAccount(context, accountManager);

        HangmanState prevState = (HangmanState) currSavManager.getLastState(SaveManager.auto, SaveManager.hangmanName);
        WordManager wordManager = prevState.getWordManager();
        //Saving/Displaying the score if the game is over.
        if (wordManager.puzzleSolved()) {
            Scoreboard scoreboard = fileSystem.loadScoreboard(context, StartingLoginActivity.SAVE_HANGMAN_SCOREBOARD);

            scoreboard.addToScoreBoard(scoreboard.createScore(StartingLoginActivity.currentUser,
                    currSavManager.getFinalScore(SaveManager.hangmanName)));

            fileSystem.saveScoreBoard(context, StartingLoginActivity.SAVE_HANGMAN_SCOREBOARD, scoreboard);
            accountManager.findUser(StartingLoginActivity.currentUser).setLastPlayedGame(Account.hangmanName);

            currSavManager.wipeSave(SaveManager.auto, SaveManager.hangmanName);
            currSavManager.wipeSave(SaveManager.perma, SaveManager.hangmanName);

            fileSystem.saveAccount(context, accountManager);

            Account user = accountManager.findUser(StartingLoginActivity.currentUser);
            user.setLastPlayedGame(Account.hangmanName);
            Intent win = new Intent(context, WinningActivity.class);
            context.startActivity(win);

        }

        if (WordManager.tries > 5){

            Account user = accountManager.findUser(StartingLoginActivity.currentUser);
            user.setLastPlayedGame(Account.hangmanName);
            Intent loose = new Intent(context, LoosingActivity.class);
            context.startActivity(loose);
        }

    }

    void saveListener(Context context){
        accountManager = fileSystem.loadAccount(context);

        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        currentAccount.getCurrentSaveManager(Account.hangmanName).updateSave(SaveManager.perma, SaveManager.hangmanName);

        fileSystem.saveAccount(context, accountManager);
    }
}


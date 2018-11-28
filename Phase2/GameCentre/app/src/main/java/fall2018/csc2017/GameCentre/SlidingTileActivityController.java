package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.content.Intent;

class SlidingTileActivityController{
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

    SlidingTileActivityController(FileSystem fileSystem, DisplayToast displayToast){
        this.fileSystem = fileSystem;
        this. displayToast = displayToast;
    }

    void onCreateListener(SlidingTileActivity context){
        accountManager = fileSystem.loadAccount(context);
        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        SaveManager currSavManager = currentAccount.getCurrentSaveManager(Account.slidingName);
        String continueOrLoad = currSavManager.getContinueOrLoad();
        SlidingTilesBoardManager newBoardManager = ((SlidingTilesState)currSavManager.getLastState(continueOrLoad, SaveManager.slidingTilesName)).getSlidingTilesBoardManager();
        context.setSlidingTilesBoardManager(newBoardManager);
    }

    void undoListener(SlidingTileActivity context){
        // load the account manager.
        accountManager = fileSystem.loadAccount(context);

        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        SaveManager currSavManager = currentAccount.getCurrentSaveManager(Account.slidingName);

        boolean undone = currSavManager.undoMove();
        if (undone){
            // update the current board manager with the new tiles.
            context.getSlidingTilesBoardManager().getBoard().setTiles(currSavManager.getboardArrangement());
            fileSystem.saveAccount(context, accountManager);
        }else{
            displayToast.displayToast(context, "Max moves undone");
        }
    }

    void updateGameListener(SlidingTileActivity context){
        accountManager = fileSystem.loadAccount(context);

        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        SaveManager currSavManager = currentAccount.getCurrentSaveManager(Account.slidingName);

        currSavManager.updateState(SaveManager.slidingTilesName, (context.getSlidingTilesBoardManager()));

        fileSystem.saveAccount(context, accountManager);

        SlidingTilesState prevState = (SlidingTilesState) currSavManager.getLastState(SaveManager.auto, SaveManager.slidingTilesName);
        SlidingTilesBoardManager slidingTilesBoardManager = prevState.getSlidingTilesBoardManager();
        //Saving/Displaying the score if the game is over.
        if (slidingTilesBoardManager.puzzleSolved()) {
            Scoreboard scoreboard = fileSystem.loadScoreboard(context, StartingLoginActivity.SAVE_SLIDING_SCOREBOARD);

            scoreboard.addToScoreBoard(scoreboard.createScore(StartingLoginActivity.currentUser,
                    prevState.getScore()));

            fileSystem.saveScoreBoard(context, StartingLoginActivity.SAVE_SLIDING_SCOREBOARD, scoreboard);

            accountManager.findUser(StartingLoginActivity.currentUser).setLastPlayedGame(Account.slidingName);
            currSavManager.wipeSave(SaveManager.auto, SaveManager.slidingTilesName);
            currSavManager.wipeSave(SaveManager.perma, SaveManager.slidingTilesName);

            fileSystem.saveAccount(context, accountManager);

            Intent tmp = new Intent(context, WinningActivity.class);
            context.startActivity(tmp);
        }
    }

    void saveListener(Context context){
        accountManager = fileSystem.loadAccount(context);

        Account currentAccount = accountManager.findUser(StartingLoginActivity.currentUser);
        currentAccount.getCurrentSaveManager(Account.slidingName).updateSave(SaveManager.perma, SaveManager.slidingTilesName);

        fileSystem.saveAccount(context, accountManager);
    }
}

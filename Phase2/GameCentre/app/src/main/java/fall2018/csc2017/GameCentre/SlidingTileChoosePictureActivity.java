//package fall2018.csc2017.GameCentre;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.Button;
//
//public class SlidingTileChoosePictureActivity extends AppCompatActivity {
//    AccountManager accountManager;
//
//    /**
//     * The filesystem.
//     */
//    private FileSystem fileSystem;
//
//    /**
//     * The current context for file reading/writing.
//     */
//    private Context currentContext = this;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        fileSystem = new FileSystem();
//        setContentView(R.layout.activity_sliding_tile_choose_picture);
//        addDefaultButtonListener();
//    }
//
//    @Override
//    public void onBackPressed() {  // no going back to board size choice
//    }
//
//
//    private void switchToGame() {
//        Intent StartingActivity = new Intent(this, SlidingTileActivity.class);
//        startActivity(StartingActivity);
//    }
//
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
//                currSavManager.wipeSave(SaveManager.auto);
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
//                currSavManager.addState(newState);
//                fileSystem.saveAccount(currentContext, accountManager);
//                switchToGame();
//            }
//        });
//    }
//}

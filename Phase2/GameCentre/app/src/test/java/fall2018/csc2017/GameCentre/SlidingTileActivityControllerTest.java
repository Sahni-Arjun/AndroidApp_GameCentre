package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SlidingTileActivityControllerTest {
    /**
     * The slidingTileActivityController to test.
     */
    SlidingTileActivityController slidingTileActivityController;

    SlidingTilesBoardManager slidingTilesBoardManager;

    Account user;

    /**
     * Make a set of tiles:
     * 9 1 3
     * 2 7 4
     * 8 5 6
     * @return a set of tiles
     */
    private List<Tile> makeTiles() {
        Board.numRows = 3;
        Board.numCols = 3;
        SlidingTileComplexityActivity.complexity = 3;
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(9, 8)); //The blank tile.
        tiles.add(new Tile(1, 0));
        tiles.add(new Tile(3, 2));
        tiles.add(new Tile(2, 1));
        tiles.add(new Tile(7, 6));
        tiles.add(new Tile(4, 3));
        tiles.add(new Tile(8, 7));
        tiles.add(new Tile(5, 4));
        tiles.add(new Tile(6, 5));
        return tiles;
    }

    /**
     * Make a starting Board.
     */
    private void setUpStartingBoard() {
        List<Tile> tiles = makeTiles();
        Board board = new Board(tiles);
        slidingTilesBoardManager = new SlidingTilesBoardManager(board);
    }

    /**
     * Make 3 moves.
     */
    private void setUpAccountWithGame() {
        makeTiles();
        setUpStartingBoard();
        user = new Account("Hello", "World");
        user.setCurrentGame(1);
        SaveManager saveManager = user.getSaveManager();
        SlidingTilesState slidingTilesState1 = new SlidingTilesState(slidingTilesBoardManager, 0, 3, 3, 0, false);
        saveManager.addState(slidingTilesState1, SaveManager.slidingTilesName);

        /**
         * 9 1 3
         * 2 7 4
         * 8 5 6
         */
        slidingTilesBoardManager.getBoard().swapTiles(0, 0, 0, 1);
        SlidingTilesState slidingTilesState2 = new SlidingTilesState(slidingTilesBoardManager, 0, 3, 3, 0, false);
        saveManager.addState(slidingTilesState2, SaveManager.slidingTilesName);
        /**
         * 1 9 3
         * 2 7 4
         * 8 5 6
         */

        slidingTilesBoardManager.getBoard().swapTiles(0, 1, 1, 1);
        SlidingTilesState slidingTilesState3 = new SlidingTilesState(slidingTilesBoardManager, 0, 3, 3, 0, false);
        saveManager.addState(slidingTilesState3, SaveManager.slidingTilesName);
        /**
         * 1 7 3
         * 2 9 4
         * 8 5 6
         */
    }

    @Before
    public void setUp(){
        slidingTileActivityController = new SlidingTileActivityController(
                new FileSystem(){
                    public AccountManager loadAccount(Context context){
                        List<Account> emptyAccounts = new ArrayList<>();
                        AccountManager accountManager = new AccountManager(emptyAccounts);
                        Account user = new Account("Hello", "World");
                        user.setCurrentGame(1);
                        SaveManager saveManager = user.getCurrentSaveManager(Account.slidingName);
                        SlidingTilesState slidingTilesState = new SlidingTilesState(slidingTilesBoardManager, 0, 3, 3, 0, false);
                        saveManager.addState(slidingTilesState, SaveManager.slidingTilesName);

                        accountManager.addUser(user);
                        return accountManager;
                    }

                    public void saveAccount(Context context, AccountManager accountManager){
                    }
                }, new DisplayToast(){
            public void displayToast(Context Context, String message){
                System.out.println(message);
            }
        }
        );
    }

//    /**
//     * Test whether pressing undo will revert back to the previous slidingTile board.
//     */
//    @Test
//    public void testUndo(){
//        Context context = new AppCompatActivity();
//        slidingTileActivityController.undoListener(context);
//
//    }
}

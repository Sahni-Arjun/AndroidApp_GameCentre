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
    private SlidingTileActivityController slidingTileActivityController;

    private SlidingTilesBoardManager slidingTilesBoardManager;

    private Account user;

    /**
     * Make a set of tiles:
     * 9 1 3
     * 2 7 4
     * 8 5 6
     * @return a set of tiles
     */
    private Tile[][] makeTiles() {
        SlidingTileComplexityActivity.complexity = 3;
        Tile[][] tiles = new Tile[3][3];

        tiles[0][0] = new Tile(9, 8);
        tiles[0][1] = new Tile(1, 0);
        tiles[0][2] = new Tile(3, 2);
        tiles[1][0] = new Tile(2, 1);
        tiles[1][1] = new Tile(7, 6);
        tiles[1][2] = new Tile(4, 3);
        tiles[2][0] = new Tile(8, 7);
        tiles[2][1] = new Tile(5, 4);
        tiles[2][2] = new Tile(6, 5);
        return tiles;
    }

    /**
     * Make a starting Board.
     */
    private void setUpStartingBoard() {
        Tile[][] tiles = makeTiles();

        Board board = new Board();
        board.setTiles(tiles);
        slidingTilesBoardManager = new SlidingTilesBoardManager(board);
    }

    private Tile[][] deepCopy(Tile[][] old){
        Tile[][] newTiles = new Tile[3][3];
        for(int i = 0; i < 3; i++){
            newTiles[i] = old[i].clone();
        }

        return newTiles;
    }

    /**
     * Make 3 moves.
     */
    private void setUpAccountWithGame() {
        setUpStartingBoard();
        user = new Account("Hello", "World");
        user.setCurrentGame(1);
        SaveManager saveManager = user.getCurrentSaveManager(Account.slidingName);

        SlidingTilesBoardManager boardManager = new SlidingTilesBoardManager();
        Board board = new Board();
        board.setTiles(deepCopy(slidingTilesBoardManager.getBoard().getTiles()));
        boardManager.setBoard(board);

        SlidingTilesState slidingTilesState1 = new SlidingTilesState(boardManager, 0, 3, 3, 0, true);
        saveManager.addState(slidingTilesState1, SaveManager.slidingTilesName);
        /**
         * 9 1 3
         * 2 7 4
         * 8 5 6
         */

        slidingTilesBoardManager.swapTiles(0, 0, 0, 1);
        boardManager = new SlidingTilesBoardManager();
        board = new Board();
        board.setTiles(deepCopy(slidingTilesBoardManager.getBoard().getTiles()));
        boardManager.setBoard(board);
        saveManager.updateState(SaveManager.slidingTilesName, boardManager);
        /**
         * 1 9 3
         * 2 7 4
         * 8 5 6
         */

        slidingTilesBoardManager.swapTiles(0, 1, 1, 1);
        boardManager = new SlidingTilesBoardManager();
        board = new Board();
        board.setTiles(deepCopy(slidingTilesBoardManager.getBoard().getTiles()));
        boardManager.setBoard(board);
        saveManager.updateState(SaveManager.slidingTilesName, boardManager);
        /**
         * 1 7 3
         * 2 9 4
         * 8 5 6
         */
    }

    @Before
    public void setUp(){
        StartingLoginActivity.currentUser = "Hello";
        setUpAccountWithGame();
//        SlidingTileActivity.slidingTilesBoardManager = new SlidingTilesBoardManager();
        slidingTileActivityController = new SlidingTileActivityController(
                new FileSystem(){
                    public AccountManager loadAccount(Context context){
                        List<Account> emptyAccounts = new ArrayList<>();
                        AccountManager accountManager = new AccountManager(emptyAccounts);
                        accountManager.addUser(user);
                        return accountManager;
                    }

                    public void saveAccount(Context context, AccountManager accountManager){
                        user = accountManager.findUser("Hello");
                    }
                }, new DisplayToast(){
            public void displayToast(Context Context, String message){
                System.out.println(message);
            }
        }
        );
    }

    /**
     * Test whether pressing undo will revert back to the previous slidingTile board.
     */
    @Test
    public void testUndo(){
        Context context = new AppCompatActivity();
        SaveManager saveManager = user.getCurrentSaveManager(Account.slidingName);
        Tile[][] oldTiles = makeTiles();

        slidingTileActivityController.undoListener((SlidingTileActivity) context);
        slidingTileActivityController.undoListener((SlidingTileActivity) context);

        SlidingTilesState state = (SlidingTilesState) saveManager.getLastState(SaveManager.auto, SaveManager.slidingTilesName);
        Tile[][] newTiles = state.getSlidingTilesBoardManager().getBoard().getTiles();

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                assertEquals(newTiles[i][j].getBackground(),oldTiles[i][j].getBackground());
            }
        }
    }
}

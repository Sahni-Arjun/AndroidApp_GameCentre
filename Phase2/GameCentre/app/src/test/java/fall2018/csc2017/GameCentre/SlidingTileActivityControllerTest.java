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
     * 9 1 3 // 9 is the blank tile.
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
        saveManager.addState(slidingTilesState1);
        /*
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
        /*
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
        /*
         * 1 7 3
         * 2 9 4
         * 8 5 6
         */
    }

    @Before
    public void setUp(){
        StartingLoginActivity.currentUser = "Hello";
        setUpAccountWithGame();
        slidingTileActivityController = new SlidingTileActivityController(
                new FileSystem() {
                    public AccountManager loadAccount(Context context) {
                        List<Account> emptyAccounts = new ArrayList<>();
                        AccountManager accountManager = new AccountManager(emptyAccounts);
                        accountManager.addUser(user);
                        return accountManager;
                    }

                    public void saveAccount(Context context, AccountManager accountManager) {
                        user = accountManager.findUser("Hello");
                    }

                    public Scoreboard loadScoreboard(Context context, String filename) {
                        return new Scoreboard();
                    }

                    void saveScoreBoard(Context context, String filename, Scoreboard scrboard) {
                    }
                }
        );
    }

    /**
     * Test whether pressing undo will revert back to the previous slidingTile board.
     */
    @Test
    public void testUndoWhenFalse(){
        Context context = new AppCompatActivity();
        SaveManager saveManager = user.getCurrentSaveManager(Account.slidingName);
        Tile[][] oldTiles = makeTiles();

        SlidingTilesBoardManager activityBoardManager = new SlidingTilesBoardManager();

        slidingTileActivityController.undoListener(context, activityBoardManager);
        boolean maxUndone = slidingTileActivityController.undoListener(context, activityBoardManager);

        assertFalse(maxUndone);

        SlidingTilesState state = (SlidingTilesState) saveManager.getLastState(SaveManager.auto);
        Tile[][] newTiles = state.getSlidingTilesBoardManager().getBoard().getTiles();

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                assertEquals(newTiles[i][j].getBackground(),oldTiles[i][j].getBackground());
            }
        }
    }

    /**
     * Test whether pressing undo will revert back to the previous slidingTile board.
     */
    @Test
    public void testUndoWhenTrue(){
        Context context = new AppCompatActivity();
        SlidingTilesBoardManager activityBoardManager = new SlidingTilesBoardManager();

        slidingTileActivityController.undoListener(context, activityBoardManager);
        slidingTileActivityController.undoListener(context, activityBoardManager);
        boolean maxUndone = slidingTileActivityController.undoListener(context, activityBoardManager);

        assertTrue(maxUndone);
    }

    /**
     * Test whether pressing save will update the permanent save to the latest auto save.
     */
    @Test
    public void testSave(){
        Context context = new AppCompatActivity();
        SaveManager saveManager = user.getCurrentSaveManager(Account.slidingName);

        assertEquals(0, saveManager.getLength(SaveManager.perma));

        slidingTileActivityController.saveListener(context);

        SlidingTilesState permaState = (SlidingTilesState) saveManager.getLastState(SaveManager.auto);
        SlidingTilesState autoState = (SlidingTilesState) saveManager.getLastState(SaveManager.auto);

        assertEquals(permaState, autoState);
        assertEquals(3, saveManager.getLength(SaveManager.perma));
    }

    /**
     * Test whether the game autosaves the correct state when a new move is made.
     */
    @Test
    public void testUpdateWhenReturnsFalse(){
        Context context = new AppCompatActivity();
        SaveManager saveManager = user.getCurrentSaveManager(Account.slidingName);

        slidingTilesBoardManager.swapTiles(1, 1, 1, 2);
        SlidingTilesBoardManager boardManager = new SlidingTilesBoardManager();
        Board board = new Board();
        board.setTiles(deepCopy(slidingTilesBoardManager.getBoard().getTiles()));
        boardManager.setBoard(board);
        /*
         * 1 7 3
         * 2 4 9
         * 8 5 6
         */

        SlidingTilesState prevAutoState = (SlidingTilesState) saveManager.getLastState(SaveManager.auto);
        int numMoves = prevAutoState.getNumMoves();
        int complexity = prevAutoState.getComplexity();
        int numMovesUndone = prevAutoState.getNumMovesUndone();
        boolean unlimitedUndo = prevAutoState.getUnlimitedUndo();

        Boolean gameOver = slidingTileActivityController.updateGameListener(context, boardManager);

        assertFalse(gameOver);

        SlidingTilesState autoState = (SlidingTilesState) saveManager.getLastState(SaveManager.auto);
        int newNumMoves = autoState.getNumMoves();
        int newComplexity = autoState.getComplexity();
        int newNumMovesUndone = autoState.getNumMovesUndone();
        boolean newUnlimitedUndo = autoState.getUnlimitedUndo();

        assertEquals(numMoves, newNumMoves - 1);
        assertEquals(complexity, newComplexity);
        assertEquals(numMovesUndone, newNumMovesUndone);
        assertEquals(unlimitedUndo, newUnlimitedUndo);

        Tile[][] newTiles = autoState.getSlidingTilesBoardManager().getBoard().getTiles();

        assertEquals(1, newTiles[0][0].getId());
        assertEquals(7, newTiles[0][1].getId());
        assertEquals(3, newTiles[0][2].getId());
        assertEquals(2, newTiles[1][0].getId());
        assertEquals(4, newTiles[1][1].getId());
        assertEquals(9, newTiles[1][2].getId());
        assertEquals(8, newTiles[2][0].getId());
        assertEquals(5, newTiles[2][1].getId());
        assertEquals(6, newTiles[2][2].getId());

        assertEquals(4, saveManager.getLength(SaveManager.auto));
    }

    /**
     * Test whether update function will return true when game is over.
     */
    @Test
    public void testUpdateWhenReturnsTrue(){
        Context context = new AppCompatActivity();
        SaveManager saveManager = user.getCurrentSaveManager(Account.slidingName);

        Tile[][] tiles = new Tile[3][3];

        tiles[0][0] = new Tile(1, 0);
        tiles[0][1] = new Tile(2, 1);
        tiles[0][2] = new Tile(3, 2);
        tiles[1][0] = new Tile(4, 3);
        tiles[1][1] = new Tile(5, 4);
        tiles[1][2] = new Tile(6, 5);
        tiles[2][0] = new Tile(7, 6);
        tiles[2][1] = new Tile(8, 7);
        tiles[2][2] = new Tile(9, 8);

        SlidingTilesBoardManager boardManager = new SlidingTilesBoardManager();
        Board board = new Board();
        Board.numCols = 3;
        Board.numRows = 3;
        board.setTiles(tiles);
        boardManager.setBoard(board);
        /*
         * 1 2 3
         * 4 5 6
         * 7 8 9
         */

        Boolean gameOver = slidingTileActivityController.updateGameListener(context, boardManager);

        assertTrue(gameOver);

        int autoLength = saveManager.getLength(SaveManager.auto);
        int permaLength = saveManager.getLength(SaveManager.perma);

        assertEquals(0, autoLength);
        assertEquals(0, permaLength);
    }

    /**
     * Test whether the onCreate function returns a valid boardManager.
     */
    @Test
    public void testOnCreate(){
        Context context = new AppCompatActivity();
        SlidingTilesBoardManager boardManager = slidingTileActivityController.onCreateListener(context);
        SaveManager saveManager = user.getCurrentSaveManager(Account.slidingName);
        SlidingTilesState state = (SlidingTilesState) saveManager.getLastState(SaveManager.auto);
        SlidingTilesBoardManager originalBoardManager = state.getSlidingTilesBoardManager();

        assertEquals(originalBoardManager, boardManager);
    }
}

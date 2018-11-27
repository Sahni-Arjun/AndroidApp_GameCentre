package fall2018.csc2017.GameCentre;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class SlidingTilesBoardManagerTest {
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

        slidingTilesBoardManager.getBoard().swapTiles(0, 0, 0, 1);
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

        slidingTilesBoardManager.getBoard().swapTiles(0, 1, 1, 1);
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
        SlidingTileActivity slidingTileActivity = new SlidingTileActivity();
        slidingTileActivity.setSlidingTilesBoardManager(new SlidingTilesBoardManager());
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

    // Constructor test
    // Test that the board constructed is solvable
    @Test
    public void slidingTiles3x3BoardManagerConstructorTest() {
        SlidingTileComplexityActivity.complexity = 3;
        SlidingTilesBoardManager boardManager = new SlidingTilesBoardManager();
        Board board = boardManager.getBoard();
        // Test if the number of tiles is 9 when the complexity is 3
        assertEquals(String.format("Expected %d to be equal to %d", 9, board.numTiles()), 9, board.numTiles());
        List<Integer> tileNums = new ArrayList<>();
        for (Tile tile: board) {
            tileNums.add(tile.getId());
        }
        Collections.sort(tileNums);
        List<Integer> listOfAllNumbers = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            listOfAllNumbers.add(i);
        }
        assertEquals(String.format("Expected %s to be equal to %s", listOfAllNumbers.toString(), tileNums.toString()), tileNums, listOfAllNumbers);
    }

    //TODO: complexity set up in setup
    @Test
    public void slidingTiles4x4BoardManagerConstructorTest() {
        SlidingTileComplexityActivity.complexity = 4;
        SlidingTilesBoardManager boardManager = new SlidingTilesBoardManager();
        Board board = boardManager.getBoard();
        // Test if the number of tiles is 9 when the complexity is 3
        assertEquals(String.format("Expected %d to be equal to %d", 16, board.numTiles()), 16, board.numTiles());
        List<Integer> tileNums = new ArrayList<>();
        for (Tile tile: board) {
            tileNums.add(tile.getId());
        }
        Collections.sort(tileNums);
        List<Integer> listOfAllNumbers = new ArrayList<>();
        for (int i = 1; i < 17; i++) {
            listOfAllNumbers.add(i);
        }
        assertEquals(String.format("Expected %s to be equal to %s", listOfAllNumbers.toString(), tileNums.toString()), tileNums, listOfAllNumbers);
    }

    @Test
    public void slidingTiles5x5BoardManagerConstructorTest() {
        SlidingTileComplexityActivity.complexity = 5;
        SlidingTilesBoardManager boardManager = new SlidingTilesBoardManager();
        Board board = boardManager.getBoard();
        // Test if the number of tiles is 9 when the complexity is 3
        assertEquals(String.format("Expected %d to be equal to %d", 25, board.numTiles()), 25, board.numTiles());
        List<Integer> tileNums = new ArrayList<>();
        for (Tile tile: board) {
            tileNums.add(tile.getId());
        }
        Collections.sort(tileNums);
        List<Integer> listOfAllNumbers = new ArrayList<>();
        for (int i = 1; i < 26; i++) {
            listOfAllNumbers.add(i);
        }
        assertEquals(String.format("Expected %s to be equal to %s", listOfAllNumbers.toString(), tileNums.toString()), tileNums, listOfAllNumbers);
    }

    //puzzleSolved
    @Test
    public void puzzleSolvedTrueTest() {
        SlidingTileComplexityActivity.complexity = 3;
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
        Board solvedBoard = new Board();
        solvedBoard.setTiles(tiles);

        slidingTilesBoardManager = new SlidingTilesBoardManager(); //TODO: Can I initiate the testing object here?
        slidingTilesBoardManager.setBoard(solvedBoard);
        assertTrue(slidingTilesBoardManager.puzzleSolved());
    }

    @Test
    public void puzzleSolvedFalseTest() {
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
        Board solvedBoard = new Board();
        solvedBoard.setTiles(tiles);

        slidingTilesBoardManager = new SlidingTilesBoardManager(); //TODO: Can I initiate the testing object here?
        slidingTilesBoardManager.setBoard(solvedBoard);
        assertFalse(slidingTilesBoardManager.puzzleSolved());
    }

    // is valid tap
    // one valid tap
    // one invalid tap
    @Test
    public void isValidTapTrueTest() {
        assertTrue(slidingTilesBoardManager.isValidTap(1));
        assertTrue(slidingTilesBoardManager.isValidTap(3));
        assertTrue(slidingTilesBoardManager.isValidTap(5));
        assertTrue(slidingTilesBoardManager.isValidTap(7));
    }

    @Test
    public void isValidTapFalseTest() {
        assertFalse(slidingTilesBoardManager.isValidTap(0));
        assertFalse(slidingTilesBoardManager.isValidTap(2));
        assertFalse(slidingTilesBoardManager.isValidTap(4));
        assertFalse(slidingTilesBoardManager.isValidTap(6));
        assertFalse(slidingTilesBoardManager.isValidTap(8));
    }

    //touchmove on valid tap
    // touchmove on invalid tap

    /**
     * 1 7 3
     * 2 9 4
     * 8 5 6
     */
    @Test
    public void touchMoveTest() {
        slidingTilesBoardManager.touchMove(1);
        List<Integer> tileNums = new ArrayList<>();
        for (Tile tile: slidingTilesBoardManager.getBoard()){
            tileNums.add(tile.getId());
        }
        int[] tiles = {1, 9, 3, 2, 7, 4, 8, 5, 6};
        List<Integer> correctTiles = new ArrayList<>();
        for (int i: tiles) {
            correctTiles.add(i);
        }
        assertEquals(String.format("Expected %s to be equal to %s", correctTiles.toString(), tileNums.toString()), correctTiles, tileNums);
    }
}

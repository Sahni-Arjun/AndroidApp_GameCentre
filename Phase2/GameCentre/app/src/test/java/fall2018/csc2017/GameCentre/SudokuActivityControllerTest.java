package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class SudokuActivityControllerTest {
    private SudokuActivityController controller;

    private SudokuBoardManager boardManager;

    private Account user;

    /**
     * Make a set of tiles:
     * 2 9 6 3 1 8 5 7 4
     * 5 8 0 9 7 2 6 1 3
     * 7 1 3 6 4 5 2 8 9
     * 6 2 5 8 9 7 3 4 1
     * 9 3 1 4 2 6 8 0 7
     * 4 7 8 5 3 1 9 2 6
     * 1 6 7 2 5 3 4 9 8
     * 8 0 9 7 6 4 1 3 2
     * 3 4 2 1 8 9 7 6 5
     * @return a set of tiles which comprise a solved sudoku board
     */
    private Tile[][] makeTiles() {
        SlidingTileComplexityActivity.complexity = 3;
        SudokuDifficultyActivity.difficulty = 3;
        Board.numRows = 9;
        Board.numCols = 9;
        Tile[][] tiles = new Tile[9][9];

        tiles[0][0] = new Tile(1);
        tiles[0][1] = new Tile(8);
        tiles[0][2] = new Tile(5);
        tiles[0][3] = new Tile(2);
        tiles[0][4] = new Tile(0);
        tiles[0][5] = new Tile(7);
        tiles[0][6] = new Tile(4);
        tiles[0][7] = new Tile(6);
        tiles[0][8] = new Tile(3);
        tiles[1][0] = new Tile(4);
        tiles[1][1] = new Tile(7);
        tiles[1][2] = new Tile(-1);
        tiles[1][3] = new Tile(8);
        tiles[1][4] = new Tile(6);
        tiles[1][5] = new Tile(1);
        tiles[1][6] = new Tile(5);
        tiles[1][7] = new Tile(0);
        tiles[1][8] = new Tile(2);
        tiles[2][0] = new Tile(6);
        tiles[2][1] = new Tile(0);
        tiles[2][2] = new Tile(2);
        tiles[2][3] = new Tile(5);
        tiles[2][4] = new Tile(3);
        tiles[2][5] = new Tile(4);
        tiles[2][6] = new Tile(1);
        tiles[2][7] = new Tile(7);
        tiles[2][8] = new Tile(8);
        tiles[3][0] = new Tile(5);
        tiles[3][1] = new Tile(1);
        tiles[3][2] = new Tile(4);
        tiles[3][3] = new Tile(7);
        tiles[3][4] = new Tile(8);
        tiles[3][5] = new Tile(6);
        tiles[3][6] = new Tile(2);
        tiles[3][7] = new Tile(3);
        tiles[3][8] = new Tile(0);
        tiles[4][0] = new Tile(8);
        tiles[4][1] = new Tile(2);
        tiles[4][2] = new Tile(0);
        tiles[4][3] = new Tile(3);
        tiles[4][4] = new Tile(1);
        tiles[4][5] = new Tile(5);
        tiles[4][6] = new Tile(7);
        tiles[4][7] = new Tile(-1);
        tiles[4][8] = new Tile(6);
        tiles[5][0] = new Tile(3);
        tiles[5][1] = new Tile(6);
        tiles[5][2] = new Tile(7);
        tiles[5][3] = new Tile(4);
        tiles[5][4] = new Tile(2);
        tiles[5][5] = new Tile(0);
        tiles[5][6] = new Tile(8);
        tiles[5][7] = new Tile(1);
        tiles[5][8] = new Tile(5);
        tiles[6][0] = new Tile(0);
        tiles[6][1] = new Tile(5);
        tiles[6][2] = new Tile(6);
        tiles[6][3] = new Tile(1);
        tiles[6][4] = new Tile(4);
        tiles[6][5] = new Tile(2);
        tiles[6][6] = new Tile(3);
        tiles[6][7] = new Tile(8);
        tiles[6][8] = new Tile(7);
        tiles[7][0] = new Tile(7);
        tiles[7][1] = new Tile(-1);
        tiles[7][2] = new Tile(8);
        tiles[7][3] = new Tile(6);
        tiles[7][4] = new Tile(5);
        tiles[7][5] = new Tile(3);
        tiles[7][6] = new Tile(0);
        tiles[7][7] = new Tile(2);
        tiles[7][8] = new Tile(1);
        tiles[8][0] = new Tile(2);
        tiles[8][1] = new Tile(3);
        tiles[8][2] = new Tile(1);
        tiles[8][3] = new Tile(0);
        tiles[8][4] = new Tile(7);
        tiles[8][5] = new Tile(8);
        tiles[8][6] = new Tile(6);
        tiles[8][7] = new Tile(5);
        tiles[8][8] = new Tile(4);


        return tiles;
    }

    public void setUpBoardManager() {
        Tile[][] tiles = makeTiles(); // Made tiles for the sudoku board
        boardManager = new SudokuBoardManager();
        SudokuBoard sudokuBoard = new SudokuBoard();
        sudokuBoard.setTiles(tiles); // Set the tiles on the sudoku board

        boardManager.setBoard(sudokuBoard); // Set board manager
    }

    private Tile[][] deepcopy(Tile[][] old) {
        Tile[][] newTiles = new Tile[9][9];
        for (int i = 0; i < 9; i ++) {
            newTiles[i] = old[i].clone();
        }
        return newTiles;
    }

    private void setUpAccountWithGame() {
        // Sets the sudokuBoardManager
        setUpBoardManager();
        // Set up the account
        user = new Account("Hello", "World");
        // Set current game as Sudoku
        user.setCurrentGame(3);
        // Get the Sudoku saveManager under the account
        SaveManager saveManager = user.getCurrentSaveManager(Account.sudokuName);

        // Set up SudokuBoardManager with the initial state, tiles made above
        SudokuBoardManager manager = new SudokuBoardManager();
        SudokuBoard board = new SudokuBoard();
        board.setTiles(deepcopy(boardManager.getBoard().getTiles()));
        manager.setBoard(board);

        //Set the initial state of sudoku game
        SudokuState sudokuState1 = new SudokuState(manager, 0, 3, 3, 0, true, 0);
        //Add the initial state to the saveManager
        saveManager.addState(sudokuState1, SaveManager.sudokuName);

        //Make the first move
        boardManager.getBoard().setTile(1, 2, new Tile(3));
        manager = new SudokuBoardManager();
        board = new SudokuBoard();
        board.setTiles(deepcopy(boardManager.getBoard().getTiles()));
        manager.setBoard(board);
        saveManager.updateState(SaveManager.sudokuName, manager);
        /**
         * 2 9 6 3 1 8 5 7 4
         * 5 8 4 9 7 2 6 1 3
         * 7 1 3 6 4 5 2 8 9
         * 6 2 5 8 9 7 3 4 1
         * 9 3 1 4 2 6 8 0 7
         * 4 7 8 5 3 1 9 2 6
         * 1 6 7 2 5 3 4 9 8
         * 8 0 9 7 6 4 1 3 2
         * 3 4 2 1 8 9 7 6 5
         */

        //Make the first move
        boardManager.getBoard().setTile(4, 7, new Tile(4));
        manager = new SudokuBoardManager();
        board = new SudokuBoard();
        board.setTiles(deepcopy(boardManager.getBoard().getTiles()));
        manager.setBoard(board);
        saveManager.updateState(SaveManager.sudokuName, manager);
        /**
         * 2 9 6 3 1 8 5 7 4
         * 5 8 4 9 7 2 6 1 3
         * 7 1 3 6 4 5 2 8 9
         * 6 2 5 8 9 7 3 4 1
         * 9 3 1 4 2 6 8 5 7
         * 4 7 8 5 3 1 9 2 6
         * 1 6 7 2 5 3 4 9 8
         * 8 0 9 7 6 4 1 3 2
         * 3 4 2 1 8 9 7 6 5
         */
    }

    @Before
    public void setUp(){
        StartingLoginActivity.currentUser = "Hello";
        setUpAccountWithGame();
        controller = new SudokuActivityController(
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

                    public Scoreboard loadScoreboard(Context context, String filename) {
                        return new Scoreboard();
                    }

                    public void saveScoreBoard(Context context, String filename, Scoreboard scoreboard) { }
                }, new DisplayToast(){
            public void displayToast(Context Context, String message){
                System.out.println(message);
            }
            }
        );
    }

    /**
     * Test if onCreateListener() method contains a correct field
     */
    @Test
    public void onCreateListenerTest() {
        Context context = new AppCompatActivity();

        SudokuBoardManager testBoardManager = controller.onCreateListener(context);
        Tile[][] testTile = testBoardManager.getBoard().getTiles();
        Tile[][] presetTile = boardManager.getBoard().getTiles();

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int testTileId = testTile[row][col].getBackground();
                int testPresetId = presetTile[row][col].getBackground();
                assertEquals(String.format("Expected the tile in row %d col %d to have id %d but had %d", row, col, testPresetId, testTileId), testPresetId, testTileId);
            }
        }
    }

    /**
     * Test if onPauseListener method updates the last state from autosave
     */
    @Test
    public void onPauseListenerTest() {
        Context context = new AppCompatActivity();
        controller.onCreateListener(context);
        for (long stop=System.nanoTime()+TimeUnit.SECONDS.toNanos(1);stop>System.nanoTime(););
        controller.onPauseListener(context);

        SaveManager currSaveManager = user.getCurrentSaveManager(SaveManager.sudokuName);
        SudokuState currLastState = (SudokuState) currSaveManager.getLastState(SaveManager.auto, SaveManager.sudokuName);
//        long time = currLastState.getTime();
        assert(currLastState.getTime() != 0);
    }

    /**
     * Test if onResumeListener changes the startTime of the resumed game
     */
    @Test
    public void onResumeListenerTest() {
        Context context = new AppCompatActivity();
        controller.onCreateListener(context);
        controller.onPauseListener(context);

        SaveManager currSaveManager = user.getCurrentSaveManager(SaveManager.sudokuName);
        SudokuState currLastState = (SudokuState) currSaveManager.getLastState(SaveManager.auto, SaveManager.sudokuName);
        long startTime = currLastState.getTime();
        for (long stop=System.nanoTime()+TimeUnit.SECONDS.toNanos(1);stop>System.nanoTime(););
        controller.onResumeListener();

        assert(controller.getStartTime() != startTime);
    }

    /**
     * Test if updateListener returns False with an unfinished game
     */
    @Test
    public void updateListenerFalseTest() {
        Context context = new AppCompatActivity();
        SudokuBoardManager manager = controller.onCreateListener(context);

        assertFalse("Expected False but returned True", controller.updateListener(context, manager));
    }

    /**
     * Test if updateListener returns True with an unfinished game
     */
    @Test
    public void updateListenerTrueTest() {
        Context context = new AppCompatActivity();
        SudokuBoardManager manager = controller.onCreateListener(context);

        manager.getBoard().setTile(7, 1, new Tile(4));
        assertTrue("Expected updateListener() return True but returned False", controller.updateListener(context, manager));
    }

    /**
     * Test if undoListener returns True if there is a state to undo
     */
    @Test
    public void undoListenerTrueTest() {
        Context context = new AppCompatActivity();
        SudokuBoardManager manager = controller.onCreateListener(context);

        assertTrue(controller.undoListener(context, manager));
    }


}

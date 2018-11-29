package fall2018.csc2017.GameCentre;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class SudokuBoardManagerTest {
    private FileSystem fileSystem;

    private SudokuBoardManager sudokuBoardManager;
    private Account user;

    /**
     * Make a set of tiles:
     * 2 9 6 3 1 8 5 7 4
     * 5 8 4 9 7 2 6 1 3
     * 7 1 3 6 4 5 2 8 9
     * 6 2 5 8 9 7 3 4 1
     * 9 3 1 4 2 6 8 5 7
     * 4 7 8 5 3 1 9 2 6
     * 1 6 7 2 5 3 4 9 8
     * 8 5 9 7 6 4 1 3 2
     * 3 4 2 1 8 9 7 6 5
     * @return a set of tiles which comprise a solved sudoku board
     */
    private Tile[][] makeTiles() {

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
        tiles[1][2] = new Tile(3);
        tiles[1][3] = new Tile(8);
        tiles[1][4] = new Tile(6);
        tiles[1][5] = new Tile(1);
        tiles[1][6] = new Tile(6);
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
        tiles[4][7] = new Tile(4);
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
        tiles[7][1] = new Tile(4);
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

    /**
     * Make a starting Board.
     */
    private void setUpStartingBoard() {
        Tile[][] tiles = makeTiles();

        sudokuBoardManager = new SudokuBoardManager();
        sudokuBoardManager.getBoard().setTiles(tiles);

    }

    private Tile[][] deepCopy(Tile[][] old){
        Tile[][] newTiles = new Tile[9][9];
        for(int i = 0; i < 8; i++){
            newTiles[i] = old[i].clone();
        }

        return newTiles;
    }
    private Tile[][] makeBlankTiles() {
        SudokuBoard board = new SudokuBoard();
        return board.getTiles();
    }

    /**
     * Make 3 moves.
     */
    private void setUpAccountWithGame() {
        setUpStartingBoard();
        user = new Account("Hello", "World");
        user.setCurrentGame(1);
        SaveManager saveManager = user.getCurrentSaveManager(Account.slidingName);

        SudokuBoardManager boardManager = new SudokuBoardManager();
        SudokuBoard board = new SudokuBoard();
        board.setTiles(deepCopy(boardManager.getBoard().getTiles()));
        boardManager.setBoard(board);

        SudokuState sudokuState1 = new SudokuState(boardManager, 0, 1, 0, 0, true,0);
        saveManager.addState(sudokuState1, SaveManager.sudokuName);
        /**
         * 9 1 3
         * 2 7 4
         * 8 5 6
         */

        boardManager.getBoard().swapTiles(0, 0, 0, 1);
        boardManager = new SudokuBoardManager();
        board = new SudokuBoard();
        board.setTiles(deepCopy(boardManager.getBoard().getTiles()));
        boardManager.setBoard(board);
        saveManager.updateState(SaveManager.slidingTilesName, boardManager);
        /**
         * 1 9 3
         * 2 7 4
         * 8 5 6
         */

        boardManager.getBoard().swapTiles(0, 1, 1, 1);
        boardManager = new SudokuBoardManager();
        board = new SudokuBoard();
        board.setTiles(deepCopy(boardManager.getBoard().getTiles()));
        boardManager.setBoard(board);
        saveManager.updateState(SaveManager.slidingTilesName, boardManager);
        /**
         * 1 7 3
         * 2 9 4
         * 8 5 6
         */
    }
    private int numBlankTile(Tile[][] tiles, int i, int j) {
        ArrayList<Tile> blankTile = new ArrayList<>();
        for (int i3 = i * 3; i3 < i * 3 + 3; i3++) {
            for (int j3 = j * 3; j3 < j * 3 + 3; j3++) {
                if (tiles[i3][j3].getId() == 0) {
                    blankTile.add(tiles[i3][j3]);
                }
            }
        }
        return blankTile.size();
    }


    @Before
    public void setUp(){
        SudokuDifficultyActivity.difficulty = 1;
        StartingLoginActivity.currentUser = "Hello";
        setUpAccountWithGame();
        SudokuActivity sudokuActivity = new SudokuActivity();
        sudokuActivity.setSudokuBoardManager(new SudokuBoardManager());
        this.fileSystem = new FileSystem(){
            public AccountManager loadAccount(Context context){
                List<Account> emptyAccounts = new ArrayList<>();
                AccountManager accountManager = new AccountManager(emptyAccounts);
                accountManager.addUser(user);
                return accountManager;
            }

            public void saveAccount(Context context, AccountManager accountManager){
                user = accountManager.findUser("Hello");
            }
        };
            }
    //puzzleSolved
    @Test
    public void puzzleSolvedFalseTest() {
        Tile[][] tiles;
        tiles = makeTiles();
        // by changing the value of any tile the sudoku rules are violated
        tiles[0][0] = new Tile(6);
        SudokuBoard solvedBoard = new SudokuBoard();
        solvedBoard.setTiles(tiles);
        sudokuBoardManager = new SudokuBoardManager(); //TODO: Can I initiate the testing object here?
        sudokuBoardManager.setBoard(solvedBoard);
        assertFalse(sudokuBoardManager.puzzleSolved());
    }

    @Test
    public void puzzleSolvedTrueTest() {
        Tile[][] tiles;
        tiles = makeTiles();
        SudokuBoard solvedBoard = new SudokuBoard();
        solvedBoard.setTiles(tiles);
        sudokuBoardManager = new SudokuBoardManager(); //TODO: Can I initiate the testing object here?
        sudokuBoardManager.setBoard(solvedBoard);
        assertTrue(sudokuBoardManager.puzzleSolved());
    }


    /**
     * test that createSolvedBoard creates a solved sudokuboard that follows all the sudoku rules.
     */
    @Test
    public void sudokuCreateSolvedBoardTest() {
        SudokuBoardManager boardManager = new SudokuBoardManager();
        boardManager.createSolvedBoard();
        assertTrue(sudokuBoardManager.puzzleSolved());


    }
    @Test
    public void sudokuBoardManagerConstructorTest() {
        SudokuDifficultyActivity.difficulty = 1;
        SudokuBoardManager boardManager = new SudokuBoardManager();
        SudokuBoard board = boardManager.getBoard();
        Tile[][] tiles = boardManager.getTiles();
        // Test if the number of tiles is 81
        assertEquals(81, board.numTiles());
        boolean correct = true;
        // test if there is 1 blank tile per 3x3 subsquare
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (numBlankTile(tiles, i, j) != 1) {
                    correct = false;
                }
            }
            assertTrue(correct);
        }
    }
    @Test
    public void getRowBuddiesTest() {
        SudokuBoardManager manager = new SudokuBoardManager();
        Tile[][] tiles = makeTiles();
        manager.setTiles(tiles);
        ArrayList<Integer> values = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            values.add(tiles[0][i].getId());
        }
        boolean correct = true;
        ArrayList<Integer> buddies = manager.getRowBuddies(0, 8);
        for (Integer x : values) {
            if (!buddies.contains(x)) {
                correct = false;
            }
            for (Integer y : buddies) {
                if (!values.contains(y)) {
                    correct = false;
                }
            }
        }
        assertTrue(correct);
    }

    @Test
    public void getColBuddiesTest(){
        SudokuBoardManager manager = new SudokuBoardManager();
        Tile[][] tiles = makeTiles();
        manager.setTiles(tiles);
        ArrayList<Integer> values = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            values.add(tiles[1][0].getId());
        }
        boolean correct = true;
        ArrayList<Integer> buddies = manager.getColBuddies(8, 0);
        for (Integer x : values) {
            if (!buddies.contains(x)) {
                correct = false;
            }
            for (Integer y : buddies) {
                if (!values.contains(y)) {
                    correct = false;
                }
            }
        }
        assertTrue(correct);
    }

    @Test
    public void getSquareBuddiesTest() {
        SudokuBoardManager manager = new SudokuBoardManager();
        Tile[][] tiles = makeTiles();
        manager.setTiles(tiles);
        ArrayList<Integer> values = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for(int j = 0;j<2;j++){
                values.add(tiles[i][j].getId());
            }

        }
        boolean correct = true;
        ArrayList<Integer> buddies = manager.getSquareBuddies(2, 2);
        for (Integer x : values) {
            if (!buddies.contains(x)) {
                correct = false;
            }
        }
            for (Integer y : buddies) {
                if (!values.contains(y)) {
                    correct = false;
                }
            }
            assertTrue(correct);
        }


    @Test
    public void getAllBuddiesTest() {

    }




//    //TODO: complexity set up in setup
//    @Test
//    public void slidingTiles4x4BoardManagerConstructorTest() {
//        SlidingTileComplexityActivity.complexity = 4;
//        SlidingTilesBoardManager boardManager = new SlidingTilesBoardManager();
//        Board board = boardManager.getBoard();
//        // Test if the number of tiles is 9 when the complexity is 3
//        assertEquals(String.format("Expected %d to be equal to %d", 16, board.numTiles()), 16, board.numTiles());
//        List<Integer> tileNums = new ArrayList<>();
//        for (Tile tile: board) {
//            tileNums.add(tile.getId());
//        }
//        Collections.sort(tileNums);
//        List<Integer> listOfAllNumbers = new ArrayList<>();
//        for (int i = 1; i < 17; i++) {
//            listOfAllNumbers.add(i);
//        }
//        assertEquals(String.format("Expected %s to be equal to %s", listOfAllNumbers.toString(), tileNums.toString()), tileNums, listOfAllNumbers);
//    }
//
//    @Test
//    public void slidingTiles5x5BoardManagerConstructorTest() {
//        SlidingTileComplexityActivity.complexity = 5;
//        SlidingTilesBoardManager boardManager = new SlidingTilesBoardManager();
//        Board board = boardManager.getBoard();
//        // Test if the number of tiles is 16 when
//        assertEquals(String.format("Expected %d to be equal to %d", 25, board.numTiles()), 25, board.numTiles());
//        List<Integer> tileNums = new ArrayList<>();
//        for (Tile tile: board) {
//            tileNums.add(tile.getId());
//        }

//        Collections.sort(tileNums);
//        List<Integer> listOfAllNumbers = new ArrayList<>();
//        for (int i = 1; i < 26; i++) {
//            listOfAllNumbers.add(i);
//        }
//        assertEquals(String.format("Expected %s to be equal to %s", listOfAllNumbers.toString(), tileNums.toString()), tileNums, listOfAllNumbers);
//    }


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

}

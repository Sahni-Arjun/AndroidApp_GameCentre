package fall2018.csc2017.GameCentre;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IsSolvableOddTest {

    /**
     * Create an odd sized board which is solvable.
     */
    private Board solvableOddBoard() {
        Tile[][] tiles = new Tile[3][3];
        tiles[0][0] = new Tile(0);
        tiles[0][1] = new Tile(1);
        tiles[0][2] = new Tile(2);
        tiles[1][0] = new Tile(3);
        tiles[1][1] = new Tile(4);
        tiles[1][2] = new Tile(5);
        tiles[2][0] = new Tile(6);
        tiles[2][1] = new Tile(8);
        tiles[2][2] = new Tile(7);
        Board retBoard = new Board();
        retBoard.setTiles(tiles);
        return retBoard;
    }

    /**
     * Create an unsolvable odd board.
     */
    private Board unsolvableOddBoard() {
        Tile[][] tiles = new Tile[3][3];
        tiles[0][0] = new Tile(8);
        tiles[0][1] = new Tile(1);
        tiles[0][2] = new Tile(2);
        tiles[1][0] = new Tile(3);
        tiles[1][1] = new Tile(4);
        tiles[1][2] = new Tile(5);
        tiles[2][0] = new Tile(6);
        tiles[2][1] = new Tile(7);
        tiles[2][2] = new Tile(0);
        Board retBoard = new Board();
        retBoard.setTiles(tiles);
        return retBoard;
    }

    /**
     * Create an already solved odd board.
     */
    private Board solvedOddBoard(){
        Tile[][] tiles = new Tile[3][3];
        tiles[0][0] = new Tile(0);
        tiles[0][1] = new Tile(1);
        tiles[0][2] = new Tile(2);
        tiles[1][0] = new Tile(3);
        tiles[1][1] = new Tile(4);
        tiles[1][2] = new Tile(5);
        tiles[2][0] = new Tile(6);
        tiles[2][1] = new Tile(7);
        tiles[2][2] = new Tile(8);
        Board retBoard = new Board();
        retBoard.setTiles(tiles);
        return retBoard;
    }

    /**
     * Set the complexity for the test.
     */
    @Before
    public void setComplexity(){
        SlidingTileComplexityActivity.complexity = 3;
    }

    /**
     * Test if the odd sized board is solvable.
     */
    @Test
    public void testIsSolvableOddSizedBoard(){
        Board board;
        SlidingTilesBoardManager boardManager;
        boardManager = new SlidingTilesBoardManager();

        board = solvableOddBoard();
        boardManager.setBoard(board);
        assertTrue(boardManager.isSolvable());

        board = unsolvableOddBoard();
        boardManager.setBoard(board);
        assertFalse(boardManager.isSolvable());

        board = solvedOddBoard();
        boardManager.setBoard(board);
        assertTrue(boardManager.isSolvable());
    }
}

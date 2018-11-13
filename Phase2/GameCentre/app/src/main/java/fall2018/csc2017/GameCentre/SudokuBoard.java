package fall2018.csc2017.GameCentre;

import java.util.Iterator;
import java.util.List;


public class SudokuBoard extends Board{
    /**
     * Number of rows
     */
    static int numRows = 9;

    /**
     * Number of cols
     */
    static int numCols = 9;

    /**
     * The tiles on the SudokuBoard in row-major order
     */
    private Tile[][] tiles = new Tile[numRows][numCols]; //TODO: Change this to SudokuTile once implemented

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == numRows * numCols
     *
     * @param t the tiles for the board
     */
    public SudokuBoard(List<Tile> t) {
        super(t);
        Iterator<Tile> iter = t.iterator();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.tiles[i][j] = iter.next();
            }
        }
    }
}

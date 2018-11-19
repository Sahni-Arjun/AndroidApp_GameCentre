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

    void setTile(int row, int col, Tile tile){
        tiles[row][col] = tile;
        setChanged();
        notifyObservers();
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == numRows * numCols
     *
     * @param t the tiles for the board
     */
    SudokuBoard(List<Tile> t) {
        super(t);
        Iterator<Tile> iter = t.iterator();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.tiles[i][j] = iter.next();
            }
        }
    }

    /**
     * Get the tile in row and col
     */
    public Tile getTile(int row, int col) {
        return this.tiles[row][col];  // TODO: better way to return SudokuTile
    }
}

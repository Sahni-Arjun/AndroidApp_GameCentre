package fall2018.csc2017.GameCentre;

import android.support.annotation.NonNull;

import java.util.Observable;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * The sliding tiles board.
 */
public class Board extends Observable implements Serializable, Iterable<Tile> {
    /**
     * The number of rows.
     */
    static int numRows = SlidingTileComplexityActivity.complexity;

    /**
     * The number of rows.
     */
    static int numCols = SlidingTileComplexityActivity.complexity;

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles = new Tile[numRows][numCols];

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == numRows * numCols
     *
     * @param tiles the tiles for the board
     */
    Board(List<Tile> tiles) {
        Iterator<Tile> iter = tiles.iterator();

        for (int row = 0; row != Board.numRows; row++) {
            for (int col = 0; col != Board.numCols; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    int numTiles() {
        return numCols * numRows;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    void swapTiles(int row1, int col1, int row2, int col2) {
        // Creating a temporary variable to save the value of the first Tile.
        Tile tempTile = tiles[row1][col1];
        tiles[row1][col1] = tiles[row2][col2];
        tiles[row2][col2] = tempTile;

        setChanged();
        notifyObservers();
    }

    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    /**
     * Return an iterator for Tiles in this Board.
     *
     * @return an iterator for Tiles
     */
    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new BoardIterator();
    }

    /**
     * The iterator for Tiles.
     */
    private class BoardIterator implements Iterator<Tile> {
        /**
         * The next row index of a given Tile.
         */
        int nextRowIndex = 0;

        /**
         * The next column index of a given Tile.
         */
        int nextColIndex = 0;

        /**
         * Return whether there is another valid tile to return.
         *
         * @return whether there is another valid tile to return
         */
        @Override
        public boolean hasNext() {
            // The Tile after the final one has coordinates (numRows, 0). Note this is an invalid
            // tile.
            return ((nextRowIndex != numRows) || (nextColIndex != 0));
        }

        /**
         * Return the next Tile.
         *
         * @return the next Tile
         */
        @Override
        public Tile next() {
            Tile current_tile = tiles[nextRowIndex][nextColIndex];
            // Getting the next indices for the next Tile.
            if (nextColIndex != numCols - 1) {
                nextColIndex += 1;
            } else { //If at the end of the column, restart nextColIndex back at 0.
                nextColIndex = 0;
                nextRowIndex += 1;
            }
            return current_tile;
        }
    }
}

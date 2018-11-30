/*
Model class will not be tested.
 */
package fall2018.csc2017.GameCentre;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Observable;

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
     * Create a new board.
     */
    Board(){}

    /**
     * Set the value of a single tile.
     * @param row row position
     * @param col column position
     * @param tile new tile to replace
     */
    public void setTile(int row, int col, Tile tile){
        this.tiles[row][col] = tile;
    }

    /**
     * Set the entire arrangement of tiles.
     * @param tiles new tiles to replace
     */
    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    /**
     * Return the entire arrangement of tiles
     * @return the arrangement of tiles.
     */
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
     * Notify observers of a change.
     */
    void change(){
        setChanged();
        notifyObservers();
    }

    /**
     * Return a string representation of the board class.
     * @return string representation of the board class.
     */
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

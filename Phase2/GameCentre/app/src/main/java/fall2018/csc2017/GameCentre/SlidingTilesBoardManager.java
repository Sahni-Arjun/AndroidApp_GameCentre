/*
Controller
 */
package fall2018.csc2017.GameCentre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class SlidingTilesBoardManager extends BoardManager implements Serializable {

    /**
     * The board being managed.
     */
    private Board board;

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    SlidingTilesBoardManager(Board board) {
        this.board = board;
    }

    /**
     * Return the current board.
     *
     * @return the current board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Set a new board.
     *
     * @param newBoard a new board
     */
    public void setBoard(Board newBoard) {
        this.board = newBoard;
    }

    /**
     * Manage a new shuffled board.
     */
    SlidingTilesBoardManager() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = Board.numRows * Board.numCols;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new SlidingTilesTile(tileNum));
        }
        Collections.shuffle(tiles);
        this.board = new Board();
        populateBoard(tiles);
        while (!this.isSolvable()){
            Collections.shuffle(tiles);
            populateBoard(tiles);}}
//            this.board = new Board(tiles);


    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    boolean puzzleSolved() {
        Iterator<Tile> boardIterator = board.iterator();
        Tile prev_Tile = boardIterator.next();
        Tile current_Tile;
        // Iterate through all Tiles checking if the previous id is larger than the next.
        while (boardIterator.hasNext()) {
            current_Tile = boardIterator.next();
            if (prev_Tile.getId() > current_Tile.getId()) {
                return false;
            }
            prev_Tile = current_Tile; }
        // If the end of the while loop is reached then all Tiles are in correct order.
        return true; }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    boolean isValidTap(int position) {

        int row = position / Board.numCols;
        int col = position % Board.numCols;
        int blankId = board.numTiles();
        // Are any of the 4 the blank tile?
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == Board.numRows - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == Board.numCols - 1 ? null : board.getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId); }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    void touchMove(int position) {

        int row = position / Board.numRows;
        int col = position % Board.numCols;
        int blankId = board.numTiles();
        int rowId = 0, colId = 0; // First tile in Board.

        // Only process touch if current tile is beside a blank one.
        if (isValidTap(position)) {
            for (int c = 0; c < Board.numCols; c++) {
                for (int r = 0; r < Board.numRows; r++) {
                    if (board.getTile(r, c).getId() == blankId) {
                        rowId = r;
                        colId = c; }
                }
            }
            swapTiles(row, col, rowId, colId); } }

    /**
     * The board is said to be solvable if:
     * 1. If the numRows is odd, then the #inversions is even.
     * 2. If the numRows is even, and the blank is on an even row, then #inversions is odd.
     * 3. If the numRows is even, and the blank is on an odd row, then #inversions is even.
     * The blank row must be counted from the bottom (second-last, fourth-last etc)
     * <p>
     * Taken from : https://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html
     *
     * @return whether or not the board is solvable
     */
     boolean isSolvable() {
        int blankTileId = Board.numRows * Board.numCols;
        int numInversions = 0;
        int blankTileRow = 0;

        boolean isSolvable;

        // TODO Maybe find a neater way to iterate through the remaining tiles?
        for (int row = 0; row < Board.numRows; row++) {
            for (int col = 0; col < Board.numCols; col++) {
                Tile currTile = board.getTile(row, col);
                // Do not count the inversions on blank tiles
                if (currTile.getId() != blankTileId) {
                    // Iterate through each subsequent tiles
                    for (int subRow = row; subRow < Board.numRows; subRow++) {
                        // TODO Pretty messy, probably change?
                        // If the current row isn't the starting row, the col must start at 0
                        int startCol = 0;
                        if(subRow == row){
                            startCol = col;
                        }
                        for (int subCol = startCol; subCol < Board.numCols; subCol++) {
                            Tile subTile = board.getTile(subRow, subCol);
                            // Inversions are possible when the nxt tile is less than the curr tile
                            if (currTile.getId() > subTile.getId()) {
                                numInversions++;
                            }
                        }
                    }
                }
                else {
                    // Must get the row counting from the bottom
                    blankTileRow = Board.numRows - row;
                }
            }
        }

        // Formatted version of the condition from above
        isSolvable = ((Board.numRows % 2 != 0 && numInversions % 2 == 0)) ||
                (Board.numRows % 2 == 0) && ((blankTileRow % 2 != 0) == (numInversions % 2 == 0));

        return isSolvable; }

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
        Tile[][] tiles = board.getTiles();
        Tile tempTile = tiles[row1][col1];
        tiles[row1][col1] = tiles[row2][col2];
        tiles[row2][col2] = tempTile;

        board.change(); }

    /**
     * Populates the board with the given tiles.
     *
     * @param tiles the tiles that must be added into the board
     */
    private void populateBoard(List<Tile> tiles){
        Collections.shuffle(tiles);
        Iterator<Tile> iter = tiles.iterator();

        for (int row = 0; row != Board.numRows; row++) {
            for (int col = 0; col != Board.numCols; col++) {
                board.setTile(row, col, iter.next());}}}}
//                tiles[row][col] = iter.next();}


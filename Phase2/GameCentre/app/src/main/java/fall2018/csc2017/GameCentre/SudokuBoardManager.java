package fall2018.csc2017.GameCentre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class SudokuBoardManager extends BoardManager implements Serializable {

    /**
     * The board being managed.
     */
    private SudokuBoard board;

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    SudokuBoardManager(SudokuBoard board) {
        this.board = board;
    }

    /**
     * Return the current board.
     *
     * @return the current board.
     */
    public SudokuBoard getBoard() {
        return board;
    }

    /**
     * Set a new board.
     *
     * @param newBoard a new board
     */
    public void setBoard(SudokuBoard newBoard) {
        this.board = newBoard;
    }

    /**
     * The correct order of the sudoku checker
     */
    static final int[] CHECKER = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    /**
     * Manage a new shuffled board.
     */
    SudokuBoardManager() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = Board.numRows * Board.numCols;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(25));
        }
//        Collections.shuffle(tiles);
        this.board = new SudokuBoard(tiles);
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    boolean puzzleSolved() {
        return this.checkRows() && this.checkCols() && this.checkBoxes();
//        Iterator<Tile> boardIterator = board.iterator();
//        Tile prev_Tile = boardIterator.next();
//        Tile current_Tile;
//        // Iterate through all Tiles checking if the previous id is larger than the next.
//        while (boardIterator.hasNext()) {
//            current_Tile = boardIterator.next();
//            if (prev_Tile.getId() > current_Tile.getId()) {
//                return false;
//            }
//            prev_Tile = current_Tile;
//        }
//        // If the end of the while loop is reached then all Tiles are in correct order.
//        return true;
//        return false;
    }

    private boolean checkRows() {
        for (int row = 0; row < 9; row ++) {
            if (!this.checkSingleRow(row)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkSingleRow(int row) {
        int[] rowTiles = new int[9];
        for (int col = 0; col < 9; col ++) {
            rowTiles[col] = this.board.getTile(row, col).getValue();
        }
        Arrays.sort(rowTiles);
        return Arrays.equals(rowTiles, CHECKER);
    }

    private boolean checkCols() {
        for (int col = 0; col < 9; col ++) {
            if (!this.checkSingleCol(col)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkSingleCol(int col) {
        int[] colTiles = new int[9];
        for (int row = 0; row < 9; row++) {
            colTiles[col] = this.board.getTile(row, col).getValue();
        }
        Arrays.sort(colTiles);
        return Arrays.equals(colTiles, CHECKER);
    }

    private boolean checkBoxes() {
        for (int ternaryCol = 0; ternaryCol < 3; ternaryCol ++) {
            for (int ternaryRow = 0; ternaryRow < 3; ternaryRow ++) {
                if (!checkSubBox(ternaryRow, ternaryCol)) {
                    return false;
                }
            }
        }
        return true;
    }









    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    boolean isValidTap(int position) { //TODO implement what it means for a tap to be valid.

//        int row = position / Board.numCols;
//        int col = position % Board.numCols;
//        int blankId = board.numTiles();
//        // Are any of the 4 the blank tile?
//        Tile above = row == 0 ? null : board.getTile(row - 1, col);
//        Tile below = row == Board.numRows - 1 ? null : board.getTile(row + 1, col);
//        Tile left = col == 0 ? null : board.getTile(row, col - 1);
//        Tile right = col == Board.numCols - 1 ? null : board.getTile(row, col + 1);
//        return (below != null && below.getId() == blankId)
//                || (above != null && above.getId() == blankId)
//                || (left != null && left.getId() == blankId)
//                || (right != null && right.getId() == blankId);
        return false;
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    void touchMove(int position) {

//        int row = position / Board.numRows;
//        int col = position % Board.numCols;
//        int blankId = board.numTiles();
//        int rowId = 0, colId = 0; // First tile in Board.
//
//        // Only process touch if current tile is beside a blank one.
//        if (isValidTap(position)) {
//            // Iterate through the board to find the tile at current position
//            for (int c = 0; c < Board.numCols; c++) {
//                for (int r = 0; r < Board.numRows; r++) {
//                    if (board.getTile(r, c).getId() == blankId) {
//                        rowId = r;
//                        colId = c;
//                    }
//                }
//            }
//            board.swapTiles(row, col, rowId, colId);
//        }
    }
}

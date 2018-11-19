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
    static final int[] CHECKER = {1, 2, 3, 4, 5, 6, 7, 8, 9};

    /**
     * Manage a new shuffled board.
     */
    SudokuBoardManager() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = Board.numRows * Board.numCols;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(-1));
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
    }

    /**
     * Checks if all rows correspond to a correct sudoku order
     * @return true iff all rows are correctly written
     */
    private boolean checkRows() {
        for (int row = 0; row < 9; row ++) {
            if (!this.checkSingleRow(row)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if one row correspond to a correct sudoku order
     * @return true iff the row is correctly written
     */
    private boolean checkSingleRow(int row) {
        List<Integer> rowTiles = new ArrayList<>();
        for (int col = 0; col < 9; col++) {
            int val = this.board.getTile(row, col).getBackground() + 1;
            if (val != 0) {
                rowTiles.add(val);
            }
        }
        return checkWithChecker(rowTiles);
    }

    /**
     * Checks if all cols correspond to a correct sudoku order
     * @return true iff all cols are correctly written
     */
    private boolean checkCols() {
        for (int col = 0; col < 9; col ++) {
            if (!this.checkSingleCol(col)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if one col correspond to a correct sudoku order
     * @return true iff the col is correctly written
     */
    private boolean checkSingleCol(int col) {
        List<Integer> colTiles = new ArrayList<>();
        for (int row = 0; row < 9; row++) {
            int val = this.board.getTile(row, col).getBackground() + 1;
            if (val != 0) {
                colTiles.add(val);
            }
        }
        return checkWithChecker(colTiles);
    }

    /**
     * Checks if all boxes correspond to a correct sudoku order
     * @return true iff all boxes are correctly written
     */
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
     * Checks if one box correspond to a correct sudoku order
     * @return true iff the box is correctly written
     */
    private boolean checkSubBox(int ternaryRow, int ternaryCol) {
        List<Integer> tiles = new ArrayList<>();
        for (int r = 0; r < 3; r ++) {
            for (int c = 0; c < 3; c ++) {
                tiles.add(this.board.getTile(3*ternaryRow + r, 3*ternaryCol + c).getBackground() + 1);
            }
        }

        return checkWithChecker(tiles);
    }

    private boolean checkWithChecker(List<Integer> numTiles) {
        if (numTiles.size() != CHECKER.length) {
            return false;
        }

        for (int num: CHECKER) {
            if (!numTiles.contains(num)) {
                return false;
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
    boolean isValidTap(int position) { //TODO check if the index works for all cases
        int row = position / SudokuBoard.numRows;
        int col = position % SudokuBoard.numCols;

        int ternaryRow = row / 3;
        int ternaryCol = col / 3;

        List<Integer> rowTiles = new ArrayList<>();
        List<Integer> colTiles = new ArrayList<>();

        // get all columns
        for (int i = 0; i < 9; i++) {
            //SudokuActivity.currentNumber
            rowTiles.add(this.board.getTile(row, i).getBackground());
        }

        // get all rows
        for (int i = 0; i < 9; i++) {
            colTiles.add(this.board.getTile(i, col).getBackground());
        }

        // check if the value we are trying to position at position is already
        // existing in column or row
        int value = SudokuActivity.currentNumber;
        if (rowTiles.contains(value) || colTiles.contains(value)) {
            return false;
        }

        List<Integer> boxTiles = new ArrayList<>();

        for (int i = ternaryCol; i < ternaryCol+3; i++) {
            for (int j = ternaryRow; j < ternaryRow+3;j++) {
                boxTiles.add(this.board.getTile(j, i).getBackground());
            }
        }

        if (boxTiles.contains(value)) {
            return false;
        }
        return true;



//        SudokuBoardManager tempBoardManager = new SudokuBoardManager();
//        SudokuBoard tempBoard = new SudokuBoard();
//        tempBoard.setTiles(this.board.getTiles().clone());
//        tempBoardManager.setBoard(tempBoard);
//        Tile tile = new Tile(SudokuActivity.currentNumber-1);
//        tempBoardManager.getBoard().setTile(row, col, tile);

//        //TODO edit or create new methods for checking isValidTap. do not use checkwithChecker
//        return tempBoardManager.checkSingleRow(row) && tempBoardManager.checkSingleCol(col) & tempBoardManager.checkSubBox(ternaryRow, ternaryCol);
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    void touchMove(int position) {
        int row = position / Board.numRows;
        int col = position % Board.numCols;

        if (isValidTap(position)){
            Tile newTile = new Tile(SudokuActivity.currentNumber-1);
            board.setTile(row, col, newTile);
        }
    }
}

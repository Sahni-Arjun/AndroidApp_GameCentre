package fall2018.csc2017.GameCentre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class SudokuBoardManager extends BoardManager implements Serializable {

    /**
     * The board being managed.
     */
    private SudokuBoard board = new SudokuBoard();
    private Tile[][] tiles = board.getTiles();

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
    private static final int[] CHECKER = {1, 2, 3, 4, 5, 6, 7, 8, 9};

    /**
     * Manage a new shuffled board.
     */
    SudokuBoardManager(){
        int i = 0;
        int j = 0;
        //boolean finished = false;

        while (!getAvailable(i,j).isEmpty()){
                Random rand = new Random();
                ArrayList<Integer> available = getAvailable(i,j);
            //if(!available.isEmpty()){
                int randomNumber = available.get(rand.nextInt(available.size()));
                tiles[i][j] = new Tile(randomNumber - 1);

                if(j<8){
                    j++;
                }
                else{
                    i++;
                    j=0;
                }
            //}
//            else{
//                if(j>1){
//                    j = j - 2;
//                }
//                else{
//                    i = i-1;
//                    j = 8-(1-j);
//                }
            if(i>8){break;}
            }
        this.board.setTiles(tiles);

        }




    private ArrayList<Integer> getRowBuddies(int i, int j ) {
        ArrayList<Integer> rowBuddies = new ArrayList<>();
        for(int x = 0; x < 9;x++){
            if(x!=j){
                rowBuddies.add(tiles[i][x].getId());

            }
        }
        return rowBuddies;
    }
    private ArrayList<Integer> getColBuddies(int i, int j ) {
        ArrayList<Integer> colBuddies = new ArrayList<>();
        for (int x = 0; x < 9; x++) {
            if (x != i) {
                colBuddies.add(tiles[x][j].getId());

            }
        }
        return colBuddies;
    }

    private ArrayList<Integer> getSquareBuddies(int i, int j ) {
        int i3 = (i/3)*3;
        int j3 = (j/3)*3;
        ArrayList<Integer> squareBuddies = new ArrayList<>();

        for(int x = i3;x < i3+3;x++){
            for(int y = j3; y < j3+3;y++){
                if((x==i)&&(y==j)){}
                else{
                    squareBuddies.add(tiles[x][y].getId());
                }

                }
            }
        return squareBuddies;
    }

    private ArrayList<Integer> getAllBuddies(int i, int j ) {
        ArrayList<Integer> allBuddies = new ArrayList<>();
        allBuddies.addAll(getRowBuddies(i,j));
        allBuddies.addAll(getColBuddies(i,j));
        allBuddies.addAll(getSquareBuddies(i,j));
        return allBuddies;
    }

    private ArrayList<Integer> getAvailable(int i, int j){
        ArrayList<Integer> digits = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        ArrayList<Integer> available = new ArrayList<>();
        ArrayList<Integer> buddies = getAllBuddies(i,j);

        for (Integer x: digits){
            if(!buddies.contains(x)){
                available.add(x);
            }
        }
        return available;
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
    boolean isValidTap(int position) {
        int row = position / SudokuBoard.numRows;
        int col = position % SudokuBoard.numCols;

        int ternaryRow = row / 3;
        int ternaryCol = col / 3;

        List<Integer> rowTiles = new ArrayList<>();
        List<Integer> colTiles = new ArrayList<>();

        // get all columns
        for (int i = 0; i < 9; i++) {
            //SudokuActivity.currentNumber
            int item = this.board.getTile(row, i).getId();
            if (item!=0) {
                rowTiles.add(item);
            }
        }

        // get all rows
        for (int i = 0; i < 9; i++) {
            int item = this.board.getTile(i, col).getId();
            if (item!=0) {
                colTiles.add(item);
            }
        }

        // check if the value we are trying to position at position is already
        // existing in column or row
        int value = SudokuActivity.currentNumber;

        List<Integer> boxTiles = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            int boxRow = ternaryRow * 3 + i;
            for (int j = 0; j < 3;j++) {
                int boxCol = ternaryCol * 3 + j;
                int item = this.board.getTile(boxRow, boxCol).getId();
                if (item!=0) {
                    boxTiles.add(item);
                }
            }
        }

        if (boxTiles.contains(value) || rowTiles.contains(value) || colTiles.contains(value)) {
            return false;
        }
        return true;
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

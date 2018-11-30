/*
Controller
 */
package fall2018.csc2017.GameCentre;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    private boolean found = false;




    /**
     * Return the current board.
     *
     * @return the current board.
     */
    public SudokuBoard getBoard() {
        return board;
    }

    /**
     * set the tiles of the boardmanager
     */
    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
        this.board.setTiles(tiles);
    }

    /**
     * return the current tiles
     */
    public Tile[][] getTiles() {
        return this.tiles;
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
    SudokuBoardManager() {
        createSolvedBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                ArrayList<Integer[]> random = new ArrayList<>();
                ArrayList<Integer> iList = new ArrayList<>(Arrays.asList(i * 3, i * 3 + 1, i * 3 + 2));
                ArrayList<Integer> jList = new ArrayList<>(Arrays.asList(j * 3, j * 3 + 1, j * 3 + 2));
                while (random.size() < SudokuDifficultyActivity.difficulty) {
                    Collections.shuffle(iList);
                    Collections.shuffle(jList);
                    Integer ri = iList.get(0);
                    Integer rj = jList.get(0);
                    Integer[] rCo = {ri, rj};
                    if (!random.contains(rCo)) {
                        random.add(rCo);
                    }
                }
                for (int i3 = i * 3; i3 < i * 3 + 3; i3++) {
                    for (int j3 = j * 3; j3 < j * 3 + 3; j3++) {
                        Integer j1 = j3;
                        Integer i1 = i3;
                        for (Integer[] x : random) {
                            if ((x[0].equals(i1) && (x[1].equals(j1)))) {
                                tiles[i3][j3] = new Tile(-1);
                            }

                        }
                    }

                }


            }
        }
        this.board.setTiles(tiles);
    }


//        ArrayList<Integer> values = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
//        Collections.shuffle(values);
//
//
//        int rand = values.get(0);
//        SudokuTreeNode root = new SudokuTreeNode(0,0,rand,0);
//


//        int x = 0;
//        int y = 0;
//        Tile[][] tempTiles = this.tiles;
//        while (!check(tempTiles)) {
//            tempTiles = this.tiles;
//            for (int i = 0; i < 9; i++) {
//                ArrayList<Integer> values = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
//                for(int y1 = 0;y1 <y;y1++){
//                    Integer reMove1 = tempTiles[i][y].getBackground();
//                    values.remove(reMove1);
//                }
//                Collections.shuffle(values);
//                for (int j = 0; j < 9-y; j++) {
//                    int v = values.get(0);
//                    tempTiles[i][j+y] = new Tile(v);
//                    values.remove(0);
//
//                }
//                x ++;
//                ArrayList<Integer> values1 = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
//                for(int x1 = 0;x1 <x;x1++){
//                    Integer reMove = tempTiles[i][x].getBackground();
//                    values1.remove(reMove);
//                }
//                Collections.shuffle(values1);
//                for (int i1 = 0;i1 < 9-x;i1++){
//                    tempTiles[i1][i] = new Tile(values1.get(0));
//                    values1.remove(0);
//                    }
//                y++;


//        this.tiles = tempTiles;
//        this.board.setTiles(tempTiles);
//    }
//
//    private boolean check(Tile[][] tiles){
//        for (int j = 0; j < 9; j++) {
//            int sum1 = 0;
//            for (int i = 0; i < 9; i++) {
//                sum1 += tiles[i][j].getId();
//            }
//            if (sum1 != 45){return false;}
//    }
//        for(int i = 0;i < 3;i++){
//            for(int j = 0;j < 3;j++){
//                int sum2 = 0;
//                for(int i3 = i*3;i3<(i+1)*3;i3++){
//                    for(int j3 = j*3;j3<(j+1)*3;j3++){
//                        sum2 += tiles[i3][j3].getId();
//                    }
//                    if(sum2 != 45){return false;}
//
//
//                }
//
//            }
//
//            }
//            return true;
//        }


    private void createSolvedBoard() {
        ArrayList<Integer> digits = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        Collections.shuffle(digits);
        int randomNumber = digits.get(0);
        SudokuTreeNode root = new SudokuTreeNode(0, 0, randomNumber, 0);
        getCorrectTiles(root);

    }

    class SudokuTreeNode {
        ArrayList<SudokuTreeNode> children;
        int row;
        int col;
        int value;
        int length;

        SudokuTreeNode(int i, int j, int x, int length) {
            this.value = x;
            this.length = length;
            this.col = j;
            this.row = i;
            this.children = new ArrayList<>();


        }


        void getChildren() {
            int nRow;
            int nCol;
            if (this.col < 8) {
                nCol = this.col + 1;
                nRow = this.row;
            } else {
                nRow = this.row + 1;
                nCol = 0;
            }
            if (nRow < 9) {
                ArrayList<Integer> available = getAvailable(nRow, nCol);
                Collections.shuffle(available);
                for (Integer x : available) {
                    SudokuTreeNode child = new SudokuTreeNode(nRow, nCol, x, this.length + 1);
                    this.children.add(child);
                }
            }

        }
    }


    private void getCorrectTiles(SudokuTreeNode node) {
        if (!found) {
            tiles[node.row][node.col] = new Tile(node.value - 1);
            node.getChildren();
            if (node.length == 80) {
                found = true;

            } else if (node.children.isEmpty()) {
            } else {
                for (SudokuTreeNode x : node.children) {
                    getCorrectTiles(x);
                }
            }
        }
    }


    private ArrayList<Integer> getRowBuddies(int i, int j) {
        ArrayList<Integer> rowBuddies = new ArrayList<>();
        for (int x = 0; x < 9; x++) {
            if (x < j) {
                rowBuddies.add(tiles[i][x].getId());

            }
        }
        return rowBuddies;
    }

    private ArrayList<Integer> getColBuddies(int i, int j) {
        ArrayList<Integer> colBuddies = new ArrayList<>();
        for (int x = 0; x < 9; x++) {
            if (x < i) {
                colBuddies.add(tiles[x][j].getId());

            }
        }
        return colBuddies;
    }

    private ArrayList<Integer> getSquareBuddies(int i, int j) {
        int i3 = (i / 3) * 3;
        int j3 = (j / 3) * 3;
        ArrayList<Integer> squareBuddies = new ArrayList<>();

        for (int x = i3; x < i3 + 3; x++) {
            for (int y = j3; y < j3 + 3; y++) {
                if (((9 * x) + y) < ((9 * i) + j)) {
                    squareBuddies.add(tiles[x][y].getId());
                }
            }
        }
        return squareBuddies;
    }

    private ArrayList<Integer> getAllBuddies(int i, int j) {
        ArrayList<Integer> allBuddies = new ArrayList<>();
        allBuddies.addAll(getRowBuddies(i, j));
        allBuddies.addAll(getColBuddies(i, j));
        allBuddies.addAll(getSquareBuddies(i, j));
        return allBuddies;
    }

    ArrayList<Integer> getAvailable(int i, int j) {
        ArrayList<Integer> digits = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        ArrayList<Integer> available = new ArrayList<>();
        ArrayList<Integer> buddies = getAllBuddies(i, j);

        for (Integer x : digits) {
            if (!buddies.contains(x)) {
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
     *
     * @return true iff all rows are correctly written
     */
    private boolean checkRows() {
        for (int row = 0; row < 9; row++) {
            if (!this.checkSingleRow(row)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if one row correspond to a correct sudoku order
     *
     * @return true iff the row is correctly written
     */
    private boolean checkSingleRow(int row) {
        List<Integer> rowTiles = new ArrayList<>();
        for (int col = 0; col < 9; col++) {
            int val = this.tiles[row][col].getId();
            if (val != 0) {
                rowTiles.add(val);
            }
        }
        return checkWithChecker(rowTiles);
    }

    /**
     * Checks if all cols correspond to a correct sudoku order
     *
     * @return true iff all cols are correctly written
     */
    private boolean checkCols() {
        for (int col = 0; col < 9; col++) {
            if (!this.checkSingleCol(col)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if one col correspond to a correct sudoku order
     *
     * @return true iff the col is correctly written
     */
    private boolean checkSingleCol(int col) {
        List<Integer> colTiles = new ArrayList<>();
        for (int row = 0; row < 9; row++) {
            int val = this.tiles[row][col].getId();
            if (val != 0) {
                colTiles.add(val);
            }
        }
        return checkWithChecker(colTiles);
    }

    /**
     * Checks if all boxes correspond to a correct sudoku order
     *
     * @return true iff all boxes are correctly written
     */
    private boolean checkBoxes() {
        for (int ternaryCol = 0; ternaryCol < 3; ternaryCol++) {
            for (int ternaryRow = 0; ternaryRow < 3; ternaryRow++) {
                if (!checkSubBox(ternaryRow, ternaryCol)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if one box correspond to a correct sudoku order
     *
     * @return true iff the box is correctly written
     */
    private boolean checkSubBox(int ternaryRow, int ternaryCol) {
        List<Integer> tiles = new ArrayList<>();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                tiles.add(this.tiles[3*ternaryRow + r][3*ternaryCol + c].getId());
            }
        }

        return checkWithChecker(tiles);
    }

    private boolean checkWithChecker(List<Integer> numTiles) {
        if (numTiles.size() != CHECKER.length) {
            return false;
        }

        for (int num : CHECKER) {
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
            if (item != 0) {
                rowTiles.add(item);
            }
        }

        // get all rows
        for (int i = 0; i < 9; i++) {
            int item = this.board.getTile(i, col).getId();
            if (item != 0) {
                colTiles.add(item);
            }
        }

        // check if the value we are trying to position at position is already
        // existing in column or row
        int value = SudokuActivity.currentNumber;

        List<Integer> boxTiles = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            int boxRow = ternaryRow * 3 + i;
            for (int j = 0; j < 3; j++) {
                int boxCol = ternaryCol * 3 + j;
                int item = this.board.getTile(boxRow, boxCol).getId();
                if (item != 0) {
                    boxTiles.add(item);
                }
            }
        }
        return !(boxTiles.contains(value) || rowTiles.contains(value) || colTiles.contains(value));
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    void touchMove(int position) {
        int row = position / SudokuBoard.numRows;
        int col = position % SudokuBoard.numCols;

        if (isValidTap(position)&&(tiles[row][col].getId()==0)) {
            Tile newTile = new Tile(SudokuActivity.currentNumber - 1);
            board.setTile(row, col, newTile);
        }
    }
}

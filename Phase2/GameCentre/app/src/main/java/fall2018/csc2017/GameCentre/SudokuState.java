package fall2018.csc2017.GameCentre;

import java.io.Serializable;

/**
 * Game state class for Sudoku.
 */
class SudokuState extends GameState implements Serializable {
    /**
     * The current SlidingTilesBoardManager, containing current configuration of tiles.
     */
    private SudokuBoardManager boardManager;

    /**
     * The difficulty of the current board.
     */
    private int difficulty = 0;

    long getTime() {
        return time;
    }

    void setTime(long time) {
        this.time = time;
    }

    /**
     * The current amount of time spent solving the game already.
     */
    private long time = 0;
    // TODO add time to one of the constructors.

    /**
     * The serialVersionUID of this serializable object.
     */
    static final long serialVersionUID = -3639277777770222289L;

    /**
     * Create sudoku state with a given board manager and number of moves.
     *
     * @param boardManager the board manager
     * @param numMoves     the current number of moves
     */
    SudokuState(SudokuBoardManager boardManager, int numMoves) {
        this.boardManager = boardManager;
        this.numMoves = numMoves;
    }

    /**
     * Create a sudoku state with a given board manager, number of moves, complexity,
     * max undone, moves undone and if the number of undones are unlimited.
     *
     * @param boardManager the board manager
     * @param numMoves     the current number of moves
     * @param difficulty   the complexity of the board
     * @param maxUndone    max number of undones
     * @param movesUndone  current number of moves undone
     * @param isUnlimited  if the number of moves undone has no limit
     */
    SudokuState(SudokuBoardManager boardManager, int numMoves, int difficulty, int maxUndone,
                      int movesUndone, boolean isUnlimited, long time) {
        this.boardManager = boardManager;
        this.numMoves = numMoves;
        this.difficulty = difficulty;
        this.maxNumMovesUndone = maxUndone;
        this.numMovesUndone = movesUndone;
        this.unlimitedUndo = isUnlimited;
        this.time = time;
    }

    /**
     * Return the current SudokuBoardManager.
     *
     * @return current SudokuBoardManager.
     */
    SudokuBoardManager getBoardManager() {
        return boardManager;
    }

    /**
     * Calculate and return the score based on number of moves taken.
     *
     * @return score of this SlidingTilesState.
     */
    int getScore() { //TODO create formula for getScore based on double time.
//        if (complexity == 3) {
//            return (int) (Math.round((500 * Math.exp(-(double) numMoves / 35))));
//        } else if (complexity == 4) {
//            return (int) (Math.round((1000 * Math.exp(-(double) numMoves / 20))));
//        } else {
//            return (int) (Math.round((3000 * Math.exp(-(double) numMoves / 100))));
//        }
        return 0;
    }

    /**
     * Return the complexity of this SlidingTilesState.
     *
     * @return the complexity.
     */
    int getDifficulty() {
        return difficulty;
    }

    /**
     * Set the complexity of the SlidingTilesState.
     *
     * @param difficulty the desired complexity of SlidingTilesState.
     */
    void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}

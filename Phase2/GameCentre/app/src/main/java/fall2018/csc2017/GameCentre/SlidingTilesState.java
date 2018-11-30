/*
Model
 */
package fall2018.csc2017.GameCentre;

import java.io.Serializable;

/**
 * Game state class for SlidingTiles.
 */
class SlidingTilesState extends GameState implements Serializable {
    /**
     * The current SlidingTilesBoardManager, containing current configuration of tiles.
     */
    private SlidingTilesBoardManager slidingTilesBoardManager;

    /**
     * The complexity of the current board.
     */
    private int complexity = 0;

    /**
     * The serialVersionUID of this serializable object.
     */
    static final long serialVersionUID = -3639293090140222289L;

    /**
     * Create sliding tile state with a given board manager and number of moves.
     *
     * @param slidingTilesBoardManager the board manager
     * @param numMoves     the current number of moves
     */
    SlidingTilesState(SlidingTilesBoardManager slidingTilesBoardManager, int numMoves) {
        this.slidingTilesBoardManager = slidingTilesBoardManager;
        this.numMoves = numMoves;
    }

    /**
     * Create a sliding tile state with a given board manager, number of moves, complexity,
     * max undone, moves undone and if the number of undones are unlimited.
     *
     * @param slidingTilesBoardManager the board manager
     * @param numMoves     the current number of moves
     * @param complexity   the complexity of the board
     * @param maxUndone    max number of undones
     * @param movesUndone  current number of moves undone
     * @param isUnlimited  if the number of moves undone has no limit
     */
    SlidingTilesState(SlidingTilesBoardManager slidingTilesBoardManager, int numMoves, int complexity, int maxUndone,
                      int movesUndone, boolean isUnlimited) {
        this.slidingTilesBoardManager = slidingTilesBoardManager;
        this.numMoves = numMoves;
        this.complexity = complexity;
        this.maxNumMovesUndone = maxUndone;
        this.numMovesUndone = movesUndone;
        this.unlimitedUndo = isUnlimited;
    }

    /**
     * Return the current SlidingTilesBoardManager.
     *
     * @return current SlidingTilesBoardManager.
     */
    SlidingTilesBoardManager getSlidingTilesBoardManager() {
        return slidingTilesBoardManager;
    }


    int getNumMoves() {return this.numMoves;}

    /**
     * Return the complexity of this SlidingTilesState.
     *
     * @return the complexity.
     */
    int getComplexity() {
        return complexity;
    }

    /**
     * Set the complexity of the SlidingTilesState.
     *
     * @param complexity the desired complexity of SlidingTilesState.
     */
    void setComplexity(int complexity) {
        this.complexity = complexity;
    }
}

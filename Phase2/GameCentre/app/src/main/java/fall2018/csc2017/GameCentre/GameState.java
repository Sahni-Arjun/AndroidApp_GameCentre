/*
Model class
 */
package fall2018.csc2017.GameCentre;

import java.io.Serializable;

abstract class GameState implements Serializable {

    static final long serialVersionUID = -3639293090177222289L;

    /**
     * The current number of moves taken.
     */
    int numMoves;

    /**
     * The number of moves already undone.
     */
    int numMovesUndone;

    /**
     * The maximum number of moves allowed to be undone.
     */
    int maxNumMovesUndone;

    /**
     * Whether unlimited Undo has been allowed.
     */
    boolean unlimitedUndo = false;

    /**
     * Return the number of moves already undone.
     *
     * @return the number of moves already undone.
     */
    int getNumMovesUndone() {
        return numMovesUndone;
    }

    /**
     * Return whether unlimited moves are allowed in this SlidingTilesState.
     *
     * @return if unlimited moves are allowed.
     */
    boolean getUnlimitedUndo() {
        return unlimitedUndo;
    }

    /**
     * Increment the number of moves already taken.
     *
     * @param prevNumMoves number of moves from the previous SlidingTilesState.
     */
    void incrementNumMoves(int prevNumMoves) {
        this.numMovesUndone = prevNumMoves + 1;
    }

    /**
     * Set the maximum number of moves allowed to be undone.
     *
     * @param maxNumMovesUndone maximum number of moves allowed to be undone.
     */
    void setMaxNumMovesUndone(int maxNumMovesUndone) {
        this.maxNumMovesUndone = maxNumMovesUndone;
    }

    /**
     * Set whether unlimited number of moves is allowed to be undone.
     *
     */
    void setUnlimitedUndo() {
        this.unlimitedUndo = true;
    }

    /**
     * Return if SlidingTilesState still allows moves to be undone.
     *
     * @return if SlidingTilesState is allowed to be undone.
     */
    boolean canUndo() {
        return unlimitedUndo || (maxNumMovesUndone > numMovesUndone);
    }
}

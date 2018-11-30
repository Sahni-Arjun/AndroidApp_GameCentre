/*
Model class
 */
package fall2018.csc2017.GameCentre;

/**
 * The board manager for board based games.
 */
abstract class BoardManager extends GameManager {

    /**
     * Checks for a valid tap
     * @param position the location of the tap
     * @return whether tap was valid.
     */
    abstract boolean isValidTap(int position);

    /**
     * Make a move
     * @param position the position of the move.
     */
    abstract void touchMove(int position);



}

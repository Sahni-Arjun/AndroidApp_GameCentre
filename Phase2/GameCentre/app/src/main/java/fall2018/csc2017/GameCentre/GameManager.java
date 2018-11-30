/*
Abstract class
 */
package fall2018.csc2017.GameCentre;

/**
 * The manager for the interaction between elementary game objects.
 */
abstract class GameManager {
    /**
     * Checks of the game objects are arranged in a winning position.
     * @return if the game has been won.
     */
    abstract boolean puzzleSolved();
}

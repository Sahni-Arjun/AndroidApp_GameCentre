/*
Model
 */
package fall2018.csc2017.GameCentre;

import java.io.Serializable;

/**
 * Game state class for Hangman.
 */
class HangmanState extends GameState implements Serializable {
    /**
     * The current SlidingTilesBoardManager, containing current configuration of tiles.
     */
    private WordManager wordManager;

    /**
     * The complexity of the current word.
     */
    private int complexity = 0;

    /**
     * The serialVersionUID of this serializable object.
     */
    static final long serialVersionUID = -3639293595145222289L;

    /**
     * Create Hangman state with a given word manager and number of tries.
     *
     * @param wordManager the word manager
     * @param numTries     the current number of tries used
     */
    HangmanState(WordManager wordManager, int numTries) {
        this.wordManager = wordManager;
        this.numMoves = numTries;
    }

    /**
     * Create a Hangman state with a given word manager, number of tries, complexity,
     * max undone, moves undone and if the number of undones are unlimited.
     *
     * @param wordManager the board manager
     * @param numTries     the current number of tries
     * @param complexity   the complexity of the board
     * @param maxUndone    max number of undones
     * @param triesUndone  current number of tries undone
     * @param isUnlimited  if the number of tries undone has no limit
     */
    HangmanState(WordManager wordManager, int numTries, int complexity, int maxUndone,
                      int triesUndone, boolean isUnlimited) {
        this.wordManager = wordManager;
        this.numMoves = numTries;
        this.complexity = complexity;
        this.maxNumMovesUndone = maxUndone;
        this.numMovesUndone = triesUndone;
        this.unlimitedUndo = isUnlimited;
    }

    /**
     * Return the current WordManager.
     *
     * @return current WordManager.
     */
    WordManager getWordManager() {
        return wordManager;
    }

    /**
     * Getter for numMoves
     *
     * @return int numMoves
     */
    int getNumMoves() {return numMoves;}

    /**
     * Return the complexity of this HangmanState.
     *
     * @return the complexity.
     */
    int getComplexity() {
        return complexity;
    }

    /**
     * Set the complexity of the HangmanState.
     *
     * @param complexity the desired complexity of HangmanState.
     */
    void setComplexity(int complexity) {
        this.complexity = complexity;
    }
}

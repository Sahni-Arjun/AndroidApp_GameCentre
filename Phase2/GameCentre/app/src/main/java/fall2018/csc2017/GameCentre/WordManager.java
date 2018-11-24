package fall2018.csc2017.GameCentre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Manage a word, similarly to a one-row board, including checking for a win or a loose
 */
class WordManager extends BoardManager implements Serializable {

    /**
     * The board being managed.
     */
    private Word word;

    public static int length;

    /**
     * Manage a word that has been pre-populated with the appropriate letters.
     *
     * @param word the word
     */
    WordManager(Word word) {
        this.word = word;
    }

    /**
     * Return the current word.
     *
     * @return the current word.
     */
    public Word getWord() {
        return word;
    }

    /**
     * Set a new word.
     *
     * @param newWord a new word
     */
    public void setWord(Word newWord) {
        this.word = newWord;
    }

    /**
     * Manage a new retrieved word.
     */
    WordManager() {

        // todo replace this block by retrieving from file according to complexity
        List<Letter> letters = new ArrayList<>();

        final int numLetter = Word.numCols; // todo update according to Kevin's implementation
        this.length = HangmanComplexityActivity.complexity + 1; // todo update according to Kevin's implementation

        for (int letterNum = 65; letterNum != (65 + numLetter); letterNum++) {  // A is coded as dec 65 in ASCII
            letters.add(new Letter(letterNum));
        }
        this.word = new Word(letters);
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    boolean puzzleSolved() {
        Iterator<Tile> boardIterator = word.iterator();
        Tile prev_Tile = boardIterator.next();
        Tile current_Tile;
        // Iterate through all Tiles checking if the previous id is larger than the next.
        while (boardIterator.hasNext()) {
            current_Tile = boardIterator.next();
            if (prev_Tile.getId() > current_Tile.getId()) {
                return false;
            }
            prev_Tile = current_Tile;
        }
        // If the end of the while loop is reached then all Tiles are in correct order.
        return true;
    }


    /**
     * Return whether the tapped position is a hidden letter.
     *
     * @param position to check
     * @return whether the letter at position is hidden
     */
    boolean isValidTap(int position) {

        // todo discuss with group if player will choose where in the word to guess
        return true;
    }


    /**
     * Process a touch at position in the word, revealing letter if appropriate.
     *
     * @param position the position
     */
    void touchMove(int position) {

        // todo: discuss with group if player will be asked to specify a guessed letter's position
    }
}

package fall2018.csc2017.GameCentre;

import java.util.List;


public class Word extends Board{
    /**
     * Number of rows
     */
    public static int numRows = 1;

    /**
     * Number of cols
     */
    public static int numCols;

    /**
     * The letters on the Hangman Word in order
     */
    private Letter[][] letters = new Letter[numRows][numCols];

    /**
     * Set the letters to the given letters.
     * @param letters letters
     */
        void setLetters(Letter[][] letters) {
            this.letters = letters;
    }

    Letter[][] getLetters() {
        return letters;
    }

    /**
     * A new word made of letters in order.
     * Precondition: len(letters) == numCols
     *
     * @param letters the letters for the word
     */
    Word(List<Letter> letters) {
        for (int i = 0; i < numCols; i++) {
            this.letters[0][i] = letters.get(i);
        }
    }

    /**
     * Get the letter at a particular position (col)
     */
    Letter getLetter(int col) {
        final int ROW = 0;
        return this.letters[ROW][col];
    }

    /**
     * Update letters containing ASCII code letter to be shown
     */
    void updateLetter(int letter) {
        for (int pos = 0; pos < Word.numCols; pos++) {
            if(letters[0][pos].getId() == letter){
                letters[0][pos].hidden = false;

            }
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Update hangman doll
     */
    void updateDoll() {
        setChanged();
        notifyObservers();
    }
}
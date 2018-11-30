package fall2018.csc2017.GameCentre;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Manage a word, similarly to a one-row board, including checking for a win or a loose
 */
class WordManager extends GameManager implements Serializable {

    /**
     * The word being managed.
     */
    private Word word;


    /**
     * A string representing the word being managed.
     */
    public static String stringWord;

    /**
     * The length of the word being managed.
     */
    public static int length;


    /**
     * The number of guessed already made.
     */
    public static int tries = 0;

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
    WordManager(String selectedWord){

        List<Letter> letters = new ArrayList<>();

        stringWord = selectedWord;

        char[] splittedWord = selectedWord.toCharArray();

        for(int count=0; count<splittedWord.length; count++){

            char curChar = Character.toUpperCase(splittedWord[count]);
            int letterNum = curChar;
            letters.add(new Letter(letterNum));

        }

        this.length = HangmanComplexityActivity.complexity + 1;
        this.word = new Word(letters);
    }


    /**
     * Manage a new retrieved word, support for mocking tests.
     */
    WordManager(Word word){

        this.word = word;
    }

    /**
     * Return whether all letters have been correctly guessed.
     *
     * @return whether letters are all being displayed
     */
    boolean puzzleSolved() {
        for (int pos = 0; pos < Word.numCols; pos++) {
            if(word.getLetter(pos).hidden){
                return false;
            }
        }
        return true;
    }

    /**
     * Process a keyboard input position if appropriate.
     *
     * @param guess ascii code of letter being guessed
     */
    void keyBoard(int guess) {
        for (int pos = 0; pos < Word.numCols; pos++) {

            if(word.getLetter(pos).getId() == guess){
                word.updateLetter(guess);
                return;
            }
        }
        tries++;
        word.updateDoll();

    }
}

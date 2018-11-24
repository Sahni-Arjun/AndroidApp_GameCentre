package fall2018.csc2017.GameCentre;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;
import java.util.List;
import java.util.Random;


/**
 * Manage a word, similarly to a one-row board, including checking for a win or a loose
 */
class WordManager extends BoardManager implements Serializable {

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
    public int tries = 0;

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
    WordManager(){

        // todo replace this block by retrieving from file according to complexity
        List<Letter> letters = new ArrayList<>();

        final int numLetter = Word.numCols; // todo update according to Kevin's implementation
        this.length = HangmanComplexityActivity.complexity + 1; // todo update according to Kevin's implementation

        //for (int letterNum = 65; letterNum != (65 + numLetter); letterNum++) {  // A is coded as dec 65 in ASCII
        //    letters.add(new Letter(letterNum));
        //}

        //String fileName = "words.txt";
        //Scanner fileScan = new Scanner(fileName);
        //ArrayList words = new ArrayList();
        //String tempWord;

        //for(; fileScan.hasNext(); words.add(tempWord)){
        //    tempWord = fileScan.next();
        //}

        ArrayList words = new ArrayList();
        words.add("ball");
        words.add("band");
        words.add("base");
        words.add("bath");

        Random rand = new Random();
        int wordNum = rand.nextInt(4);
        String selectedWord = (String)words.get(wordNum);

        stringWord = selectedWord;

        char[] splittedWord = selectedWord.toCharArray();

        for(int count=0; count<splittedWord.length; count++){

            int letterNum = splittedWord[count];
            letters.add(new Letter(letterNum));

        }

        this.word = new Word(letters);
    }

    /**
     * Return whether all letters have been correctly guessed.
     *
     * @return whether letters are all being displayed
     */
    boolean puzzleSolved() {

        for (int pos = 0; pos < Word.numCols; pos++) {

            if(word.getLetter(0, pos).hidden){
                return false;
            }
        }
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



    /**
     * Process a keyboard input position if appropriate.
     *
     * @param guess ascii code of letter being guessed
     */
    void keyBoard(int guess) {

        for (int pos = 0; pos < Word.numCols; pos++) {

            if(word.getLetter(0, pos).getId() == guess){
                word.updateLetter(guess);
                return;
            }
        }
        tries++;
        word.updateDoll();

    }
}

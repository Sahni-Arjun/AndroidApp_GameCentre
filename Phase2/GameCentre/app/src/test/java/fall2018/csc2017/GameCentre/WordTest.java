package fall2018.csc2017.GameCentre;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WordTest {

    private WordManager wordManager;

    private Word currWord;

    @Before
    public void setUpWord() {
        SlidingTileComplexityActivity.complexity = 3;
        HangmanComplexityActivity.complexity = 3;
        Word.numCols = HangmanComplexityActivity.complexity + 1;
        Word.numRows = 1;
        wordManager = new WordManager("Test");
        currWord = wordManager.getWord();
    }

    @Test
    public void testUpdateLetter() {
        currWord.updateLetter(84);
        currWord.updateLetter(1);
        Letter[][] arrayLetter = currWord.getLetters();
        assertEquals(arrayLetter[0][0].hidden, false);
        assertEquals(arrayLetter[0][1].hidden, true);
        assertEquals(arrayLetter[0][2].hidden, true);
        assertEquals(arrayLetter[0][3].hidden, false);
    }
}

package fall2018.csc2017.GameCentre;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class WordTest {

    private Word currWord;

    @Before
    public void setUpWord() {
        SlidingTileComplexityActivity.complexity = 3;
        HangmanComplexityActivity.complexity = 3;
        Word.numCols = HangmanComplexityActivity.complexity + 1;
        Word.numRows = 1;
        WordManager wordManager = new WordManager("Test");
        currWord = wordManager.getWord();
    }

    @Test
    public void testUpdateLetter() {
        currWord.updateLetter(84);
        currWord.updateLetter(1);
        Letter[][] arrayLetter = currWord.getLetters();
        assertFalse(arrayLetter[0][0].hidden);
        assertTrue(arrayLetter[0][1].hidden);
        assertTrue(arrayLetter[0][2].hidden);
        assertFalse(arrayLetter[0][3].hidden);
    }
}

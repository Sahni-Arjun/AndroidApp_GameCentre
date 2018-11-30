package fall2018.csc2017.GameCentre;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class WordManagerTest {
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
    public void testPuzzleSolved() {
        currWord.updateLetter(84);
        currWord.updateLetter(1);
        assertFalse(wordManager.puzzleSolved());
        currWord.updateLetter(69);
        currWord.updateLetter(83);
        assertTrue(wordManager.puzzleSolved());
    }
}

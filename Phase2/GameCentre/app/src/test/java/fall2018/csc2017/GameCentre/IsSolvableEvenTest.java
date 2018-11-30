//package fall2018.csc2017.GameCentre;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//public class IsSolvableEvenTest {
//
//    /**
//     * The board used for this test.
//     */
//    Board board;
//
//    /**
//     * Create the even board where the blank is on the even row and has odd number of inversions.
//     * This board is solvable.b
//     */
//    private void solvableEvenBoardBlankOnEven() {
//        List<Tile> tilesBlankEvenRowInversionsOdd = new ArrayList<>();
//        tilesBlankEvenRowInversionsOdd.add(new Tile(0));
//        tilesBlankEvenRowInversionsOdd.add(new Tile(3));
//        tilesBlankEvenRowInversionsOdd.add(new Tile(2));
//        tilesBlankEvenRowInversionsOdd.add(new Tile(1));
//        this.board = new Board(tilesBlankEvenRowInversionsOdd);
//    }
//
//    /**
//     * Create the even board where the blank is on the even row and has even number of inversions.
//     * This board is unsolvable.
//     */
//    private void unsolvableEvenBoardBlankOnEven() {
//        List<Tile> tilesBlankEvenRowInversionsEven = new ArrayList<>();
//        tilesBlankEvenRowInversionsEven.add(new Tile(0));
//        tilesBlankEvenRowInversionsEven.add(new Tile(3));
//        tilesBlankEvenRowInversionsEven.add(new Tile(1));
//        tilesBlankEvenRowInversionsEven.add(new Tile(2));
//        this.board = new Board(tilesBlankEvenRowInversionsEven);
//    }
//
//    /**
//     * Create the even board where the blank is on the odd row and has an even number of inversions.
//     * This is a solvable board.
//     */
//    private void solvableEvenBoardBlankOnOdd() {
//        List<Tile> tilesBlankOddRowInversionsEven = new ArrayList<>();
//        tilesBlankOddRowInversionsEven.add(new Tile(1));
//        tilesBlankOddRowInversionsEven.add(new Tile(2));
//        tilesBlankOddRowInversionsEven.add(new Tile(3));
//        tilesBlankOddRowInversionsEven.add(new Tile(0));
//        this.board = new Board(tilesBlankOddRowInversionsEven);
//    }
//
//    /**
//     * Create the even board where the blank is on the odd row and has an odd number of inversions.
//     * This is an unsolvable board.
//     */
//    private void unsolvableEvenBoardBlankOnOdd() {
//        List<Tile> tilesBlankOddRowInversionsOdd = new ArrayList<>();
//        tilesBlankOddRowInversionsOdd.add(new Tile(0));
//        tilesBlankOddRowInversionsOdd.add(new Tile(2));
//        tilesBlankOddRowInversionsOdd.add(new Tile(1));
//        tilesBlankOddRowInversionsOdd.add(new Tile(3));
//        this.board = new Board(tilesBlankOddRowInversionsOdd);
//    }
//
//    /**
//     * Create an even board which is already solved.
//     */
//    private void solvedEvenBoard() {
//        List<Tile> tilesSolved = new ArrayList<>();
//        tilesSolved.add(new Tile(0));
//        tilesSolved.add(new Tile(1));
//        tilesSolved.add(new Tile(2));
//        tilesSolved.add(new Tile(3));
//        this.board = new Board(tilesSolved);
//    }
//
//    /**
//     * Set the complexity for the test.
//     */
//    @Before
//    public void setComplexity(){
//        SlidingTileComplexityActivity.complexity = 2;
//    }
//
//    /**
//     * Test whether or not the even board is solvable.
//     */
//    @Test
//    public void testIsSolvableEvenSizedBoard() {
//        solvableEvenBoardBlankOnEven();
//        assertEquals(true, this.board.isSolvable());
//        unsolvableEvenBoardBlankOnEven();
//        assertEquals(false, this.board.isSolvable());
//        solvableEvenBoardBlankOnOdd();
//        assertEquals(true, this.board.isSolvable());
//        unsolvableEvenBoardBlankOnOdd();
//        assertEquals(false, this.board.isSolvable());
//        solvedEvenBoard();
//        assertEquals(true, this.board.isSolvable());
//    }
//}
